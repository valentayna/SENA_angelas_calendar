"""
Aplicación Flask para sistema de manejo de citas con autenticación de usuarios
Este módulo proporciona:
- Sistema de login/registro con roles (cliente/administrador)
- Dashboard para clientes con gestión de citas
- Vista de administrador
- Validación de formularios y manejo de sesiones
"""

# Importación de librerías y modulos

from flask import Flask, render_template, request, redirect, session, url_for, jsonify
from datetime import datetime, time, date
import conexion    # modulo para manejar la conexión a la base de datos


# Inicialización de la aplicación Flask
app = Flask(__name__)
app.secret_key = 'clave-secreta' 

@app.route('/', methods=['GET', 'POST'])
def login():

    """
    Maneja el proceso de autenticación de usuarios
    
    Rutas:
        GET / - Muestra el formulario de login
        POST / - Procesa los datos del formulario
    
    Proceso:
        1. Valida credenciales contra la base de datos
        2. Establece variables de sesión
        3. Redirige al dashboard según el rol del usuario
    
    Returns:
        render_template: Retorna template login.html o redirección
    """

    mensaje = ""
    # procesamiento de formulario post
    if request.method == 'POST':
        correo = request.form['correo']
        contraseña = request.form['contraseña']

        conn = None
        cursor = None
        try:
            conn = conexion.conectar()
            cursor = conn.cursor(dictionary=True)
            cursor.execute("SELECT correo, nombre, rol FROM usuarios WHERE correo = %s AND contraseña = %s", (correo, contraseña))
            resultado = cursor.fetchone()

            if resultado:
                #establecer sesión
                nombre = resultado["nombre"] # type: ignore
                rol = resultado["rol"] # type: ignore
                correo = resultado["correo"] # type: ignore
                session['nombre'] = nombre
                session['correo'] = correo
                #redireción a pagina de inicio segun el rol
                if rol == "cliente":
                    return redirect(url_for('homeCliente'))  # Redirige a la vista de inicio del cliente
                elif rol == "administrador":
                    return redirect(url_for('homeAdmin'))  # Redirige a la vista de inicio del administrador
                else:
                    mensaje= "Error en la base de datos."

            else:
                mensaje = "Correo o contraseña incorrectos."

        except Exception as e:
            mensaje = f"Error en la base de datos: {e}"

        finally:
            if cursor:
                cursor.close()
            if conn and conn.is_connected():
                conn.close()

    return render_template("login.html", mensaje=mensaje)

@app.route('/homeCliente')
def homeCliente():

    """
    Dashboard principal para usuarios con rol 'cliente'
    
    Proceso:
        1. Obtiene citas del cliente desde la BD
        2. Calcula la próxima cita
        3. Formatea datos para visualización
    
    Returns:
        render_template: Template homeCliente.html con contexto:
            - nombre: Nombre del usuario
            - correo: Correo del usuario  
            - citas: Lista de citas del cliente
            - dia/mes: Próxima cita formateada
    """
    
    # verificación de sesion
    nombre = session.get('nombre', 'usuario')
    correo = session.get('correo', 'usuario')

    conn = None
    cursor = None
    citas = []

    # Función auxiliar
    def formatear_tiempo_mysql(tiempo):
        if isinstance(tiempo, time):
            return tiempo.strftime('%H:%M')
        elif hasattr(tiempo, 'seconds'):
            horas = tiempo.seconds // 3600
            minutos = (tiempo.seconds % 3600) // 60
            return f"{horas:02}:{minutos:02}"
        return str(tiempo)

    # Nombres de los meses
    meses_nombres = [
        "enero", "febrero", "marzo", "abril", "mayo", "junio",
        "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    ]

    dia = ""
    mes = ""

    try:
        conn = conexion.conectar()
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT * FROM citas WHERE correo_cliente = %s", (correo,))
        citas_raw = cursor.fetchall()

        for cita in citas_raw:
            cita['fecha'] = cita['fecha'].isoformat()
            cita['hora_inicio'] = formatear_tiempo_mysql(cita['hora_inicio'])
            cita['hora_fin'] = formatear_tiempo_mysql(cita['hora_fin'])
        citas = citas_raw

        # Buscar próxima cita
        hoy = datetime.now().date()
        futuras = [c for c in citas if datetime.fromisoformat(c['fecha']).date() >= hoy]
        futuras.sort(key=lambda c: (c['fecha'], c['hora_inicio']))  # Ordenar por fecha y hora

        if futuras:
            proxima = datetime.fromisoformat(futuras[0]['fecha'])
            dia = proxima.day
            mes = meses_nombres[proxima.month - 1]  # índice 0

    except Exception as e:
        print(f"Error al consultar citas: {e}")
    finally:
        if cursor:
            cursor.close()
        if conn and conn.is_connected():
            conn.close()

    return render_template('homeCliente.html',
                           nombre=nombre,
                           correo=correo,
                           citas=citas,
                           dia=dia,
                           mes=mes)

@app.route('/homeAdmin')
def homeAdmin():

    """
    Dashboard principal para usuarios administradores
    
    Returns:
        render_template: Template homeAdmin.html con nombre de usuario
    """

    nombre = session.get('nombre', 'usuario')
    return render_template('homeAdmin.html', nombre=nombre)

@app.route('/register', methods=['GET', 'POST'])
def register():

    """
    Maneja el registro de nuevos usuarios
    Compatible con formulario HTML y JSON (Postman)
    
    Validaciones:
        - Longitud mínima de contraseña (8 caracteres)
        - Correo electrónico único
    
    Returns:
        render_template: Template register.html con mensajes de estado
    """
    mensaje = ""

    if request.method == 'POST':
        # Detectar si viene JSON o formulario
        data = request.get_json(silent=True)
        if data:
            correo = data.get('correo')
            nombre = data.get('nombre')
            contraseña = data.get('contraseña')
        else:
            correo = request.form.get('correo')
            nombre = request.form.get('nombre')
            contraseña = request.form.get('contraseña')

        rol = "cliente"

        if not correo or not nombre or not contraseña:
            return jsonify({"error": "Faltan campos obligatorios"}), 400

        if len(contraseña) < 8:
            mensaje = "La contraseña debe contener al menos 8 dígitos"
            return jsonify({"mensaje": mensaje}), 400

        try:
            conn = conexion.conectar()
            cursor = conn.cursor()

            # Verificar si ya existe el correo
            cursor.execute("SELECT * FROM usuarios WHERE correo = %s", (correo,))
            existente = cursor.fetchone()

            if existente:
                mensaje = "Ya existe un usuario registrado con ese correo."
                return jsonify({"mensaje": mensaje}), 409

            # Insertar nuevo usuario
            cursor.execute(
                "INSERT INTO usuarios (correo, nombre, contraseña, rol) VALUES (%s, %s, %s, %s)",
                (correo, nombre, contraseña, rol)
            )
            conn.commit()
            mensaje = "Registro exitoso. ¡Inicia sesión!"
            return jsonify({"mensaje": mensaje}), 201

        except Exception as e:
            return jsonify({"error": str(e)}), 500

        finally:
            if cursor:
                cursor.close()
            if conn and conn.is_connected():
                conn.close()

    # Si es GET, solo devuelve el formulario (si existe)
    return render_template('register.html', mensaje=mensaje)



#-------------------------------------#
# Rutas Cliente
@app.route('/cliente/agenda')
def agendarCliente():
    return render_template('agendarCliente.html')

@app.route('/cliente/citas')
def citasCliente():
    return render_template('citasCliente.html')

@app.route('/cliente/editar-usuario')
def editar_usuarioCliente():
    return render_template('editar_usuarioCliente.html')


@app.route('/cliente/notificaciones')
def notificacionesCliente():
    return render_template('notificacionesCliente.html')

@app.route('/cliente/modificar')
def modificarCliente():
    return render_template('modificarCliente.html')

@app.route('/cliente/usuario')
def usuarioCliente():
    return render_template('usuarioCliente.html')

# Rutas Admin

@app.route('/admin/horarios')
def horarioAdmin():
    return render_template('horarioAdmin.html')

@app.route('/admin/modificar-citas')
def modificarCitasAdmin():
    return render_template('modificarCitasAdmin.html')

@app.route('/admin/servicios')
def serviciosAdmin():
    return render_template('serviciosAdmin.html')

@app.route('/admin/nuevo-servicio')
def nuevoServicioAdmin():
    return render_template('nuevoServicioAdmin.html')



if __name__ == '__main__':
    app.run(debug=True)    # ejecutar la aplicación en modo depuración

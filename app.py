from flask import Flask, render_template, request, redirect, session, url_for
import conexion

app = Flask(__name__)
app.secret_key = 'clave-secreta' 

@app.route('/', methods=['GET', 'POST'])
def login():
    mensaje = ""
    if request.method == 'POST':
        correo = request.form['correo']
        contraseña = request.form['contraseña']

        conn = None
        cursor = None
        try:
            conn = conexion.conectar()
            cursor = conn.cursor(dictionary=True)
            cursor.execute("SELECT nombre FROM usuarios WHERE correo = %s AND contraseña = %s", (correo, contraseña))
            resultado = cursor.fetchone()

            if resultado:
                nombre = resultado["nombre"] # type: ignore
                session['nombre'] = nombre
                return redirect(url_for('home'))  # Redirige a otra vista
            else:
                mensaje = "❌ Correo o contraseña incorrectos."

        except Exception as e:
            mensaje = f"⚠️ Error en la base de datos: {e}"

        finally:
            if cursor:
                cursor.close()
            if conn and conn.is_connected():
                conn.close()

    return render_template("login.html", mensaje=mensaje)


@app.route('/home')
def home():
    nombre = session.get('nombre', 'usuario')
    return render_template('home.html', nombre=nombre)

@app.route('/register', methods=['GET', 'POST'])
def register():
    mensaje = ""
    if request.method == 'POST':
        correo = request.form['correo']
        nombre = request.form['nombre']
        contraseña = request.form['contraseña']

        conn = None
        cursor = None
        try:
            conn = conexion.conectar()
            cursor = conn.cursor()

            # Verificar si ya existe el correo
            cursor.execute("SELECT * FROM usuarios WHERE correo = %s", (correo,))
            existente = cursor.fetchone()
            if existente:
                mensaje = "❌ Ya existe un usuario registrado con ese correo."
            else:
                # Insertar nuevo usuario
                cursor.execute("INSERT INTO usuarios (correo, nombre, contraseña) VALUES (%s, %s, %s)", 
                               (correo, nombre, contraseña))
                conn.commit()
                mensaje = "✅ Registro exitoso. ¡Inicia sesión!"
                return redirect(url_for('login'))

        except Exception as e:
            mensaje = f"⚠️ Error en la base de datos: {e}"

        finally:
            if cursor:
                cursor.close()
            if conn and conn.is_connected():
                conn.close()

    return render_template('register.html', mensaje=mensaje)

if __name__ == '__main__':
    app.run(debug=True)

# pagina web web de agendamiento de citas **Angela´s Calendar**

---

## descripción general
* Hasta el momento se ha completado el modulo de inicio de sesion y registro de la pagina web incluyendo la conexión a la base de datos
* Se realizaron algunos cambios en el frontend para hacer la mas llamativa
* Se integró la base de datos a la pagina web utilizando **mysql.connector** y el framework de programación web **flask** para el backend

---

## ejecutar la pagina web
1. asegurese de tener todas las librerias especificadas en *requirements* instaladas.
2. dirijase al archivo *app.py*
3. de click en *ejecutar archivo de python*
4. deberá aparecer algo como esto:
 * Serving Flask app 'app'
 * Debug mode: on
 WARNING: This is a development server. Do not use it in a production deployment. Use a production WSGI server instead.
 * Running on http://127.0.0.1:5000
 Press CTRL+C to quit
 * Restarting with stat
 * Debugger is active!
 * Debugger PIN: 132-493-589
5. debe seguir el vinculo *http://127.0.0.1:5000* para que la pagina web se abra en el navegador

---

## detalles de la loogica:
### pagina de inicio de sesion
* La pagina de inicio de sesion solicita **correo** y **contraseña** del usuario
* Comprueba si ese usuario existe con el correo y contraseña indicados
* se verifica el tipo de rol que el usuario tiene **(cliente o administrador)** y dependiendo de eso lo redirige a la pagina home correspondiente
* tambien se obtiene el nombre del usuario para mostrar un **mensaje de bienvenida personalizado** en la pagina home

### pagina de registro
* en la pagina de registro de solicita **correo**, **nombre de usuario** y **contraseña**

> psd: se pensaba poner sección de confirmar contraseña pero hubo problemas para integrar las animaciones de el csv con javascript debido a interferencia con el otro entry de contraseña, entonces se decidió retirar esa parte

* se verifica que ese correo no esté asignado a otra cuenta de usuario y que la contraseña tenga una longitud mayor a 8 digitos
* en caso de que todos los datos sean correctos, se crea el nuevo usuario con el rol asignado de **cliente** por defecto
* se redirige a la ventana de inicio de sesion

---

> psd2: se intentó establecer la conección a la base de datos utilizando java o php, pero no soy tan buena con esos lenguajes y no pude **ಥ_ಥ** ah y perdón por esas paginas de home, despues las pongo bonitas :)
def conectar():

    """
    Establece una conexión a la base de datos MySQL.
    Esta función utiliza el conector MySQL para Python para conectarse a una base de datos
    específica en un servidor MySQL. Se requiere que el servidor esté configurado para
    aceptar conexiones SSL.
    Returns:
        mysql.connector.connection.MySQLConnection: 
            Un objeto de conexión a la base de datos que se puede usar para ejecutar consultas.
    Raises:
        mysql.connector.Error: 
            Lanza una excepción si hay un error al intentar conectarse a la base de datos.
    """

    import mysql.connector    # Importa el conector MySQL
    # Establece la conexión a la base de datos con los parámetros necesarios
    conn = mysql.connector.connect(
                    host='cinema-server.mysql.database.azure.com',
                    user='main',
                    password='Va862082',
                    database='angelas_calendar',
                    ssl_ca='BaltimoreCyberTrustRoot.crt.pem'    # Certificado SSL para la conexión segura
                )
    return conn    # retorna el objeto de conexión
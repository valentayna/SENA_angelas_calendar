def conectar():
    import mysql.connector
    conn = mysql.connector.connect(
                    host='cinema-server.mysql.database.azure.com',
                    user='main',
                    password='Va862082',
                    database='angelas_calendar',
                    ssl_ca='BaltimoreCyberTrustRoot.crt.pem'
                )
    return conn
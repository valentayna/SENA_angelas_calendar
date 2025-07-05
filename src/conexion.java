import java.sql.*;

public class conexion {

    static String url = "jdbc:mysql://localhost:3306/angelas_calendar";
    static String user = "root";
    static String password = "Va862082";

    public Connection conectar(){
        Connection conexion = null;

        try{

            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("conexión exitosa");

        }catch(SQLException e){
            e.printStackTrace();
        }
        return conexion;
    }
}

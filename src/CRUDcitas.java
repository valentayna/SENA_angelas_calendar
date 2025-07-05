/* 
Table: citas
Columns:
id_cita int AI PK 
id_cliente int 
id_servicios_select int 
fecha date 
hora_inicio time 
hora_fin time 
precio_total int    
*/


import java.sql.*;

public class CRUDcitas {

    conexion conexion = new conexion();
    
    //CREATE
    public void create (int id_cita, int id_cliente, int id_servicios_select, String fecha, String hora_inicio, String hora_fin, int precio_total){
        String sql = "INSERT INTO citas (id_cita, id_cliente, id_servicios_select, fecha, hora_inicio, hora_fin, precio_total) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion.conectar();
         PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setLong(1, id_cita);
            stmt.setLong(2, id_cliente);
            stmt.setLong(3, id_servicios_select);
            stmt.setString(4, fecha);
            stmt.setString(5, hora_inicio);
            stmt.setString(6, hora_fin);
            stmt.setLong(7, precio_total);
            stmt.executeUpdate();
            System.out.println("cita creada exitosamente");
         } catch (SQLException e) {
            e.printStackTrace();
         }
    }

    //READ
    public void read(){
        String sql = "SELECT * FROM clientes";

        try (Connection con= conexion.conectar();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

                while (rs.next()) {
                    System.out.println("id_cita:" + rs.getInt("id_cita"));
                    System.out.println("id_cliente:" + rs.getInt("id_cliente"));
                    System.out.println("id_servicios_select:" + rs.getInt("id_servicios_select"));
                    System.out.println("fecha:" + rs.getInt("fecha"));
                    System.out.println("hora_inicio:" + rs.getInt("hora_inicio"));
                    System.out.println("hora_fin:" + rs.getInt("hora_fin"));
                    System.out.println("precio_total:" + rs.getInt("precio_total"));
                    System.out.println("-------------");

                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

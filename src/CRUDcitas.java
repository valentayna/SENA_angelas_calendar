/* 
Table: citas
Columns:
id_cita int AI PK 
correo varchar(50) 
fecha date 
hora_inicio time 
hora_fin time 
precio_total int 
*/


import java.sql.*;

public class CRUDcitas {

    conexion conexion = new conexion();
    
    //CREATE
    public void create (String correo, Date fecha, Time hora_inicio, Time hora_fin, int precio_total){
        String sql = "INSERT INTO citas (correo, fecha, hora_inicio, hora_fin, precio_total) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = conexion.conectar();
         PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, correo);
            stmt.setDate(2, fecha);
            stmt.setTime(3, hora_inicio);
            stmt.setTime(4, hora_fin);
            stmt.setInt(5, precio_total);
            stmt.executeUpdate();
            System.out.println("cita creada exitosamente");
         } catch (SQLException e) {
            e.printStackTrace();
         }
    }

    //READ
    public void read(){
        String sql = "SELECT * FROM citas";

        try (Connection con= conexion.conectar();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

                while (rs.next()) {
                    System.out.println("id_cita:" + rs.getInt("id_cita"));
                    System.out.println("correo:" + rs.getString("correo"));
                    System.out.println("fecha:" + rs.getDate("fecha"));
                    System.out.println("hora_inicio:" + rs.getTime("hora_inicio"));
                    System.out.println("hora_fin:" + rs.getTime("hora_fin"));
                    System.out.println("precio_total:" + rs.getInt("precio_total"));
                    System.out.println("-------------");

                }

                System.out.println("Citas Obtenidas.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //UPDATE
    public void update(int id_cita, Date fecha, Time hora_inicio, Time hora_fin, int precio_total) {
        String sql = "UPDATE citas SET fecha = ?, hora_inicio = ?, hora_fin = ?, precio_total = ? WHERE id_cita = ?";

        try (Connection con = conexion.conectar();
            PreparedStatement stmt = con.prepareStatement(sql)){

                stmt.setDate(1, fecha);
                stmt.setTime(2, hora_inicio);
                stmt.setTime(3, hora_fin);
                stmt.setInt(4, precio_total);
                stmt.setInt(5, id_cita);
                stmt.executeUpdate();

                System.out.println("Cita actualizada.");
            
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }

    //DELETE
    public void delete(int id_cita){
        String sql = "DELETE FROM citas WHERE id_cita = ?";

        try (Connection con = conexion.conectar();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            
                stmt.setInt(1, id_cita);
                stmt.executeUpdate();

                 System.out.println("Cita eliminada.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

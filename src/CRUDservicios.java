/*
Table: servicios
Columns:
id_servicio varchar(50) PK
descripcion varchar(200) 
duracion int 
precio int
 */

import java.sql.*;

public class CRUDservicios {

    conexion conexion = new conexion();

    // CREATE
    public void create(String id_servicio, String descripcion, int duracion, int precio) {
        String sql = "INSERT INTO servicios (id_servicio, descripcion, duracion, precio) VALUES (?, ?, ?, ?)";

        try (Connection con = conexion.conectar();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, id_servicio);
            stmt.setString(2, descripcion);
            stmt.setInt(3, duracion);
            stmt.setInt(4, precio);
            stmt.executeUpdate();

            System.out.println("Servicio creado exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public void read() {
        String sql = "SELECT * FROM servicios";

        try (Connection con = conexion.conectar();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("id_servicio: " + rs.getString("id_servicio"));
                System.out.println("descripcion: " + rs.getString("descripcion"));
                System.out.println("duracion: " + rs.getInt("duracion"));
                System.out.println("precio: " + rs.getInt("precio"));
                System.out.println("-------------");
            }

            System.out.println("Servicios obtenidos.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void update(String id_servicio, String descripcion, int duracion, int precio) {
        String sql = "UPDATE servicios SET descripcion = ?, duracion = ?, precio = ? WHERE id_servicio = ?";

        try (Connection con = conexion.conectar();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, descripcion);
            stmt.setInt(2, duracion);
            stmt.setInt(3, precio);
            stmt.setString(4, id_servicio);
            stmt.executeUpdate();

            System.out.println("Servicio actualizado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(String id_servicio) {
        String sql = "DELETE FROM servicios WHERE id_servicio = ?";

        try (Connection con = conexion.conectar();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, id_servicio);
            stmt.executeUpdate();

            System.out.println("Servicio eliminado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

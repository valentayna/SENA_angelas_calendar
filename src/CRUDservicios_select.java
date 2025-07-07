/*
Table: servicios_select
Columns:
id_servicios_select int AI PK 
id_cliente int 
id_servicio varchar(50) 
id_cita int
*/

import java.sql.*;

public class CRUDservicios_select {

    conexion conexion = new conexion();

    // CREATE
    public void create(int id_cliente, String id_servicio, int id_cita) {
        String sql = "INSERT INTO servicios_select (id_cliente, id_servicio, id_cita) VALUES (?, ?, ?)";

        try (Connection con = conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id_cliente);
            stmt.setString(2, id_servicio);
            stmt.setInt(3, id_cita);
            stmt.executeUpdate();

            System.out.println("Registro en servicios_select creado exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public void read() {
        String sql = "SELECT * FROM servicios_select";

        try (Connection con = conexion.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("id_servicios_select: " + rs.getInt("id_servicios_select"));
                System.out.println("id_cliente: " + rs.getInt("id_cliente"));
                System.out.println("id_servicio: " + rs.getString("id_servicio"));
                System.out.println("id_cita: " + rs.getInt("id_cita"));
                System.out.println("-------------");
            }

            System.out.println("Registros obtenidos de servicios_select.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void update(int id_servicios_select, String id_servicio) {
        String sql = "UPDATE servicios_select SET id_servicio = ? WHERE id_servicios_select = ?";

        try (Connection con = conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, id_servicio);
            stmt.setInt(2, id_servicios_select);
            stmt.executeUpdate();

            System.out.println("Registro de servicios_select actualizado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(int id_servicios_select) {
        String sql = "DELETE FROM servicios_select WHERE id_servicios_select = ?";

        try (Connection con = conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id_servicios_select);
            stmt.executeUpdate();

            System.out.println("Registro de servicios_select eliminado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


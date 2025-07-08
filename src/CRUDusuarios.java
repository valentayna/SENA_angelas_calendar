/*
Table: usuarios
Columns:
correo varchar(200) pk
nombre varchar(50) 
contraseña varchar(50)
rol varchar(50)
 */

import java.sql.*;

public class CRUDusuarios {

    conexion conexion = new conexion();

    // CREATE
    public void create(String correo, String nombre, String contraseña, String rol) {
        String sql = "INSERT INTO usuarios (correo, nombre, contraseña, rol) VALUES (?, ?, ?, ?)";

        try (Connection con = conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, nombre);
            stmt.setString(3, contraseña);
            stmt.setString(4, rol);
            stmt.executeUpdate();

            System.out.println("Usuario creado exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public void read() {
        String sql = "SELECT * FROM usuarios";

        try (Connection con = conexion.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("correo: " + rs.getString("correo"));
                System.out.println("nombre: " + rs.getString("nombre"));
                System.out.println("contraseña: " + rs.getString("contraseña"));
                System.out.println("Rol: " + rs.getString("rol"));
                System.out.println("-------------");
            }

            System.out.println("Usuarios obtenidos.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void update(String correo_actual, String correo, String nombre, String contraseña) {
        String sql = "UPDATE usuarios SET correo = ?, nombre = ?, contraseña = ? WHERE correo_actual = ?";

        try (Connection con = conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, nombre);
            stmt.setString(3, contraseña);
            stmt.setString(4, correo_actual);
            stmt.executeUpdate();

            System.out.println("Usuario actualizado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(String correo) {
        String sql = "DELETE FROM usuarios WHERE correo = ?";

        try (Connection con = conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.executeUpdate();

            System.out.println("Usuario eliminado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

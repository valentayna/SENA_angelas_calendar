/*
Table: usuarios
Columns:
id_usuario int PK AI
correo varchar(200) 
nombre varchar(50) 
contraseña varchar(50)
 */

import java.sql.*;

public class CRUDusuarios {

    conexion conexion = new conexion();

    // CREATE
    public void create(String correo, String nombre, String contraseña) {
        String sql = "INSERT INTO usuarios (correo, nombre, contraseña) VALUES (?, ?, ?)";

        try (Connection con = conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, nombre);
            stmt.setString(3, contraseña);
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
                System.out.println("id_usuario: " + rs.getInt("id_usuario"));
                System.out.println("correo: " + rs.getString("correo"));
                System.out.println("nombre: " + rs.getString("nombre"));
                System.out.println("contraseña: " + rs.getString("contraseña"));
                System.out.println("-------------");
            }

            System.out.println("Usuarios obtenidos.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void update(int id_usuario, String correo, String nombre, String contraseña) {
        String sql = "UPDATE usuarios SET correo = ?, nombre = ?, contraseña = ? WHERE id_usuario = ?";

        try (Connection con = conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, nombre);
            stmt.setString(3, contraseña);
            stmt.setInt(4, id_usuario);
            stmt.executeUpdate();

            System.out.println("Usuario actualizado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(int id_usuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (Connection con = conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id_usuario);
            stmt.executeUpdate();

            System.out.println("Usuario eliminado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

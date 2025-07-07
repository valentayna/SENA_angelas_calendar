import java.util.Scanner;

public class TestCRUDusuarios {

    public static void main(String[] args) {

        CRUDusuarios crud = new CRUDusuarios();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ CRUD USUARIOS ---");
            System.out.println("1. Crear usuario");
            System.out.println("2. Leer usuarios");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String contrasena = scanner.nextLine();
                    crud.create(correo, nombre, contrasena);
                    break;

                case 2:
                    crud.read();
                    break;

                case 3:
                    System.out.print("ID del usuario a actualizar: ");
                    int idUpdate = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nuevo correo: ");
                    String nuevoCorreo = scanner.nextLine();
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Nueva contraseña: ");
                    String nuevaContrasena = scanner.nextLine();
                    crud.update(idUpdate, nuevoCorreo, nuevoNombre, nuevaContrasena);
                    break;

                case 4:
                    System.out.print("ID del usuario a eliminar: ");
                    int idDelete = scanner.nextInt();
                    scanner.nextLine();
                    crud.delete(idDelete);
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        scanner.close();
    }

}

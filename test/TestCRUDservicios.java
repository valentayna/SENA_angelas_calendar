import java.util.Scanner;

public class TestCRUDservicios {

    public static void main(String[] args) {

        CRUDservicios crud = new CRUDservicios();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ CRUD SERVICIOS ---");
            System.out.println("1. Crear servicio");
            System.out.println("2. Leer servicios");
            System.out.println("3. Actualizar servicio");
            System.out.println("4. Eliminar servicio");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("ID del servicio: ");
                    String id = scanner.nextLine();
                    System.out.print("Descripción: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("Duración (en minutos): ");
                    int duracion = scanner.nextInt();
                    System.out.print("Precio: ");
                    int precio = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer
                    crud.create(id, descripcion, duracion, precio);
                    break;

                case 2:
                    crud.read();
                    break;

                case 3:
                    System.out.print("ID del servicio a actualizar: ");
                    String idUpdate = scanner.nextLine();
                    System.out.print("Nueva descripción: ");
                    String nuevaDescripcion = scanner.nextLine();
                    System.out.print("Nueva duración: ");
                    int nuevaDuracion = scanner.nextInt();
                    System.out.print("Nuevo precio: ");
                    int nuevoPrecio = scanner.nextInt();
                    scanner.nextLine();
                    crud.update(idUpdate, nuevaDescripcion, nuevaDuracion, nuevoPrecio);
                    break;

                case 4:
                    System.out.print("ID del servicio a eliminar: ");
                    String idDelete = scanner.nextLine();
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

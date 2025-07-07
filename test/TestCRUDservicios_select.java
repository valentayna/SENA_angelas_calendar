import java.util.Scanner;

public class TestCRUDservicios_select {

    public static void main(String[] args) {
        CRUDservicios_select crud = new CRUDservicios_select();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ CRUD SERVICIOS_SELECT ---");
            System.out.println("1. Crear registro");
            System.out.println("2. Leer registros");
            System.out.println("3. Actualizar servicio por ID");
            System.out.println("4. Eliminar registro");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("ID del cliente: ");
                    int idCliente = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("ID del servicio: ");
                    String idServicio = scanner.nextLine();
                    System.out.print("ID de la cita: ");
                    int idCita = scanner.nextInt();
                    crud.create(idCliente, idServicio, idCita);
                    break;

                case 2:
                    crud.read();
                    break;

                case 3:
                    System.out.print("ID del registro a actualizar: ");
                    int idSelect = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nuevo ID del servicio: ");
                    String nuevoServicio = scanner.nextLine();
                    crud.update(idSelect, nuevoServicio);
                    break;

                case 4:
                    System.out.print("ID del registro a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    crud.delete(idEliminar);
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}

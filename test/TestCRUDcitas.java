import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;

public class TestCRUDcitas {

    public static void main(String[] args) {
        CRUDcitas crud = new CRUDcitas();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ CRUD CITAS ---");
            System.out.println("1. Crear cita");
            System.out.println("2. Leer citas");
            System.out.println("3. Actualizar cita");
            System.out.println("4. Eliminar cita");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("ID del cliente: ");
                    int idCliente = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Fecha (AAAA-MM-DD): ");
                    Date fecha = Date.valueOf(scanner.nextLine());
                    System.out.print("Hora de inicio (HH:MM:SS): ");
                    Time horaInicio = Time.valueOf(scanner.nextLine());
                    System.out.print("Hora de fin (HH:MM:SS): ");
                    Time horaFin = Time.valueOf(scanner.nextLine());
                    System.out.print("Precio total: ");
                    int precio = scanner.nextInt();
                    crud.create(idCliente, fecha, horaInicio, horaFin, precio);
                    break;

                case 2:
                    crud.read();
                    break;

                case 3:
                    System.out.print("ID de la cita a actualizar: ");
                    int idCita = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nueva fecha (AAAA-MM-DD): ");
                    Date nuevaFecha = Date.valueOf(scanner.nextLine());
                    System.out.print("Nueva hora de inicio (HH:MM:SS): ");
                    Time nuevaHoraInicio = Time.valueOf(scanner.nextLine());
                    System.out.print("Nueva hora de fin (HH:MM:SS): ");
                    Time nuevaHoraFin = Time.valueOf(scanner.nextLine());
                    System.out.print("Nuevo precio total: ");
                    int nuevoPrecio = scanner.nextInt();
                    crud.update(idCita, nuevaFecha, nuevaHoraInicio, nuevaHoraFin, nuevoPrecio);
                    break;

                case 4:
                    System.out.print("ID de la cita a eliminar: ");
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

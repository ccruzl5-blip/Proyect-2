import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RegistroAtletas registroAtletas = new RegistroAtletas();
        RegistroEntrenamientos registroEntrenamientos = new RegistroEntrenamientos();
        HistorialEntrenamientos historial = new HistorialEntrenamientos();
        Estadisticas estadisticas = new Estadisticas();
        GestorDB gestorDB = new GestorDB();


        registroAtletas.cargarDatosDesdeDB();

        int opcion;
        do {
            System.out.println("\n--- Sistema de Monitoreo de Atletas (MariaDB) ---");
            System.out.println("1. Registrar nuevo atleta");
            System.out.println("2. Registrar sesión de entrenamiento");
            System.out.println("3. Mostrar historial de un atleta");
            System.out.println("4. Calcular y mostrar estadísticas");
            System.out.println("5. Registrar pago en planilla");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    registrarAtleta(scanner, registroAtletas);
                    break;
                case 2:
                    registrarSesion(scanner, registroAtletas, registroEntrenamientos);
                    break;
                case 3:
                    mostrarHistorial(scanner, registroAtletas, historial);
                    break;
                case 4:
                    mostrarEstadisticas(scanner, registroAtletas, estadisticas);
                    break;
                case 5:
                    registrarPagoPlanilla(scanner, registroAtletas, gestorDB);
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    private static void registrarAtleta(Scanner scanner, RegistroAtletas registro) {
        System.out.print("Nombre del atleta: ");
        String nombre = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Disciplina: ");
        String disciplina = scanner.nextLine();
        System.out.print("Departamento: ");
        String departamento = scanner.nextLine();

        Atleta nuevoAtleta = new Atleta(nombre, edad, disciplina, departamento);
        registro.agregarAtleta(nuevoAtleta);
        System.out.println("Atleta registrado con éxito en la base de datos.");
    }

    private static void registrarSesion(Scanner scanner, RegistroAtletas registro, RegistroEntrenamientos regEntrenamiento) {
        System.out.print("Nombre del atleta para registrar sesión: ");
        String nombre = scanner.nextLine();
        Atleta atleta = registro.buscarAtleta(nombre);

        if (atleta == null) {
            System.out.println("Atleta no encontrado.");
            return;
        }

        try {
            System.out.print("Fecha del entrenamiento (YYYY-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());
            System.out.print("Tipo de entrenamiento: ");
            String tipo = scanner.nextLine();
            System.out.print("Valor/Marca: ");
            double valor = scanner.nextDouble();
            scanner.nextLine();

            regEntrenamiento.registrarNuevaSesion(atleta, fecha, tipo, valor);
        } catch (Exception e) {
            System.err.println("Error en los datos ingresados: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void registrarPagoPlanilla(Scanner scanner, RegistroAtletas registro, GestorDB gestorDB) {
        System.out.print("Nombre del atleta para registrar pago: ");
        String nombre = scanner.nextLine();
        Atleta atleta = registro.buscarAtleta(nombre);

        if (atleta == null) {
            System.out.println("Atleta no encontrado.");
            return;
        }

        try {
            System.out.print("Fecha de pago (YYYY-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());
            System.out.print("Monto del pago: ");
            double monto = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Estado del pago (Ej. Pagado, Pendiente): ");
            String estado = scanner.nextLine();

            Planilla nuevoPago = new Planilla(atleta.getId(), fecha, monto, estado);
            gestorDB.insertarPagoPlanilla(nuevoPago);
            System.out.println("Pago registrado exitosamente en la planilla.");

        } catch (Exception e) {
            System.err.println("Error en los datos ingresados: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void mostrarHistorial(Scanner scanner, RegistroAtletas registro, HistorialEntrenamientos historial) {
        System.out.print("Nombre del atleta para ver historial: ");
        String nombre = scanner.nextLine();
        Atleta atleta = registro.buscarAtleta(nombre);
        historial.mostrarHistorial(atleta);
    }

    private static void mostrarEstadisticas(Scanner scanner, RegistroAtletas registro, Estadisticas estadisticas) {
        System.out.print("Nombre del atleta para ver estadísticas: ");
        String nombre = scanner.nextLine();
        Atleta atleta = registro.buscarAtleta(nombre);
        estadisticas.mostrarEstadisticas(atleta);
    }
}
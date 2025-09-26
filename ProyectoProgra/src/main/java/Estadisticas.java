import java.util.Comparator;
import java.util.List;

public class Estadisticas {

    public void mostrarEstadisticas(Atleta atleta) {
        if (atleta == null || atleta.getEntrenamientos().isEmpty()) {
            System.out.println("No hay datos de entrenamiento para mostrar estadísticas.");
            return;
        }

        List<Entrenamiento> entrenamientos = atleta.getEntrenamientos();


        double suma = 0;
        for (Entrenamiento e : entrenamientos) {
            suma += e.getValor();
        }
        double promedio = suma / entrenamientos.size();
        System.out.println("\n--- Estadísticas para " + atleta.getNombre() + " ---");
        System.out.printf("Promedio de Rendimiento: %.2f\n", promedio);


        Entrenamiento mejorMarca = entrenamientos.stream()
                .max(Comparator.comparingDouble(Entrenamiento::getValor))
                .orElse(null);
        System.out.println("Mejor Marca Registrada: " + (mejorMarca != null ? mejorMarca : "N/A"));


        System.out.println("\n--- Evolución en el Tiempo ---");
        entrenamientos.stream()
                .sorted(Comparator.comparing(Entrenamiento::getFecha))
                .forEach(System.out::println);
    }
}
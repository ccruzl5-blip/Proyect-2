public class HistorialEntrenamientos {

    public void mostrarHistorial(Atleta atleta) {
        if (atleta == null) {
            System.out.println("Atleta no encontrado.");
            return;
        }

        System.out.println("\n--- Historial de Entrenamientos de " + atleta.getNombre() + " ---");
        if (atleta.getEntrenamientos().isEmpty()) {
            System.out.println("Este atleta aún no tiene entrenamientos registrados.");
        } else {
            for (Entrenamiento e : atleta.getEntrenamientos()) {
                System.out.println(e);
            }
        }
    }
}
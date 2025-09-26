import java.time.LocalDate;

public class RegistroEntrenamientos {
    private GestorDB gestorDB;

    public RegistroEntrenamientos() {
        this.gestorDB = new GestorDB();
    }

    public void registrarNuevaSesion(Atleta atleta, LocalDate fecha, String tipo, double valor) {
        if (atleta != null) {
            Entrenamiento nuevoEntrenamiento = new Entrenamiento(fecha, tipo, valor);
            nuevoEntrenamiento.setIdAtleta(atleta.getId());


            gestorDB.insertarEntrenamiento(nuevoEntrenamiento);

            atleta.agregarEntrenamiento(nuevoEntrenamiento);

            System.out.println("Entrenamiento registrado para " + atleta.getNombre());
        } else {
            System.out.println("Error: No se pudo registrar el entrenamiento porque el atleta no existe.");
        }
    }
}
import java.util.ArrayList;
import java.util.List;

public class RegistroAtletas {
    private List<Atleta> atletas;
    private GestorDB gestorDB;

    public RegistroAtletas() {
        this.gestorDB = new GestorDB();
        this.atletas = new ArrayList<>();
    }

    public void cargarDatosDesdeDB() {
        this.atletas = gestorDB.cargarAtletas();
        System.out.println("Datos cargados desde la base de datos.");
    }

    public void agregarAtleta(Atleta atleta) {

        Atleta atletaConId = gestorDB.insertarAtleta(atleta);

        atletas.add(atletaConId);
    }

    public Atleta buscarAtleta(String nombre) {
        for (Atleta atleta : atletas) {
            if (atleta.getNombre().equalsIgnoreCase(nombre)) {
                return atleta;
            }
        }
        return null;
    }

    public List<Atleta> getAtletas() {
        return atletas;
    }
}
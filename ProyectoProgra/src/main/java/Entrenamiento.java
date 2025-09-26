import java.time.LocalDate;

public class Entrenamiento {
    private int id;
    private int idAtleta;
    private LocalDate fecha;
    private String tipo;
    private double valor;

    public Entrenamiento(LocalDate fecha, String tipo, double valor) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.valor = valor;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdAtleta() { return idAtleta; }
    public void setIdAtleta(int idAtleta) { this.idAtleta = idAtleta; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    @Override
    public String toString() {
        return "Fecha: " + fecha + ", Tipo: '" + tipo + "', Marca: " + valor;
    }
}
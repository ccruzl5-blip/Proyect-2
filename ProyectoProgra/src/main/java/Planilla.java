import java.time.LocalDate;

public class Planilla {
    private int id;
    private int idAtleta;
    private LocalDate fechaPago;
    private double monto;
    private String estado;

    public Planilla(int idAtleta, LocalDate fechaPago, double monto, String estado) {
        this.idAtleta = idAtleta;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.estado = estado;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdAtleta() { return idAtleta; }
    public void setIdAtleta(int idAtleta) { this.idAtleta = idAtleta; }
    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
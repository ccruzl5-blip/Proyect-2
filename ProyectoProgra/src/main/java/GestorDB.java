import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorDB {
    private static final String URL = "jdbc:mariadb://localhost:3306/proyecto_atletas";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "Coquit02006";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }

    public Atleta insertarAtleta(Atleta atleta) {
        String sql = "INSERT INTO atletas (nombre, edad, disciplina, departamento) VALUES (?, ?, ?, ?)";

        // Usamos try-with-resources para asegurar que la conexión se cierre siempre
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, atleta.getNombre());
            pstmt.setInt(2, atleta.getEdad());
            pstmt.setString(3, atleta.getDisciplina());
            pstmt.setString(4, atleta.getDepartamento());

            // 1. Verificamos si la inserción fue exitosa
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas == 0) {
                // Si no se afectaron filas, algo salió mal.
                throw new SQLException("La creación del atleta falló, no se afectaron filas.");
            }

            System.out.println("¡Inserción en DB exitosa! Filas afectadas: " + filasAfectadas);

            // 2. Obtenemos el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    atleta.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La creación del atleta falló, no se pudo obtener el ID.");
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR CRÍTICO al insertar atleta: " + e.getMessage());
            e.printStackTrace(); // Imprime el rastro completo del error
            return null; // Devolvemos null para indicar que falló
        }
        return atleta;
    }


    public List<Atleta> cargarAtletas() {
        List<Atleta> atletas = new ArrayList<>();
        String sql = "SELECT * FROM atletas";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Atleta atleta = new Atleta(
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("disciplina"),
                        rs.getString("departamento")
                );
                atleta.setId(rs.getInt("id_atleta"));
                cargarEntrenamientosParaAtleta(atleta);
                atletas.add(atleta);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar atletas: " + e.getMessage());
            e.printStackTrace();
        }
        return atletas;
    }

    public void cargarEntrenamientosParaAtleta(Atleta atleta) {
        String sql = "SELECT * FROM entrenamiento WHERE id_atleta = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, atleta.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Entrenamiento entrenamiento = new Entrenamiento(
                            rs.getDate("fecha").toLocalDate(),
                            rs.getString("tipo"),
                            rs.getDouble("valor")
                    );
                    entrenamiento.setIdAtleta(atleta.getId());
                    entrenamiento.setId(rs.getInt("id_entrenamiento"));
                    atleta.agregarEntrenamiento(entrenamiento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar entrenamientos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertarEntrenamiento(Entrenamiento entrenamiento) {
        String sql = "INSERT INTO entrenamiento (id_atleta, fecha, tipo, valor) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, entrenamiento.getIdAtleta());
            pstmt.setDate(2, Date.valueOf(entrenamiento.getFecha()));
            pstmt.setString(3, entrenamiento.getTipo());
            pstmt.setDouble(4, entrenamiento.getValor());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar entrenamiento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertarPagoPlanilla(Planilla planilla) {
        String sql = "INSERT INTO planilla (id_atleta, fecha_pago, monto, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, planilla.getIdAtleta());
            pstmt.setDate(2, Date.valueOf(planilla.getFechaPago()));
            pstmt.setDouble(3, planilla.getMonto());
            pstmt.setString(4, planilla.getEstado());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar pago de planilla: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
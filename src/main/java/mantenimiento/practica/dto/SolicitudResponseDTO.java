package mantenimiento.practica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "DTO que representa la respuesta de una solicitud")
public class SolicitudResponseDTO {

    private Long id;
    private String descripcion;
    private String estado;
    private String nombreCliente;
    private String nombreTecnico;
    private LocalDate fechaCreacion;
    private int versionHistorico; // Mapeamos tu campo 'historico'

    // Getters y Setters
    // ... (Genera los getters y setters para estos campos)
}
package mantenimiento.practica.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la creación de una nueva solicitud")
public class SolicitudRequestDTO {

    @Schema(description = "Descripción detallada de la incidencia", example = "El servidor no arranca")
    private String descripcion;

    @Schema(description = "ID del cliente que realiza la solicitud", example = "101")
    private Long clienteId;

    // Getters y Setters
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}
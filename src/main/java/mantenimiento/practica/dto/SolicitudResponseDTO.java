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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getVersionHistorico() {
        return versionHistorico;
    }

    public void setVersionHistorico(int versionHistorico) {
        this.versionHistorico = versionHistorico;
    }
}
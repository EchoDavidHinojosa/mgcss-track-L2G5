package mantenimiento.practica.api;

import jakarta.validation.constraints.NotBlank;

public class SolicitudRequestDTO {
	
	@NotBlank(message = "Debes indicar una descripción en la solicitud")
    private String descripcion;

    public SolicitudRequestDTO() {}

    public SolicitudRequestDTO(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}

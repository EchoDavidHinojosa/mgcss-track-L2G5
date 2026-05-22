package mantenimiento.practica.api;

import jakarta.validation.constraints.NotBlank;

public class CambiarEstadoRequestDTO {

    @NotBlank(message = "Debes indicar el nuevo estado de la solicitud")
    private String estado;

    public CambiarEstadoRequestDTO() {
    }

    public CambiarEstadoRequestDTO(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
package mantenimiento.practica.api;

import jakarta.validation.constraints.NotNull;

public class AsignarTecnicoRequestDTO {

    @NotNull(message = "Debes indicar el identificador del técnico")
    private Long tecnicoId;

    public AsignarTecnicoRequestDTO() {
    }

    public AsignarTecnicoRequestDTO(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public void setTecnicoId(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }
}
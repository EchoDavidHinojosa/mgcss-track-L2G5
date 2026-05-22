package mantenimiento.practica.api;

import mantenimiento.practica.domain.solicitud;

public class SolicitudMapper {

    public SolicitudResponseDTO toResponseDTO(solicitud solicitud) {
        if (solicitud == null) {
            return null;
        }

        return new SolicitudResponseDTO(
                solicitud.getId(),
                solicitud.getDescripcion(),
                solicitud.getEstado().name()
        );
    }
}
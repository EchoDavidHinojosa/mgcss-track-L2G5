package mantenimiento.practica.mapper;

import mantenimiento.practica.domain.solicitud;
import mantenimiento.practica.dto.SolicitudResponseDTO;

public class SolicitudMapper {
    public static SolicitudResponseDTO toResponseDTO(solicitud s) {
        if (s == null) return null;
        SolicitudResponseDTO dto = new SolicitudResponseDTO();
        dto.setId(s.getId());
        dto.setDescripcion(s.getDescripcion());
        dto.setEstado(s.getEstado().name());
        dto.setFechaCreacion(s.getFechaCreacion());
        dto.setVersionHistorico(s.getHistorico());
        if (s.getTecnicoAsignado() != null) {
            dto.setNombreTecnico(s.getTecnicoAsignado().getNombre());
        }
        return dto;
    }
}

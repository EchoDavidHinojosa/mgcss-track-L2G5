package mantenimiento.practica.service;

import mantenimiento.practica.domain.EstadoSolicitud;
import mantenimiento.practica.domain.Solicitud;
import mantenimiento.practica.domain.SolicitudRepository;
import mantenimiento.practica.domain.Tecnico;

public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public Solicitud crearSolicitud(Solicitud solicitud) {
        return solicitudRepository.save(solicitud);
    }

    public Solicitud asignarTecnico(Long solicitudId, Tecnico tecnico) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.asignarTecnico(tecnico);
        return solicitudRepository.save(solicitud);
    }

    public Solicitud cambiarEstado(Long solicitudId, EstadoSolicitud nuevoEstado) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.cambiarEstado(nuevoEstado);
        return solicitudRepository.save(solicitud);
    }
}
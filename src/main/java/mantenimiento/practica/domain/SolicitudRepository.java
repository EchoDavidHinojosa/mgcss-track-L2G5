package mantenimiento.practica.domain;

import java.util.Optional;

public interface SolicitudRepository {

    solicitud save(solicitud solicitud);

    Optional<solicitud> findById(Long id);
}
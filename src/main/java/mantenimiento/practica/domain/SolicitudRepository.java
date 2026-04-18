package mantenimiento.practica.domain;

import java.util.Optional;

public interface SolicitudRepository {

    Solicitud save(Solicitud solicitud);

    Optional<Solicitud> findById(Long id);
}
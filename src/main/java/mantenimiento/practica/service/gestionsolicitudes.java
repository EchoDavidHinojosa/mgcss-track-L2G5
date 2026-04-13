package mantenimiento.practica.service;

import mantenimiento.practica.domain.cliente;
import mantenimiento.practica.domain.solicitud;
import mantenimiento.practica.domain.tecnico;
import mantenimiento.practica.domain.estadoSolicitud; // Importamos el Enum

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class gestionsolicitudes {

    private List<solicitud> solicitudes = new ArrayList<>();
    private Long siguienteId = 1L;

    public solicitud crearSolicitud(cliente cliente, String descripcion) {
        solicitud nueva = new solicitud(siguienteId, cliente, descripcion);
        siguienteId++;
        solicitudes.add(nueva);
        return nueva;
    }

    public boolean asignarTecnico(Long idSolicitud, tecnico tecnico) {//False si no lo asigna true si sí
        if (!tecnico.isActivo()) {
            System.out.println("Tecnico no activo ");
            return false;
        }

        for (solicitud s : solicitudes) {
            if (s.getId().equals(idSolicitud)) {
                s.setTecnicoAsignado(tecnico);
                // Cambiamos el estado usando el Enum
                s.setEstado(estadoSolicitud.EN_PROCESO);
                return true;
            }
        }
        return false;
    }

    public boolean cerrarSolicitud(Long idSolicitud) {
        for (solicitud s : solicitudes) {
            if (s.getId().equals(idSolicitud)) {

                // Comparamos usando el Enum directamente
                if (s.getEstado() == estadoSolicitud.EN_PROCESO) {
                    s.setEstado(estadoSolicitud.CERRADA);
                    s.setFechaCierre(LocalDate.now());
                    return true;
                } else {
                    System.out.println("Error: La solicitud debe estar EN_PROCESO para poder cerrarse.");
                    return false;
                }
            }
        }
        return false;
    }

    public solicitud consultarSolicitud(Long id) {
        for (solicitud s : solicitudes) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public List<solicitud> listarSolicitudes() {
        return solicitudes;
    }
}
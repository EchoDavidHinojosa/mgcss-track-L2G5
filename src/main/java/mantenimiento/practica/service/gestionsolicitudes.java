package mantenimiento.practica.service;

import mantenimiento.practica.domain.cliente;
import mantenimiento.practica.domain.solicitud;
import mantenimiento.practica.domain.tecnico;
import mantenimiento.practica.domain.estadoSolicitud; // Importamos el Enum

import java.time.LocalDate;
import java.util.*;

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

    //Modificamos este método para que vaya dejando las solicitudes como histórico y en las solicitudes vamos a teer un int
    // que marque cual es la versión más actualizada de la solicitud.

    public boolean cerrarSolicitud(Long idSolicitud) {
        List<solicitud> tmp = new ArrayList<>();
        for (solicitud s : solicitudes) {
            if (s.getId().equals(idSolicitud)) {
                tmp.add(s);
            }
        }
        solicitud MasReciente = Collections.max(
                tmp,
                Comparator.comparingInt(solicitud::getHistorico)
        );
        if (MasReciente !=null){

            if (MasReciente.getEstado() == estadoSolicitud.EN_PROCESO) {
                solicitud registro= new solicitud(MasReciente);
                registro.setEstado(estadoSolicitud.CERRADA);
                registro.setFechaCierre(LocalDate.now());
                this.solicitudes.add(registro);
                return true;
            } else {
                System.out.println("Error: La solicitud debe estar EN_PROCESO para poder cerrarse.");
                return false;
            }
        }
        return false;
    }


    public boolean rearbirSolicitud(Long idSolicitud){
        List<solicitud> tmp = new ArrayList<>();
        boolean encontrada=false;
        for (solicitud s : solicitudes) {
            if (s.getId().equals(idSolicitud)) {
                tmp.add(s);
                encontrada=true;
            }
        }
        solicitud MasReciente = Collections.max(
                tmp,
                Comparator.comparingInt(solicitud::getHistorico)
        );
        if (encontrada==false){
            return false;
        }
        if (MasReciente !=null){

            if (MasReciente.getEstado() == estadoSolicitud.CERRADA) {
                solicitud registro= new solicitud(MasReciente);
                registro.setEstado(estadoSolicitud.EN_PROCESO);
                registro.setFechaCierre(LocalDate.now());
                this.solicitudes.add(registro);
                return true;
            } else {
                System.out.println("Error: La solicitud debe estar CERRADA para poder reabrise.");
                return false;
            }
        }
        return false;
    }

    public solicitud consultarSolicitud(Long id) {
        List<solicitud> tmp = new ArrayList<>();
        boolean encontrada=false;
        for (solicitud s : solicitudes) {
            if (s.getId().equals(id)) {
                tmp.add(s);
                encontrada=true;
            }
        }
        if (encontrada== false){
            return null;
        }
        solicitud MasReciente = Collections.max(
                tmp,
                Comparator.comparingInt(solicitud::getHistorico)
        );
        return MasReciente;
    }

    public List<solicitud> listarSolicitudes() {
        return solicitudes;
    }
}
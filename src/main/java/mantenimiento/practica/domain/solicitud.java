package mantenimiento.practica.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;

public class solicitud {


    private static final Logger logger = LoggerFactory.getLogger(solicitud.class);

    private Long id;
    private int historico; // Empieza en 0 y va aumentando conforme se van cambiando los estado,la más alta es la última actualización
    private cliente clienteAsignado;
    private String descripcion;
    private LocalDate fechaCreacion;
    private estadoSolicitud estado; // "ABIERTA", "EN_PROCESO", "CERRADA"
    private tecnico tecnicoAsignado;
    private LocalDate fechaCierre;


    public solicitud(Long id, cliente clienteAsignado, String descripcion) {
        this.id = id;
        this.clienteAsignado = clienteAsignado;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDate.now();
        this.estado = estadoSolicitud.ABIERTA;
        this.historico=0;
    }
    //Este constructor se usará para dejar registro cuando se cambie el estado de ahí que se aumente el histórico en 1
    public solicitud(solicitud s){
        this.id = s.getId();
        this.clienteAsignado = s.getClienteAsignado();
        this.descripcion = s.getDescripcion();
        this.fechaCreacion = s.getFechaCreacion();
        this.estado = s.getEstado();
        this.historico=s.getHistorico()+1;
    }

    public int getHistorico(){
        return this.historico;
    }

    public void setHistorico(int historico){
        this.historico=historico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public cliente getClienteAsignado() {
        return clienteAsignado;
    }

    public void setClienteAsignado(cliente clienteAsignado) {
        this.clienteAsignado = clienteAsignado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public estadoSolicitud getEstado() {
        return estado;
    }

    public boolean setEstado(estadoSolicitud estado) {

        if (estado == estadoSolicitud.CERRADA && this.estado != estadoSolicitud.EN_PROCESO) {

            logger.warn("No se puede hacer el cambio de estado a CERRADA. El estado actual es: {}", this.estado);
            return false;
        }
        this.estado = estado;
        return true;
    }

    public tecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public boolean setTecnicoAsignado(tecnico tecnicoAsignado) {

        if (tecnicoAsignado.isActivo()) {
            this.tecnicoAsignado = tecnicoAsignado;
            return true;
        } else {

            logger.warn("El tecnico no está disponible para ser asignado a la solicitud ID: {}", this.id);
            return false;
        }
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}
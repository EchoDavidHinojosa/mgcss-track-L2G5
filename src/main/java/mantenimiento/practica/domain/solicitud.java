package mantenimiento.practica.domain;


import java.time.LocalDate;

public class solicitud {

    private Long id;
    private cliente clienteAsignado;
    private String descripcion;
    private LocalDate fechaCreacion;
    private estadoSolicitud estado; // "ABIERTA", "EN_PROCESO", "CERRADA"
    private tecnico tecnicoAsignado;
    private LocalDate fechaCierre;

    public solicitud() {
    }

    public solicitud(Long id, cliente clienteAsignado, String descripcion) {
        this.id = id;
        this.clienteAsignado = clienteAsignado;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDate.now();
        this.estado = estadoSolicitud.ABIERTA;
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

    public void setEstado(estadoSolicitud estado) {
        this.estado = estado;
    }

    public tecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setTecnicoAsignado(tecnico tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}
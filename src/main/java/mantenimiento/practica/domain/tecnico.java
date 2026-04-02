package mantenimiento.practica.domain;


public class tecnico {
    private int id;
    private String nombre;
    private String especialidad;
    private boolean activo;

    public tecnico(int id, String nombre, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.activo = true; // Por defecto se crea activo [cite: 42]
    }


    public int getId() { return id; }

    public String getNombre() { return nombre; }

    public String getEspecialidad() { return especialidad; }

    public boolean isActivo() { return activo; }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s (%s) - %s",
                id, nombre, especialidad, activo ? "ACTIVO" : "INACTIVO");
    }
}
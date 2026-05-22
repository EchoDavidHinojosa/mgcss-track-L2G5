package mantenimiento.practica.domain;


public class cliente {

    private Long id;
    private String nombre;
    private String email;
    private boolean tipocliente;// true es premium

    public cliente() {

    }

    public cliente(Long id, String nombre, String email, boolean tipocliente) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipocliente = tipocliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getTipocliente() {
        return tipocliente;
    }

    public void setTipocliente(boolean tipocliente) {
        this.tipocliente = tipocliente;
    }
}
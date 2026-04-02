package mantenimiento.practica.service;

import java.util.ArrayList;
import java.util.List;

import mantenimiento.practica.domain.tecnico;

public class GestionTecnico {

    private List<tecnico> lista = new ArrayList<>();


    public void crearTecnico(int id, String nombre, String especialidad) {
        tecnico nuevo = new tecnico(id, nombre, especialidad);
        lista.add(nuevo);
    }


    public void cambiarEstado(int id, boolean activo) {
        for (tecnico t : lista) {
            if (t.getId() == id) {
                t.setActivo(activo);
                return;
            }
        }
    }


    public List<tecnico> listarDisponibles() {
        List<tecnico> disponibles = new ArrayList<>();
        for (tecnico t : lista) {
            if (t.isActivo()) {
                disponibles.add(t);
            }
        }
        return disponibles;
    }
}
package mantenimiento.practica.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mantenimiento.practica.domain.Tecnico;

public class GestorTecnico {

    private final List<Tecnico> tecnicos = new ArrayList<>();

    public void crearTecnico(Long id, String nombre, String especialidad) {
        Tecnico nuevo = new Tecnico(id, nombre, especialidad);
        tecnicos.add(nuevo);
    }

    public void cambiarEstado(Long id, boolean estado) {
        tecnicos.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .ifPresent(t -> t.setActivo(estado));
    }

    public List<Tecnico> listarDisponibles() {
        return tecnicos.stream()
                .filter(Tecnico::isActivo)
                .collect(Collectors.toList());
    }
}
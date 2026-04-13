package mantenimiento.practica.service;

import mantenimiento.practica.domain.cliente;

import java.util.ArrayList;
import java.util.List;

public class gestionclientes {

    private List<cliente> clientes = new ArrayList<>();
    private Long siguienteId = 1L;

    public cliente crearcliente(cliente cliente) {
        cliente.setId(siguienteId);
        siguienteId++;
        clientes.add(cliente);
        return cliente;
    }

    public cliente modificarcliente(Long id, cliente datosNuevos) {
        for (cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                cliente.setNombre(datosNuevos.getNombre());
                cliente.setEmail(datosNuevos.getEmail());
                cliente.setTipocliente(datosNuevos.getTipocliente());
                return cliente;
            }
        }
        return null;
    }

    public cliente consultarcliente(Long id) {
        for (cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        return null;
    }

    public List <cliente> listarclientes() {
        System.out.println("Clientes");
        for (cliente c : clientes) {

            System.out.println("id :" + c.getId() +
                    "  Nombre :" + c.getNombre() +
                    "  Email :" + c.getEmail());
        }

        return clientes;
    }
}
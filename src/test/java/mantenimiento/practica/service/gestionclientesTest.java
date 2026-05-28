package mantenimiento.practica.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import mantenimiento.practica.domain.cliente;
import java.util.List;

public class gestionclientesTest {

    @Test
    public void testCrearClienteDebeAsignarIdYGuardar() {
        gestionclientes gestor = new gestionclientes();
        cliente tipo = new cliente(); // Objeto para el tipo de cliente
        cliente nuevo = new cliente(null, "Ana López", "ana@correo.com", tipo.getTipocliente());

        cliente creado = gestor.crearcliente(nuevo);

        assertNotNull(creado.getId(), "El ID no debería ser null tras crear el cliente");
        assertEquals("Ana López", creado.getNombre());
        assertEquals(1, gestor.listarclientes().size());
    }

    @Test
    public void testModificarClienteDebeActualizarDatos() {
        gestionclientes gestor = new gestionclientes();
        cliente tipo = new cliente();
        cliente original = gestor.crearcliente(new cliente(null, "Pedro", "p@p.com", tipo.getTipocliente()));


        cliente datosNuevos = new cliente(null, "Pedro Reales", "pedro@nuevo.com", tipo.getTipocliente());

        cliente modificado = gestor.modificarcliente(original.getId(), datosNuevos);

        assertNotNull(modificado);
        assertEquals("Pedro Reales", modificado.getNombre());
        assertEquals("pedro@nuevo.com", modificado.getEmail());
    }

    @Test
    public void testConsultarClienteDebeRetornarCorrecto() {
        gestionclientes gestor = new gestionclientes();
        cliente tipo = new cliente();
        cliente guardado = gestor.crearcliente(new cliente(null, "Marta", "m@m.com", tipo.getTipocliente()));

        cliente consultado = gestor.consultarcliente(guardado.getId());

        assertNotNull(consultado);
        assertEquals("Marta", consultado.getNombre());
    }

    @Test
    public void testListarClientesDebeRetornarTodos() {
        gestionclientes gestor = new gestionclientes();
        cliente tipo = new cliente();

        gestor.crearcliente(new cliente(null, "User1", "u1@test.com", tipo.getTipocliente()));
        gestor.crearcliente(new cliente(null, "User2", "u2@test.com", tipo.getTipocliente()));

        List<cliente> lista = gestor.listarclientes();

        assertEquals(2, lista.size(), "La lista debería tener 2 clientes");
    }
}
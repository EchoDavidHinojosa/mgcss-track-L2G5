package mantenimiento.practica.service;

import mantenimiento.practica.domain.tecnico;
import org.junit.jupiter.api.Test;
import java.util.List;
import mantenimiento.practica.service.gestionTecnico;
import static org.junit.jupiter.api.Assertions.*;

public class gestionTecnicoTest {

    @Test
    public void testCrearTecnicoDebeAñadirALaLista() {
        // Preparar
        gestionTecnico gestor = new gestionTecnico();

        // Actuar
        gestor.crearTecnico(1, "Ramon", "Electricista");

        // Comprobar (usamos listarDisponibles porque los técnicos nacen activos por defecto)
        List<tecnico> activos = gestor.listarDisponibles();
        assertEquals(1, activos.size(), "La lista debería tener 1 técnico");
        assertEquals("Ramon", activos.get(0).getNombre());
    }

    @Test
    public void testCambiarEstadoDebeDesactivarTecnico() {
        // Preparar
        gestionTecnico gestor = new gestionTecnico();
        gestor.crearTecnico(1, "Ana", "Redes");

        // Actuar: La desactivamos
        gestor.cambiarEstado(1, false);

        // Comprobar
        List<tecnico> disponibles = gestor.listarDisponibles();
        assertTrue(disponibles.isEmpty(), "La lista de disponibles debería estar vacía tras desactivar al técnico");
    }

    @Test
    public void testListarDisponiblesSoloMuestraActivos() {
        // Preparar
        gestionTecnico gestor = new gestionTecnico();
        gestor.crearTecnico(1, "Tecnico Activo", "A");
        gestor.crearTecnico(2, "Tecnico Inactivo", "B");

        // Actuar
        gestor.cambiarEstado(2, false);
        List<tecnico> resultado = gestor.listarDisponibles();

        // Comprobar
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());
    }
}
package mantenimiento.practica.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class solicitudTest {

    @Test
    public void testConstructorInicializaValoresPorDefecto() {
        cliente c = new cliente();
        solicitud s = new solicitud(1L, c, "Pantalla rota");

        assertEquals(1L, s.getId(), "El ID debe ser 1");
        assertEquals(c, s.getClienteAsignado(), "El cliente debe coincidir");
        assertEquals("Pantalla rota", s.getDescripcion());
        assertEquals(estadoSolicitud.ABIERTA, s.getEstado(), "Al nacer, la solicitud debe ser ABIERTA");
        assertNotNull(s.getFechaCreacion(), "La fecha de creación no puede ser nula");
    }

    // ==========================================
    // TESTS PARA EL CAMBIO DE ESTADO
    // ==========================================

    @Test
    public void testSetEstadoNoPermiteCerrarDirectamente() {
        solicitud s = new solicitud(1L, new cliente(), "Error de red");

        // Actuar: Intentar pasar de ABIERTA (estado inicial) a CERRADA
        boolean resultado = s.setEstado(estadoSolicitud.CERRADA);

        // Comprobar: Debe fallar y quedarse en ABIERTA
        assertFalse(resultado, "No debería devolver true al intentar cerrar directamente");
        assertEquals(estadoSolicitud.ABIERTA, s.getEstado(), "El estado no debe haber cambiado");
    }

    @Test
    public void testSetEstadoPermiteCerrarSiEstaEnProceso() {
        solicitud s = new solicitud(1L, new cliente(), "Error de red");
        s.setEstado(estadoSolicitud.EN_PROCESO); // La ponemos en proceso primero

        // Actuar: Intentar cerrar
        boolean resultado = s.setEstado(estadoSolicitud.CERRADA);

        // Comprobar: Debe funcionar
        assertTrue(resultado, "Debería dejar cerrar si estaba EN_PROCESO");
        assertEquals(estadoSolicitud.CERRADA, s.getEstado());
    }

    @Test
    public void testSetEstadoPermitePasarAEnProceso() {
        solicitud s = new solicitud(1L, new cliente(), "Error de red");

        // Actuar: Pasar de ABIERTA a EN_PROCESO
        boolean resultado = s.setEstado(estadoSolicitud.EN_PROCESO);

        // Comprobar
        assertTrue(resultado);
        assertEquals(estadoSolicitud.EN_PROCESO, s.getEstado());
    }

    // ==========================================
    // TESTS PARA LA ASIGNACIÓN DE TÉCNICO
    // ==========================================

    @Test
    public void testSetTecnicoAsignadoPermiteTecnicoActivo() {
        solicitud s = new solicitud(1L, new cliente(), "Virus");
        tecnico t = new tecnico(1, "Mario", "Software");
        t.setActivo(true);

        // Actuar
        boolean resultado = s.setTecnicoAsignado(t);

        // Comprobar: Debe asignarse
        assertTrue(resultado, "Debe devolver true si el técnico está activo");
        assertNotNull(s.getTecnicoAsignado(), "El técnico no debe ser null");
        assertEquals("Mario", s.getTecnicoAsignado().getNombre());
    }

    @Test
    public void testSetTecnicoAsignadoNoPermiteTecnicoInactivo() {
        solicitud s = new solicitud(1L, new cliente(), "Virus");
        tecnico t = new tecnico(2, "Luis", "Hardware");
        t.setActivo(false); // Técnico inactivo

        // Actuar
        boolean resultado = s.setTecnicoAsignado(t);

        // Comprobar: No debe asignarse
        assertFalse(resultado, "Debe devolver false si el técnico está inactivo");
        assertNull(s.getTecnicoAsignado(), "El técnico de la solicitud debe seguir siendo null");
    }
}
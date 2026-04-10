package mantenimiento.practica.service;

import mantenimiento.practica.domain.solicitud;
import mantenimiento.practica.domain.cliente;
import mantenimiento.practica.domain.estadoSolicitud;
import mantenimiento.practica.domain.tecnico;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class gestionsolicitudesTest {

    @Test
    public void testCambioAEnProcesoDebeSerExitoso() {

        solicitud s = new solicitud(1L, new cliente(), "Lo que sea ");


        boolean resultado = s.setEstado(estadoSolicitud.EN_PROCESO);


        assertTrue(resultado, "Cambia a en proceso");
        assertEquals(estadoSolicitud.EN_PROCESO, s.getEstado());
    }

    @Test
    public void testCerrarSolicitudDirectamenteDebeFallar() {

        solicitud s = new solicitud(1L, new cliente(), "mondongo");


        boolean resultado = s.setEstado(estadoSolicitud.CERRADA);


        assertFalse(resultado, "debería e fallar");
        assertEquals(estadoSolicitud.ABIERTA, s.getEstado(), "no debe cambiar");
    }

    @Test
    public void testCerrarSolicitudDesdeEnProcesoDebeSerExitoso() {

        solicitud s = new solicitud(1L, new cliente(), "uhepa ");
        s.setEstado(estadoSolicitud.EN_PROCESO); // Primero la ponemos en proceso

        boolean resultado = s.setEstado(estadoSolicitud.CERRADA);


        assertTrue(resultado, "Ahora si se debe de cerrar");
        assertEquals(estadoSolicitud.CERRADA, s.getEstado());
    }

    @Test
    public void testAsignarTecnicoActivoDebeSerExitoso() {

        solicitud s = new solicitud(1L, new cliente(), "Reparación PC");
        tecnico ana = new tecnico(1, "Ana", "Hardware");
        ana.setActivo(true); // Nos aseguramos de que esté activa


        boolean resultado = s.setTecnicoAsignado(ana);


        assertTrue(resultado, "Debería permitir asignar un técnico activo");
        assertNotNull(s.getTecnicoAsignado());
        assertEquals("Ana", s.getTecnicoAsignado().getNombre());
    }

    @Test
    public void testAsignarTecnicoInactivoDebeFallar() {

        solicitud s = new solicitud(1L, new cliente(), "Reparación PC");
        tecnico pedro = new tecnico(2, "Pedro", "Software");
        pedro.setActivo(false);


        boolean resultado = s.setTecnicoAsignado(pedro);


        assertFalse(resultado, "No debería permitir asignar un técnico inactivo");
        assertNull(s.getTecnicoAsignado(), "El técnico asignado debe seguir siendo null");
    }

}
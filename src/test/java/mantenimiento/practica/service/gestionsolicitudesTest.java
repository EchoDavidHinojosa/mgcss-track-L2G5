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

    // ==========================================
    // LO QUE FALTABA (Pruebas del Gestor)
    // ==========================================

    @Test
    public void testCrearSolicitudDebeGenerarIdYGuardarEnLista() {
        gestionsolicitudes gestor = new gestionsolicitudes();
        solicitud nueva = gestor.crearSolicitud(new cliente(), "La pantalla parpadea");

        assertNotNull(nueva);
        assertEquals(1L, nueva.getId(), "El ID debe ser 1 para la primera solicitud");
        assertEquals(estadoSolicitud.ABIERTA, nueva.getEstado(), "Debe nacer en estado ABIERTA");
        assertEquals(1, gestor.listarSolicitudes().size(), "Debe haber 1 solicitud en la lista");
    }

    @Test
    public void testGestorAsignarTecnicoDebeCambiarEstadoAEnProceso() {
        gestionsolicitudes gestor = new gestionsolicitudes();
        solicitud s = gestor.crearSolicitud(new cliente(), "No hay internet");

        tecnico t = new tecnico(1, "Carlos", "Redes");
        t.setActivo(true);

        boolean asignado = gestor.asignarTecnico(s.getId(), t);

        assertTrue(asignado, "Debería devolver true al asignar");
        assertEquals(estadoSolicitud.EN_PROCESO, s.getEstado(), "El estado debe cambiar a EN_PROCESO");
        assertEquals("Carlos", s.getTecnicoAsignado().getNombre());
    }

    @Test
    public void testGestorCerrarSolicitudSoloSiEstaEnProceso() {
        gestionsolicitudes gestor = new gestionsolicitudes();
        solicitud s = gestor.crearSolicitud(new cliente(), "Virus");
        s.setHistorico(0);
        // 1. Intentamos cerrar directamente (Debe fallar)
        boolean cerradaFallo = gestor.cerrarSolicitud(s.getId());
        assertFalse(cerradaFallo, "No se puede cerrar si está ABIERTA");

        // 2. Asignamos técnico para pasarla a EN_PROCESO
        tecnico t = new tecnico(1, "Sara", "Seguridad");
        t.setActivo(true);
        gestor.asignarTecnico(s.getId(), t);

        // 3. Volvemos a intentar cerrar (Ahora debe funcionar)
        boolean cerradaExito = gestor.cerrarSolicitud(s.getId());

        assertTrue(cerradaExito, "Debería poder cerrarse ahora");
        solicitud solicitudActualizada=gestor.consultarSolicitud(s.getId());
        assertEquals(estadoSolicitud.CERRADA, solicitudActualizada.getEstado());
        assertNotNull(solicitudActualizada.getFechaCierre(), "Debe asignarse fecha de cierre");
    }

    @Test
    public void testReabrirSolicitudCerradaDebeSerExitoso() {
        gestionsolicitudes gestor = new gestionsolicitudes();

        solicitud s = gestor.crearSolicitud(new cliente(), "PC dañada");

        tecnico t = new tecnico(1, "Luis", "Hardware");
        t.setActivo(true);

        gestor.asignarTecnico(s.getId(), t);
        gestor.cerrarSolicitud(s.getId());

        boolean resultado = gestor.rearbirSolicitud(s.getId());

        assertTrue(resultado, "Debería permitir reabrir una solicitud cerrada");

        solicitud solicitudActualizada = gestor.consultarSolicitud(s.getId());

        assertEquals(
                estadoSolicitud.EN_PROCESO,
                solicitudActualizada.getEstado(),
                "La nueva solicitud debe quedar EN_PROCESO"
        );
    }

    @Test
    public void testReabrirSolicitudNoCerradaDebeFallar() {
        gestionsolicitudes gestor = new gestionsolicitudes();

        solicitud s = gestor.crearSolicitud(new cliente(), "Sin internet");

        boolean resultado = gestor.rearbirSolicitud(s.getId());

        assertFalse(resultado, "No debería reabrir solicitudes que no estén cerradas");

        solicitud solicitudActualizada = gestor.consultarSolicitud(s.getId());

        assertEquals(
                estadoSolicitud.ABIERTA,
                solicitudActualizada.getEstado()
        );
    }

    @Test
    public void testConsultarSolicitudDebeDevolverLaCorrectaONull() {
        gestionsolicitudes gestor = new gestionsolicitudes();
        solicitud s1 = gestor.crearSolicitud(new cliente(), "Problema 1");

        solicitud encontrada = gestor.consultarSolicitud(s1.getId());
        solicitud noEncontrada = gestor.consultarSolicitud(99L); // ID que no existe

        assertEquals(s1, encontrada);
        assertNull(noEncontrada, "Debe devolver null si el ID no existe");
    }
}
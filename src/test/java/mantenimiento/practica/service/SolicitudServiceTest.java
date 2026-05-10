package mantenimiento.practica.service;

import mantenimiento.practica.domain.cliente;
import mantenimiento.practica.domain.estadoSolicitud;
import mantenimiento.practica.domain.solicitud;
import mantenimiento.practica.domain.tecnico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @InjectMocks
    private gestionsolicitudes service; // La clase que proporcionaste

    @Mock
    private cliente clienteMock;

    @Mock
    private tecnico tecnicoMock;

    @BeforeEach
    void setUp() {
        // Aseguramos que el técnico esté activo para las pruebas de asignación
        lenient().when(tecnicoMock.isActivo()).thenReturn(true);
    }

    @Test
    void crearSolicitud_debeAñadirAListaYAsignarId() {
        // Ejecución: crearSolicitud recibe (cliente, String) según tu código
        solicitud resultado = service.crearSolicitud(clienteMock, "Fallo de red");

        // Verificación
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId()); // El primer ID es 1L
        assertEquals("Fallo de red", resultado.getDescripcion());
        assertEquals(1, service.listarSolicitudes().size());
    }

    @Test
    void asignarTecnico_debeCambiarEstadoAEnProceso() {
        // 1. Creamos una solicitud primero
        solicitud s = service.crearSolicitud(clienteMock, "Reparación");

        // 2. Ejecución
        boolean asignado = service.asignarTecnico(s.getId(), tecnicoMock);

        // 3. Verificación
        assertTrue(asignado);
        assertEquals(estadoSolicitud.EN_PROCESO, s.getEstado());
        assertEquals(tecnicoMock, s.getTecnicoAsignado());
    }

    @Test
    void asignarTecnico_noDebeAsignarSiTecnicoEstaInactivo() {
        // Configuramos el mock para que el técnico no esté activo
        when(tecnicoMock.isActivo()).thenReturn(false);
        solicitud s = service.crearSolicitud(clienteMock, "Reparación");

        // Ejecución
        boolean asignado = service.asignarTecnico(s.getId(), tecnicoMock);

        // Verificación
        assertFalse(asignado);
        assertNotEquals(estadoSolicitud.EN_PROCESO, s.getEstado());
    }

    @Test
    void cerrarSolicitud_debeFuncionarSoloSiEstaEnProceso() {
        // 1. Preparación: Crear y poner en proceso
        solicitud s = service.crearSolicitud(clienteMock, "Mantenimiento");
        service.asignarTecnico(s.getId(), tecnicoMock); // Esto la pone EN_PROCESO

        // 2. Ejecución: Intentar cerrar
        boolean cerrado = service.cerrarSolicitud(s.getId());

        // 3. Verificación
        assertTrue(cerrado);
        assertEquals(estadoSolicitud.CERRADA, s.getEstado());
        assertNotNull(s.getFechaCierre());
    }

    @Test
    void cerrarSolicitud_debeFallarSiEstaAbierta() {
        // 1. Preparación: La solicitud nace ABIERTA
        solicitud s = service.crearSolicitud(clienteMock, "Mantenimiento");

        // 2. Ejecución: Intentar cerrar sin haber asignado técnico antes
        boolean cerrado = service.cerrarSolicitud(s.getId());

        // 3. Verificación
        assertFalse(cerrado);
        assertNotEquals(estadoSolicitud.CERRADA, s.getEstado());
        assertNull(s.getFechaCierre());
    }

    @Test
    void consultarSolicitud_debeRetornarCorrecta() {
        solicitud s1 = service.crearSolicitud(clienteMock, "Duda 1");
        solicitud s2 = service.crearSolicitud(clienteMock, "Duda 2");

        solicitud encontrada = service.consultarSolicitud(s2.getId());

        assertNotNull(encontrada);
        assertEquals("Duda 2", encontrada.getDescripcion());
    }
}
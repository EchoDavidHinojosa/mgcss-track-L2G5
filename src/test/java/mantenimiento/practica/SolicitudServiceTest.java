package mantenimiento.practica;

import mantenimiento.practica.domain.EstadoSolicitud;
import mantenimiento.practica.domain.Solicitud;
import mantenimiento.practica.domain.SolicitudRepository;
import mantenimiento.practica.domain.Tecnico;
import mantenimiento.practica.service.SolicitudService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock
    private SolicitudRepository solicitudRepository;

    @InjectMocks
    private SolicitudService solicitudService;

    @Test
    void crearSolicitud_debeGuardarYDevolverSolicitud() {
        Solicitud solicitud = new Solicitud();

        when(solicitudRepository.save(solicitud)).thenReturn(solicitud);

        Solicitud resultado = solicitudService.crearSolicitud(solicitud);

        assertNotNull(resultado);
        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void asignarTecnico_cuandoSolicitudExiste_debeGuardarSolicitudActualizada() {
        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);
        Tecnico tecnico = new Tecnico(1L, "Carlos", "Hardware");

        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(solicitudRepository.save(any(Solicitud.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Solicitud resultado = solicitudService.asignarTecnico(1L, tecnico);

        assertNotNull(resultado);
        assertEquals(tecnico, resultado.getTecnicoAsignado());
        assertEquals(EstadoSolicitud.ASIGNADA, resultado.getEstado());
        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void asignarTecnico_cuandoSolicitudNoExiste_debeLanzarExcepcion() {
        Tecnico tecnico = new Tecnico(1L, "Carlos", "Hardware");

        when(solicitudRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> solicitudService.asignarTecnico(1L, tecnico)
        );

        assertEquals("Solicitud no encontrada", exception.getMessage());
        verify(solicitudRepository, never()).save(any(Solicitud.class));
    }

    @Test
    void cambiarEstado_cuandoSolicitudExiste_debeGuardarEstadoActualizado() {
        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);

        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(solicitudRepository.save(any(Solicitud.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Solicitud resultado = solicitudService.cambiarEstado(1L, EstadoSolicitud.RESUELTA);

        assertNotNull(resultado);
        assertEquals(EstadoSolicitud.RESUELTA, resultado.getEstado());
        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void cambiarEstado_cuandoSolicitudNoExiste_debeLanzarExcepcion() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> solicitudService.cambiarEstado(1L, EstadoSolicitud.RESUELTA)
        );

        assertEquals("Solicitud no encontrada", exception.getMessage());
        verify(solicitudRepository, never()).save(any(Solicitud.class));
    }
}
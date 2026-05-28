package mantenimiento.practica.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DtosTest {

    @Test
    @DisplayName("Cubre el 100% de AsignarTecnicoRequestDTO")
    void testAsignarTecnicoRequestDTO() {
        // Constructor vacío
        AsignarTecnicoRequestDTO dtoVacio = new AsignarTecnicoRequestDTO();
        assertNull(dtoVacio.getTecnicoId());

        // Setter y Getter
        dtoVacio.setTecnicoId(10L);
        assertEquals(10L, dtoVacio.getTecnicoId());

        // Constructor con parámetros
        AsignarTecnicoRequestDTO dtoParametros = new AsignarTecnicoRequestDTO(20L);
        assertEquals(20L, dtoParametros.getTecnicoId());
    }

    @Test
    @DisplayName("Cubre el 100% de CambiarEstadoRequestDTO")
    void testCambiarEstadoRequestDTO() {
        // Constructor vacío
        CambiarEstadoRequestDTO dtoVacio = new CambiarEstadoRequestDTO();
        assertNull(dtoVacio.getEstado());

        // Setter y Getter
        dtoVacio.setEstado("EN_PROCESO");
        assertEquals("EN_PROCESO", dtoVacio.getEstado());

        // Constructor con parámetros
        CambiarEstadoRequestDTO dtoParametros = new CambiarEstadoRequestDTO("CERRADA");
        assertEquals("CERRADA", dtoParametros.getEstado());
    }
}
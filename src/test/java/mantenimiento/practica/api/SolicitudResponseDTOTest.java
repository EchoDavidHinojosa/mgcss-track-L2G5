package mantenimiento.practica.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SolicitudResponseDTOTest {

    @Test
    @DisplayName("Cubre el constructor vacío y todos los setters/getters")
    void testConstructorVacioYSetters() {

        SolicitudResponseDTO dto = new SolicitudResponseDTO();


        assertNull(dto.getId());
        assertNull(dto.getDescripcion());
        assertNull(dto.getEstado());


        dto.setId(10L);
        dto.setDescripcion("Fallo en la fuente de alimentación");
        dto.setEstado("ABIERTA");


        assertEquals(10L, dto.getId());
        assertEquals("Fallo en la fuente de alimentación", dto.getDescripcion());
        assertEquals("ABIERTA", dto.getEstado());
    }

    @Test
    @DisplayName("Cubre el constructor con parámetros")
    void testConstructorConParametros() {
        SolicitudResponseDTO dto = new SolicitudResponseDTO(99L, "Revisión de red", "EN_PROCESO");

        assertEquals(99L, dto.getId());
        assertEquals("Revisión de red", dto.getDescripcion());
        assertEquals("EN_PROCESO", dto.getEstado());
    }
}
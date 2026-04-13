package mantenimiento.practica.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class tecnicoTest {

    @Test
    public void testConstructorInicializaCorrectamente() {
        // Preparar y Actuar
        tecnico t = new tecnico(1, "Ramón", "Electricidad");

        // Comprobar
        assertEquals(1, t.getId());
        assertEquals("Ramón", t.getNombre());
        assertEquals("Electricidad", t.getEspecialidad());
        assertTrue(t.isActivo(), "El técnico debe nacer activo por defecto");
    }

    @Test
    public void testSetActivoCambiaEstado() {
        tecnico t = new tecnico(1, "Ana", "Redes");

        // Actuar: Desactivar
        boolean resultado = t.setActivo(false);

        // Comprobar
        assertTrue(resultado, "Debe permitir desactivar a un técnico activo");
        assertFalse(t.isActivo());
    }

    @Test
    public void testSetActivoNoPermiteDesactivarSiYaEstaInactivo() {
        tecnico t = new tecnico(1, "Ana", "Redes");
        t.setActivo(false); // Ya está inactivo

        // Actuar: Intentar desactivar de nuevo
        boolean resultado = t.setActivo(false);

        // Comprobar según tu lógica de negocio
        assertFalse(resultado, "Debe devolver false si intentas desactivar a alguien ya inactivo");
    }

    @Test
    public void testToStringFormatoCorrecto() {
        tecnico t = new tecnico(5, "Pepe", "Hardware");
        String resultado = t.toString();

        // Comprobamos que el String contiene los datos clave
        assertTrue(resultado.contains("ID: 5"));
        assertTrue(resultado.contains("Pepe"));
        assertTrue(resultado.contains("Hardware"));
        assertTrue(resultado.contains("ACTIVO"));
    }
}
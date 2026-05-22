package mantenimiento.practica.api;


import mantenimiento.practica.domain.estadoSolicitud;
import mantenimiento.practica.domain.solicitud;
import mantenimiento.practica.service.gestionsolicitudes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import mantenimiento.practica.domain.cliente;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SolicitudController.class)
@Import(SolicitudMapper.class)
class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private gestionsolicitudes servicioSolicitud;

    @Test
    @DisplayName("POST /api/solicitudes crea una solicitud correctamente")
    void crearSolicitudDevuelveSolicitudCreada() throws Exception {
        cliente Cliente=new cliente(23L,"prueba","Prueba",false);
        solicitud solicitud = new solicitud(
                1L,Cliente,
                "Incidencia de prueba");

        given(servicioSolicitud.crearSolicitud(eq("Incidencia de prueba")))
                .willReturn(solicitud);

        mockMvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "descripcion": "Incidencia de prueba"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Incidencia de prueba"))
                .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    @DisplayName("POST /api/solicitudes devuelve 400 si la descripción está vacía")
    void crearSolicitudSinDescripcionDevuelveBadRequest() throws Exception {
        mockMvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "descripcion": ""
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    private solicitud crearSolicitudEjemplo(Long id, String descripcion, estadoSolicitud estado) {
        cliente cliente = new cliente(
                1L,
                "Cliente API",
                "api@test.com",
                false
        );

        return new solicitud(id, cliente, descripcion);
    }
}
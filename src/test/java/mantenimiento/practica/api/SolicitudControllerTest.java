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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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


    @Test
    @DisplayName("GET /api/solicitudes/{id} devuelve la solicitud si existe")
    void obtenerPorIdDevuelveSolicitudExistente() throws Exception {
        Long idExistente = 1L;
        solicitud sol = crearSolicitudEjemplo(idExistente, "Aire acondicionado roto", estadoSolicitud.ABIERTA);

        // Simulamos que el servicio encuentra la solicitud cuando le pasamos el ID 1
        given(servicioSolicitud.consultarSolicitud(idExistente)).willReturn(sol);

        // Ejecutamos el GET con la ruta /api/solicitudes/1
        mockMvc.perform(get("/api/solicitudes/{id}", idExistente)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idExistente))
                .andExpect(jsonPath("$.descripcion").value("Aire acondicionado roto"))
                .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    @DisplayName("PUT /api/solicitudes/{id} devuelve 200 y la solicitud actualizada si existe")
    void actualizarSolicitudExitosa() throws Exception {
        Long idExistente = 1L;
        String nuevaDesc = "Nueva descripción corregida";
        solicitud solicitudActualizada = crearSolicitudEjemplo(idExistente, nuevaDesc, estadoSolicitud.ABIERTA);

        // Mockeamos el servicio para que devuelva la solicitud procesada
        given(servicioSolicitud.actualizarSolicitud(eq(idExistente), eq(nuevaDesc)))
                .willReturn(solicitudActualizada);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/solicitudes/{id}", idExistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "descripcion": "Nueva descripción corregida"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idExistente))
                .andExpect(jsonPath("$.descripcion").value(nuevaDesc));
    }

    @Test
    @DisplayName("PUT /api/solicitudes/{id} devuelve 404 si la solicitud no existe")
    void actualizarSolicitudNoExistenteDevuelveNotFound() throws Exception {
        Long idInexistente = 99L;
        String nuevaDesc = "Intento de actualización";

        // Si el servicio no la encuentra, tu controlador espera un null
        given(servicioSolicitud.actualizarSolicitud(eq(idInexistente), eq(nuevaDesc)))
                .willReturn(null);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/solicitudes/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "descripcion": "Intento de actualización"
                                }
                                """))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("PATCH /api/solicitudes/{id}/estado devuelve 200 si se cambia con éxito")
    void cambiarEstadoExitoso() throws Exception {
        Long idSolicitud = 1L;

        // El controlador llama a rearbirSolicitud(id) internamente
        given(servicioSolicitud.rearbirSolicitud(idSolicitud)).willReturn(true);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch("/api/solicitudes/{id}/estado", idSolicitud)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "estado": "EN_PROCESO"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Estado actualizado correctamente a: EN_PROCESO"));
    }

    @Test
    @DisplayName("PATCH /api/solicitudes/{id}/estado devuelve 400 si falla el cambio en el servicio")
    void cambiarEstadoFallaDevuelveBadRequest() throws Exception {
        Long idSolicitud = 1L;

        given(servicioSolicitud.rearbirSolicitud(idSolicitud)).willReturn(false);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch("/api/solicitudes/{id}/estado", idSolicitud)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "estado": "CERRADA"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("No se pudo cambiar el estado de la solicitud. Verifique el ID o que el estado sea correcto."));
    }

    @Test
    @DisplayName("PUT /api/solicitudes/{id}/cerrar devuelve 200 si se cierra correctamente")
    void cerrarSolicitudExitosa() throws Exception {
        Long idSolicitud = 1L;

        given(servicioSolicitud.cerrarSolicitud(idSolicitud)).willReturn(true);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/solicitudes/{id}/cerrar", idSolicitud))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Solicitud cerrada correctamente"));
    }

    @Test
    @DisplayName("PUT /api/solicitudes/{id}/cerrar devuelve 400 si el servicio no puede cerrarla")
    void cerrarSolicitudFallaDevuelveBadRequest() throws Exception {
        Long idSolicitud = 2L;

        given(servicioSolicitud.cerrarSolicitud(idSolicitud)).willReturn(false);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/solicitudes/{id}/cerrar", idSolicitud))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("No se pudo cerrar la solicitud"));
    }

    // ==========================================
    // PRUEBAS DE reabrirSolicitud (PUT)
    // ==========================================

    @Test
    @DisplayName("PUT /api/solicitudes/{id}/reabrir devuelve 200 si se reabre correctamente")
    void reabrirSolicitudExitosa() throws Exception {
        Long idSolicitud = 1L;

        given(servicioSolicitud.rearbirSolicitud(idSolicitud)).willReturn(true);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/solicitudes/{id}/reabrir", idSolicitud))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Solicitud reabierta correctamente"));
    }

    @Test
    @DisplayName("PUT /api/solicitudes/{id}/reabrir devuelve 400 si el servicio no puede reabrirla")
    void reabrirSolicitudFallaDevuelveBadRequest() throws Exception {
        Long idSolicitud = 3L;

        given(servicioSolicitud.rearbirSolicitud(idSolicitud)).willReturn(false);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/solicitudes/{id}/reabrir", idSolicitud))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("No se pudo reabrir la solicitud"));
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
package mantenimiento.practica.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mantenimiento.practica.domain.solicitud;
import mantenimiento.practica.service.gestionsolicitudes;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final gestionsolicitudes servicioSolicitud;
    private final SolicitudMapper solicitudMapper;

    public SolicitudController(
            gestionsolicitudes servicioSolicitud,
            SolicitudMapper solicitudMapper) {

        this.servicioSolicitud = servicioSolicitud;
        this.solicitudMapper = solicitudMapper;
    }
    @Operation(
            summary = "Crear una solicitud",
            description = "Registra una nueva solicitud de servicio en estado ABIERTA"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<SolicitudResponseDTO> crearSolicitud(
            @Valid @RequestBody SolicitudRequestDTO requestDTO) {

        solicitud solicitud = servicioSolicitud.crearSolicitud(requestDTO.getDescripcion());

        SolicitudResponseDTO response =solicitudMapper.toResponseDTO(solicitud);

        return ResponseEntity.ok(response);
    }
}
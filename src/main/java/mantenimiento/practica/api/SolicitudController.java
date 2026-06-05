package mantenimiento.practica.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mantenimiento.practica.domain.solicitud;
import mantenimiento.practica.service.gestionsolicitudes;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        SolicitudResponseDTO response = solicitudMapper.toResponseDTO(solicitud);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener una solicitud por ID",
            description = "Recupera los detalles de una solicitud específica mediante su identificador único"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> obtenerPorId(
            @Parameter(description = "ID de la solicitud a buscar", example = "1")
            @PathVariable Long id) {

        solicitud solicitud = servicioSolicitud.consultarSolicitud(id);
        if (solicitud == null) {
            return ResponseEntity.notFound().build();
        }

        SolicitudResponseDTO response = solicitudMapper.toResponseDTO(solicitud);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Actualizar una solicitud",
            description = "Modifica la descripción de una solicitud existente mediante su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> actualizarSolicitud(
            @Parameter(description = "ID de la solicitud a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody SolicitudRequestDTO requestDTO) {

        solicitud solicitud = servicioSolicitud.actualizarSolicitud(id, requestDTO.getDescripcion());

        // Si el servicio devuelve null significa que no se encontró la solicitud con ese ID
        if (solicitud == null) {
            return ResponseEntity.notFound().build();
        }

        SolicitudResponseDTO response = solicitudMapper.toResponseDTO(solicitud);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Cambiar estado de la solicitud",
            description = "Modifica dinámicamente el estado de una solicitud utilizando CambiarEstadoRequestDTO"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado cambiado correctamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada"),
            @ApiResponse(responseCode = "400", description = "No se pudo cambiar el estado o el dato es inválido")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(
            @Parameter(description = "ID de la solicitud a modificar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CambiarEstadoRequestDTO cambiarEstadoDTO) {

        boolean actualizado = servicioSolicitud.rearbirSolicitud(id);

        if (!actualizado) {
            // Nota: Dado que el servicio devuelve 'false' tanto si no existe como si el Enum falla,
            // puedes optar por devolver un Bad Request genérico o controlar la existencia previamente.
            return ResponseEntity.badRequest().body("No se pudo cambiar el estado de la solicitud. Verifique el ID o que el estado sea correcto.");
        }

        return ResponseEntity.ok("Estado actualizado correctamente a: " + cambiarEstadoDTO.getEstado());
    }

    @Operation(
            summary = "Cerrar una solicitud",
            description = "Cierra una solicitud existente si está en estado EN_PROCESO"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud cerrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada"),
            @ApiResponse(responseCode = "400", description = "No se puede cerrar la solicitud")
    })
    @PutMapping("/{id}/cerrar")
    public ResponseEntity<?> cerrarSolicitud(
            @Parameter(description = "ID de la solicitud a cerrar", example = "1")
            @PathVariable Long id) {
        boolean cerrada = servicioSolicitud.cerrarSolicitud(id);

        if (!cerrada) {
            return ResponseEntity.badRequest().body("No se pudo cerrar la solicitud");
        }

        return ResponseEntity.ok("Solicitud cerrada correctamente");
    }

    @Operation(
            summary = "Reabrir una solicitud",
            description = "Reabre una solicitud existente si está en estado CERRADA"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud reabierta correctamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada"),
            @ApiResponse(responseCode = "400", description = "No se puede reabrir la solicitud")
    })
    @PutMapping("/{id}/reabrir")
    public ResponseEntity<?> reabrirSolicitud(
            @Parameter(description = "ID de la solicitud a reabrir", example = "1")
            @PathVariable Long id) {
        boolean reabierta = servicioSolicitud.rearbirSolicitud(id);

        if (!reabierta) {
            return ResponseEntity.badRequest().body("No se pudo reabrir la solicitud");
        }

        return ResponseEntity.ok("Solicitud reabierta correctamente");
    }
}
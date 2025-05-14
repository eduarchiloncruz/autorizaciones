package com.echc.autorizaciones.controllers;

import com.echc.autorizaciones.commons.dtos.AutorizacionGrupedDTO;
import com.echc.autorizaciones.commons.dtos.SolicitudDTO;
import com.echc.autorizaciones.services.SolicitudService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(
        info = @Info(
                title = "Solicitudes Controller - Autorizaciones",
                description = "Endpoint to manage solicitudes"
        )
)
@RestController
@RequestMapping("/solicitud")
public class SolicitudController {

    private SolicitudService solicitudService;

    @Autowired
    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @Operation(summary = "Crea una solicitud con sus requerimientos")
    @PostMapping("/new")
    public ResponseEntity<SolicitudDTO> createAutorizacion(@RequestBody AutorizacionGrupedDTO autorizacionGrupedDTO) {
        SolicitudDTO response = this.solicitudService.createAutorizacion(autorizacionGrupedDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una solicitud por id")
    @GetMapping("/{idSolicitud}")
    public ResponseEntity<SolicitudDTO> getSolicitud(@PathVariable Integer idSolicitud) {
        SolicitudDTO response = this.solicitudService.getSolicitud(idSolicitud);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de solicitudes paginadas")
    @GetMapping()
    public Page<SolicitudDTO> getAllSolicitud(@RequestParam(required = false, defaultValue = "0") Integer page,
                                              @RequestParam(required = false, defaultValue = "10") Integer size) {
        Pageable response = PageRequest.of(page, size);
        return this.solicitudService.getAllSolicitudPaged(response);
    }

    @Operation(summary = "Actualiza una solicitud")
    @PutMapping("/{idSolicitud}")
    public ResponseEntity<SolicitudDTO> updateSolicitud(@PathVariable Integer idSolicitud, @RequestBody SolicitudDTO solicitudDTO) {
        SolicitudDTO response = this.solicitudService.updateSolicitud(idSolicitud, solicitudDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una solicitud")
    @DeleteMapping("/{idSolicitud}")
    public ResponseEntity<Void> deleteSolicitud(@PathVariable Integer idSolicitud) {
        this.solicitudService.deleteSolicitud(idSolicitud);
        return ResponseEntity.noContent().build();
    }
}

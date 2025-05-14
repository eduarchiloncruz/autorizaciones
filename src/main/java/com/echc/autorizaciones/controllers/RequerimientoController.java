package com.echc.autorizaciones.controllers;

import com.echc.autorizaciones.commons.dtos.RequerimientoDTO;
import com.echc.autorizaciones.commons.enums.EstadoEnum;
import com.echc.autorizaciones.services.RequerimientoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@OpenAPIDefinition(
        info = @Info(
                title = "Rquerimientos Controller - Autorizaciones",
                description = "Endpoint to manage requerimientos"
        )
)
@RestController
@RequestMapping("/requerimiento")
public class RequerimientoController {

    private RequerimientoService requerimientoService;

    @Autowired
    public RequerimientoController(RequerimientoService requerimientoService) {
        this.requerimientoService = requerimientoService;
    }

    @Operation(summary = "Cambia el estado de un requerimiento")
    @PutMapping("/{idSolicitud}/{numero}")
    public ResponseEntity<RequerimientoDTO> changeEstadoRequerimiento(@PathVariable Integer idSolicitud,
                                                                      @PathVariable Integer numero,
                                                                      @RequestBody EstadoEnum estado) {
        RequerimientoDTO response = this.requerimientoService.changeEstado(idSolicitud, numero, estado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de requerimientos")
    @GetMapping("/all")
    public ResponseEntity<List<RequerimientoDTO>> getAllRequerimientos() {
        List<RequerimientoDTO> response = this.requerimientoService.getRequerimientos();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de requerimientos por estado")
    @GetMapping()
    public ResponseEntity<List<RequerimientoDTO>> getAllRequerimientosByEstado(@RequestParam EstadoEnum estado) {
        List<RequerimientoDTO> response = this.requerimientoService.getRequerimientosByEstado(estado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un requerimiento por idSolicitud y numero")
    @GetMapping("/{idSolicitud}/{numero}")
    public ResponseEntity<RequerimientoDTO> getRequerimientoByIdSolicitudAndNumero(@PathVariable Integer idSolicitud,
                                                                                    @PathVariable Integer numero) {
        RequerimientoDTO response = this.requerimientoService.getRequerimientoById(idSolicitud, numero);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

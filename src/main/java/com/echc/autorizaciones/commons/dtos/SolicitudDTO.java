package com.echc.autorizaciones.commons.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudDTO implements Serializable {

    private Integer idSolicitud;
    private String patologia;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss.SSS", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaCreacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss.SSS", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaCirugia;

    private String observacion;
    private String beneficiario;
    private List<RequerimientoDTO> requerimientos;
}

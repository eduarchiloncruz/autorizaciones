package com.echc.autorizaciones.commons.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutorizacionGrupedDTO implements Serializable {

    private SolicitudDTO solicitud;
    private List<RequerimientoDTO> requerimientos;
}

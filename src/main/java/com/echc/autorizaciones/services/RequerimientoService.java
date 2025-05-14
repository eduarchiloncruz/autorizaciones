package com.echc.autorizaciones.services;

import com.echc.autorizaciones.commons.dtos.RequerimientoDTO;
import com.echc.autorizaciones.commons.enums.EstadoEnum;

import java.util.List;

public interface RequerimientoService {

    RequerimientoDTO getRequerimientoById(Integer idSolicitud, Integer numero);
    RequerimientoDTO changeEstado(Integer idSolicitud, Integer numero, EstadoEnum estado);
    List<RequerimientoDTO> getRequerimientos();
    List<RequerimientoDTO> getRequerimientosByEstado(EstadoEnum estado);
}

package com.echc.autorizaciones.services;

import com.echc.autorizaciones.commons.dtos.AutorizacionGrupedDTO;
import com.echc.autorizaciones.commons.dtos.SolicitudDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SolicitudService {

    SolicitudDTO createAutorizacion(AutorizacionGrupedDTO autorizacionGrupedDTO);
    SolicitudDTO getSolicitud(Integer idSolicitud);
    Page<SolicitudDTO> getAllSolicitudPaged(Pageable pageable);
    SolicitudDTO updateSolicitud(Integer idSolicitud, SolicitudDTO solicitudDTO);
    void deleteSolicitud(Integer idSolicitud);
}

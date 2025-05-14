package com.echc.autorizaciones.commons.mappers;

import com.echc.autorizaciones.commons.dtos.SolicitudDTO;
import com.echc.autorizaciones.persistances.models.Solicitud;

public class SolicitudMapper {
    
    public static SolicitudDTO toDTO(Solicitud solicitud) {
        return SolicitudDTO.builder()
                .idSolicitud(solicitud.getIdSolicitud())
                .patologia(solicitud.getPatologia())
                .fechaCreacion(solicitud.getFechaCreacion())
                .fechaCirugia(solicitud.getFechaCirugia())
                .observacion(solicitud.getObservacion())
                .beneficiario(solicitud.getBeneficiario())
                .build();
    }

    public static Solicitud toEntity(SolicitudDTO solicitudDTO) {
        return Solicitud.builder()
                .idSolicitud(solicitudDTO.getIdSolicitud())
                .patologia(solicitudDTO.getPatologia())
                .fechaCreacion(solicitudDTO.getFechaCreacion())
                .fechaCirugia(solicitudDTO.getFechaCirugia())
                .observacion(solicitudDTO.getObservacion())
                .beneficiario(solicitudDTO.getBeneficiario())
                .build();
    }
}

package com.echc.autorizaciones.mocks;

import com.echc.autorizaciones.commons.dtos.SolicitudDTO;
import com.echc.autorizaciones.persistances.models.Solicitud;

import java.util.Date;

public class SolicitudDTOMock {

    public static SolicitudDTO getSolicitudDTOMock_1() {
        return SolicitudDTO.builder()
                .idSolicitud(1)
                .patologia("Apendicitis")
                .fechaCreacion(new Date())
                .fechaCirugia(new Date())
                .observacion("Cirugía de urgencia")
                .beneficiario("Juan Pérez")
                .build();
    }

    public static SolicitudDTO getSolicitudDTOMock_2() {
        return SolicitudDTO.builder()
                .idSolicitud(2)
                .patologia("Hernia inguinal")
                .fechaCreacion(new Date())
                .fechaCirugia(new Date())
                .observacion("Paciente estable")
                .beneficiario("Ana García")
                .build();
    }
}

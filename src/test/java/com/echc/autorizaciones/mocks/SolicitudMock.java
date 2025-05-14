package com.echc.autorizaciones.mocks;

import com.echc.autorizaciones.persistances.models.Solicitud;

import java.util.Date;

public class SolicitudMock {

    public static Solicitud getSolicitudMock_1() {
        return Solicitud.builder()
                .idSolicitud(1)
                .patologia("Apendicitis")
                .fechaCreacion(new Date())
                .fechaCirugia(new Date())
                .observacion("Cirugía de urgencia")
                .beneficiario("Juan Pérez")
                .build();
    }

    public static Solicitud getSolicitudMock_2() {
        return Solicitud.builder()
                .idSolicitud(2)
                .patologia("Hernia inguinal")
                .fechaCreacion(new Date())
                .fechaCirugia(new Date())
                .observacion("Paciente estable")
                .beneficiario("Ana García")
                .build();
    }
}

package com.echc.autorizaciones.mocks;

import com.echc.autorizaciones.commons.dtos.RequerimientoDTO;
import com.echc.autorizaciones.commons.enums.EstadoEnum;

public class RequerimientoDTOMock {

    public static RequerimientoDTO getRequerimientoDTOMock_1() {
        return RequerimientoDTO.builder()
                .numero(1)
                .cantidad(2)
                .estado(EstadoEnum.INICIADO)
                .descripcion("Malla quirúrgica")
                .build();
    }

    public static RequerimientoDTO getRequerimientoDTOMock_2() {
        return RequerimientoDTO.builder()
                .numero(2)
                .cantidad(1)
                .estado(EstadoEnum.INICIADO)
                .descripcion("Protesis de brazo derecho")
                .build();
    }

    public static RequerimientoDTO getRequerimientoDTOMock_3() {
        return RequerimientoDTO.builder()
                .numero(1)
                .cantidad(3)
                .estado(EstadoEnum.INICIADO)
                .descripcion("Inyeccion de colageno")
                .build();
    }
}

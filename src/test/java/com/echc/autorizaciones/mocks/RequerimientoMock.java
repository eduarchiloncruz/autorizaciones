package com.echc.autorizaciones.mocks;

import com.echc.autorizaciones.commons.enums.EstadoEnum;
import com.echc.autorizaciones.persistances.models.requerimiento.Requerimiento;

public class RequerimientoMock {

    public static Requerimiento getRequerimientoMock_1() {
        return Requerimiento.builder()
                .numero(1)
                .cantidad(2)
                .estado(String.valueOf(EstadoEnum.INICIADO))
                .descripcion("Malla quirúrgica")
                .build();
    }

    public static Requerimiento getRequerimientoMock_2() {
        return Requerimiento.builder()
                .numero(2)
                .cantidad(1)
                .estado(String.valueOf(EstadoEnum.INICIADO))
                .descripcion("Protesis de brazo derecho")
                .build();
    }

    public static Requerimiento getRequerimientoMock_3() {
        return Requerimiento.builder()
                .numero(1)
                .cantidad(3)
                .estado(String.valueOf(EstadoEnum.INICIADO))
                .descripcion("Inyeccion de colageno")
                .build();
    }
}

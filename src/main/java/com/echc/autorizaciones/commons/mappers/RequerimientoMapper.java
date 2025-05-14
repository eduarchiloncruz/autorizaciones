package com.echc.autorizaciones.commons.mappers;

import com.echc.autorizaciones.commons.dtos.RequerimientoDTO;
import com.echc.autorizaciones.commons.enums.EstadoEnum;
import com.echc.autorizaciones.persistances.models.requerimiento.Requerimiento;

public class RequerimientoMapper {

    public static RequerimientoDTO toDTO(Requerimiento requerimiento) {
        return RequerimientoDTO.builder()
                .numero(requerimiento.getNumero())
                .descripcion(requerimiento.getDescripcion())
                .estado(EstadoEnum.valueOf(requerimiento.getEstado()))
                .cantidad(requerimiento.getCantidad())
                .build();
    }

    public static Requerimiento toEntity(RequerimientoDTO requerimientoDTO) {
        return Requerimiento.builder()
                .numero(requerimientoDTO.getNumero())
                .descripcion(requerimientoDTO.getDescripcion())
                .estado(String.valueOf(requerimientoDTO.getEstado()))
                .cantidad(requerimientoDTO.getCantidad())
                .build();
    }
}

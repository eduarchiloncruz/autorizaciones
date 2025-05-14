package com.echc.autorizaciones.commons.dtos;

import com.echc.autorizaciones.commons.enums.EstadoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequerimientoDTO implements Serializable {

    private Integer numero;
    private Integer cantidad;
    private EstadoEnum estado;
    private String descripcion;
}

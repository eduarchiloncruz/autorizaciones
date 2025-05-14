package com.echc.autorizaciones.persistances.models.requerimiento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequerimientoId implements Serializable {

    private Integer solicitud;
    private Integer numero;
}

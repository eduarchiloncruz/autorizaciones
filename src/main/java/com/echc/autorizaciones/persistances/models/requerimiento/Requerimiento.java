package com.echc.autorizaciones.persistances.models.requerimiento;

import com.echc.autorizaciones.persistances.models.Solicitud;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "requerimiento")
@IdClass(RequerimientoId.class)
public class Requerimiento implements Serializable {

    @Id
    @Column(name = "numero")
    private Integer numero;

    @Id
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "solicitud")
    private Solicitud solicitud;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "estado")
    private String estado;

    @Column(name = "descripcion")
    private String descripcion;
}

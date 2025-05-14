package com.echc.autorizaciones.persistances.models;

import com.echc.autorizaciones.persistances.models.requerimiento.Requerimiento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "solicitud")
public class Solicitud implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSolicitud")
    private Integer idSolicitud;

    @Column(name = "patologia")
    private String patologia;

    @Column(name = "fechaCreacion")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss.SSS", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaCreacion;

    @Column(name = "fechaCirugia")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss.SSS", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaCirugia;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "beneficiario")
    private String beneficiario;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Requerimiento> requerimientos;
}

package com.echc.autorizaciones.persistances.repositories;


import com.echc.autorizaciones.commons.enums.EstadoEnum;
import com.echc.autorizaciones.persistances.models.Solicitud;
import com.echc.autorizaciones.persistances.models.requerimiento.Requerimiento;
import com.echc.autorizaciones.persistances.models.requerimiento.RequerimientoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequerimientoRepository extends JpaRepository<Requerimiento, RequerimientoId> {

    List<Requerimiento> findAllByEstado(EstadoEnum estado);

    @Query("SELECT r FROM Requerimiento r WHERE r.solicitud.idSolicitud IN :solicitudesIds")
    List<Requerimiento> findRequerimientosBySolicitudesIds(@Param("solicitudesIds") List<Integer> solicitudesIds);
}

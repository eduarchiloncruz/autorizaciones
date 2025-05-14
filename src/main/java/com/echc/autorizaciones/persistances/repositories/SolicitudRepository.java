package com.echc.autorizaciones.persistances.repositories;

import com.echc.autorizaciones.persistances.models.Solicitud;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer>, PagingAndSortingRepository<Solicitud, Integer> {

    @EntityGraph(attributePaths = "requerimientos") // JOIN FETCH
    Page<Solicitud> findAll(Pageable pageable);
}

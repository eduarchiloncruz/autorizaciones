package com.echc.autorizaciones.services.impl;

import com.echc.autorizaciones.commons.dtos.RequerimientoDTO;
import com.echc.autorizaciones.commons.enums.EstadoEnum;
import com.echc.autorizaciones.commons.mappers.RequerimientoMapper;
import com.echc.autorizaciones.exceptions.customs.EntityModelNotFoundException;
import com.echc.autorizaciones.persistances.models.requerimiento.Requerimiento;
import com.echc.autorizaciones.persistances.models.requerimiento.RequerimientoId;
import com.echc.autorizaciones.persistances.repositories.RequerimientoRepository;
import com.echc.autorizaciones.services.RequerimientoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequerimientoServiceImpl implements RequerimientoService {

    private static final String REQUERIMIENTO_NOT_FOUND = "No existe requerimiento numero %d de la solicitud %d.";

    private RequerimientoRepository requerimientoRepository;

    @Autowired
    public RequerimientoServiceImpl(RequerimientoRepository requerimientoRepository) {
        this.requerimientoRepository = requerimientoRepository;
    }

    @Override
    public RequerimientoDTO getRequerimientoById(Integer idSolicitud, Integer numero) {
        RequerimientoId requerimientoId = new RequerimientoId(idSolicitud, numero);
        Requerimiento requerimiento = this.requerimientoRepository.findById(requerimientoId)
                .orElseThrow(() -> new EntityModelNotFoundException(
                        String.format(REQUERIMIENTO_NOT_FOUND, numero, idSolicitud)));

        return RequerimientoMapper.toDTO(requerimiento);
    }

    @Override
    @Transactional
    public RequerimientoDTO changeEstado(Integer idSolicitud, Integer numero, EstadoEnum estado) {
        RequerimientoId requerimientoId = new RequerimientoId(idSolicitud, numero);
        Requerimiento requerimiento = this.requerimientoRepository.findById(requerimientoId)
                .orElseThrow(() -> new EntityModelNotFoundException(
                        String.format(REQUERIMIENTO_NOT_FOUND, numero, idSolicitud)));

        requerimiento.setEstado(String.valueOf(estado));
        return RequerimientoMapper.toDTO(requerimientoRepository.save(requerimiento));
    }

    @Override
    public List<RequerimientoDTO> getRequerimientos() {
        return this.requerimientoRepository.findAll().stream()
                .map(RequerimientoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequerimientoDTO> getRequerimientosByEstado(EstadoEnum estado) {
        return this.requerimientoRepository.findAllByEstado(estado).stream()
                .map(RequerimientoMapper::toDTO)
                .collect(Collectors.toList());
    }
}

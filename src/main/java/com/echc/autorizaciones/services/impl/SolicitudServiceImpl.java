package com.echc.autorizaciones.services.impl;

import com.echc.autorizaciones.commons.dtos.AutorizacionGrupedDTO;
import com.echc.autorizaciones.commons.dtos.RequerimientoDTO;
import com.echc.autorizaciones.commons.dtos.SolicitudDTO;
import com.echc.autorizaciones.commons.mappers.RequerimientoMapper;
import com.echc.autorizaciones.commons.mappers.SolicitudMapper;
import com.echc.autorizaciones.exceptions.customs.EntityModelNotFoundException;
import com.echc.autorizaciones.persistances.models.Solicitud;
import com.echc.autorizaciones.persistances.models.requerimiento.Requerimiento;
import com.echc.autorizaciones.persistances.repositories.RequerimientoRepository;
import com.echc.autorizaciones.persistances.repositories.SolicitudRepository;
import com.echc.autorizaciones.services.SolicitudService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private static final String SOLICITUD_NOT_FOUND = "No existe solicitud con el numero: ";

    private SolicitudRepository solicitudRepository;

    private RequerimientoRepository requerimientoRepository;

    @Autowired
    public SolicitudServiceImpl(SolicitudRepository solicitudRepository, RequerimientoRepository requerimientoRepository) {
        this.solicitudRepository = solicitudRepository;
        this.requerimientoRepository = requerimientoRepository;
    }

    @Override
    @Transactional
    public SolicitudDTO createAutorizacion(AutorizacionGrupedDTO autorizacionGrupedDTO) {
        Solicitud solicitud = SolicitudMapper.toEntity(autorizacionGrupedDTO.getSolicitud());

        List<RequerimientoDTO> requerimientosDTO = autorizacionGrupedDTO.getRequerimientos();
        if (requerimientosDTO != null && !requerimientosDTO.isEmpty()) {
            List<Requerimiento> requerimientos = requerimientosDTO.stream()
                    .map(dto -> {
                        Requerimiento req = RequerimientoMapper.toEntity(dto);
                        req.setSolicitud(solicitud);
                        return req;
                    })
                    .collect(Collectors.toList());
            solicitud.setRequerimientos(requerimientos);
        }

        Solicitud solicitudCreated = solicitudRepository.save(solicitud);

        SolicitudDTO response = SolicitudMapper.toDTO(solicitudCreated);
        response.setRequerimientos(
                solicitudCreated.getRequerimientos().stream()
                        .map(RequerimientoMapper::toDTO)
                        .collect(Collectors.toList())
        );
        return response;
    }

    @Override
    public SolicitudDTO getSolicitud(Integer idSolicitud) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud).orElseThrow(
                () -> new EntityModelNotFoundException(SOLICITUD_NOT_FOUND.concat(idSolicitud.toString()))
        );

        SolicitudDTO solicitudDTO = SolicitudMapper.toDTO(solicitud);

        if (solicitud.getRequerimientos() != null && !solicitud.getRequerimientos().isEmpty()) {
            List<RequerimientoDTO> requerimientosDTO = solicitud.getRequerimientos().stream()
                    .map(RequerimientoMapper::toDTO)
                    .collect(Collectors.toList());
            solicitudDTO.setRequerimientos(requerimientosDTO);
        }
        return solicitudDTO;
    }

    @Override
    public Page<SolicitudDTO> getAllSolicitudPaged(Pageable pageable) {
        Page<Solicitud> solicitudesPage = solicitudRepository.findAll(pageable);

        List<SolicitudDTO> response = solicitudesPage.getContent().stream()
                .map(solicitud -> {
                    SolicitudDTO dto = SolicitudMapper.toDTO(solicitud);
                    List<RequerimientoDTO> requerimientosDTO = solicitud.getRequerimientos().stream()
                            .map(RequerimientoMapper::toDTO)
                            .collect(Collectors.toList());
                    dto.setRequerimientos(requerimientosDTO);
                    return dto;
                }).collect(Collectors.toList());

        return new PageImpl<>(response, pageable, solicitudesPage.getTotalElements());
    }

    @Override
    @Transactional
    public SolicitudDTO updateSolicitud(Integer idSolicitud, SolicitudDTO solicitudDTO) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new EntityModelNotFoundException(SOLICITUD_NOT_FOUND + idSolicitud));

        solicitud.setPatologia(solicitudDTO.getPatologia());
        solicitud.setFechaCreacion(solicitudDTO.getFechaCreacion());
        solicitud.setFechaCirugia(solicitudDTO.getFechaCirugia());
        solicitud.setObservacion(solicitudDTO.getObservacion());
        solicitud.setBeneficiario(solicitudDTO.getBeneficiario());

        return SolicitudMapper.toDTO(solicitudRepository.save(solicitud));
    }

    @Override
    @Transactional
    public void deleteSolicitud(Integer idSolicitud) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new EntityModelNotFoundException(SOLICITUD_NOT_FOUND + idSolicitud));

        solicitudRepository.delete(solicitud);
    }
}

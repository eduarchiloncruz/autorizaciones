package com.echc.autorizaciones.services;

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
import com.echc.autorizaciones.services.impl.SolicitudServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.echc.autorizaciones.mocks.RequerimientoDTOMock.*;
import static com.echc.autorizaciones.mocks.RequerimientoMock.*;
import static com.echc.autorizaciones.mocks.SolicitudDTOMock.getSolicitudDTOMock_1;
import static com.echc.autorizaciones.mocks.SolicitudMock.getSolicitudMock_1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SolicitudServiceTest {

    private SolicitudRepository solicitudRepository;
    private RequerimientoRepository requerimientoRepository;
    private SolicitudService solicitudService;

    @BeforeEach
    public void setUp() {
        solicitudRepository = mock(SolicitudRepository.class);
        requerimientoRepository = mock(RequerimientoRepository.class);
        solicitudService = new SolicitudServiceImpl(solicitudRepository, requerimientoRepository);
    }

    @Test
    public void createAutorizacionTest() {
        SolicitudDTO solicitudDTO = getSolicitudDTOMock_1();
        RequerimientoDTO reqDTO1 = getRequerimientoDTOMock_1();
        RequerimientoDTO reqDTO2 = getRequerimientoDTOMock_2();

        AutorizacionGrupedDTO autorizacionGrupedDTO = new AutorizacionGrupedDTO();
        autorizacionGrupedDTO.setSolicitud(solicitudDTO);
        autorizacionGrupedDTO.setRequerimientos(Arrays.asList(reqDTO1, reqDTO2));

        Solicitud solicitudEntity = SolicitudMapper.toEntity(solicitudDTO);
        List<Requerimiento> requerimientos = Arrays.asList(
                RequerimientoMapper.toEntity(reqDTO1),
                RequerimientoMapper.toEntity(reqDTO2)
        );
        solicitudEntity.setRequerimientos(requerimientos);

        when(solicitudRepository.save(any(Solicitud.class)))
                .thenReturn(solicitudEntity);

        SolicitudDTO result = solicitudService.createAutorizacion(autorizacionGrupedDTO);

        assertNotNull(result);
        assertEquals(solicitudDTO.getIdSolicitud(), result.getIdSolicitud());
        assertEquals(2, result.getRequerimientos().size());
    }

    @Test
    public void getSolicitudSuccessTest() {
        Integer idSolicitud = 1;
        Solicitud solicitudEntity = getSolicitudMock_1();
        List<Requerimiento> requerimientos = Arrays.asList(
                getRequerimientoMock_1(),
                getRequerimientoMock_2()
        );
        solicitudEntity.setRequerimientos(requerimientos);

        when(solicitudRepository.findById(idSolicitud)).thenReturn(Optional.of(solicitudEntity));

        SolicitudDTO result = solicitudService.getSolicitud(idSolicitud);

        assertNotNull(result);
        assertEquals(1, result.getIdSolicitud());
    }

    @Test
    public void getSolicitudExceptionTest() {
        Integer idSolicitud = 1;

        when(solicitudRepository.findById(idSolicitud)).thenReturn(Optional.empty());

        EntityModelNotFoundException exception = assertThrows(EntityModelNotFoundException.class,
                ()-> solicitudService.getSolicitud(idSolicitud));

        assertEquals(String.format("No existe solicitud con el numero: %d", idSolicitud), exception.getMessage());
    }

    @Test
    public void getAllSolicitudPagedTest() {
        Solicitud solicitud1 = getSolicitudMock_1();
        solicitud1.setRequerimientos(Arrays.asList(getRequerimientoMock_1(), getRequerimientoMock_2()));
        Solicitud solicitud2 = getSolicitudMock_1();
        solicitud2.setRequerimientos(Arrays.asList(getRequerimientoMock_3()));

        List<Solicitud> solicitudes = Arrays.asList(solicitud1, solicitud2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Solicitud> solicitudesPage = new PageImpl<>(solicitudes, pageable, solicitudes.size());

        when(solicitudRepository.findAll(pageable)).thenReturn(solicitudesPage);

        Page<SolicitudDTO> result = solicitudService.getAllSolicitudPaged(pageable);

        assertNotNull(result);
        assertEquals(solicitudes.size(), result.getTotalElements());
    }

    @Test
    public void updateSolicitudSuccessTest() {
        Integer idSolicitud = 1;
        SolicitudDTO solicitudDTO = getSolicitudDTOMock_1();
        solicitudDTO.setBeneficiario("Lionel Messi");
        solicitudDTO.setObservacion("Cambio de paciente");

        Solicitud solicitudEntity = SolicitudMapper.toEntity(solicitudDTO);

        when(solicitudRepository.findById(idSolicitud)).thenReturn(Optional.of(solicitudEntity));
        when(solicitudRepository.save(any(Solicitud.class)))
                .thenReturn(solicitudEntity);

        SolicitudDTO result = solicitudService.updateSolicitud(idSolicitud, solicitudDTO);

        assertNotNull(result);
        assertEquals("Lionel Messi", result.getBeneficiario());
        assertEquals("Cambio de paciente", result.getObservacion());
    }

    @Test
    public void updateSolicitudExceptionTest() {
        Integer idSolicitud = 1;
        SolicitudDTO solicitudDTO = getSolicitudDTOMock_1();

        when(solicitudRepository.findById(idSolicitud)).thenReturn(Optional.empty());

        EntityModelNotFoundException exception = assertThrows(EntityModelNotFoundException.class,
                ()-> solicitudService.updateSolicitud(idSolicitud, solicitudDTO));

        assertEquals(String.format("No existe solicitud con el numero: %d", idSolicitud), exception.getMessage());
    }

    @Test
    public void deleteSolicitudSuccessTest() {
        Integer idSolicitud = 1;

        Solicitud solicitudEntity = getSolicitudMock_1();
        when(solicitudRepository.findById(idSolicitud)).thenReturn(Optional.of(solicitudEntity));

        solicitudService.deleteSolicitud(idSolicitud);

        verify(solicitudRepository).delete(solicitudEntity);
    }

    @Test
    public void deleteSolicitudExceptionTest() {
        Integer idSolicitud = 1;

        when(solicitudRepository.findById(idSolicitud)).thenReturn(Optional.empty());

        EntityModelNotFoundException exception = assertThrows(EntityModelNotFoundException.class,
                ()-> solicitudService.deleteSolicitud(idSolicitud));

        assertEquals(String.format("No existe solicitud con el numero: %d", idSolicitud), exception.getMessage());
    }
}

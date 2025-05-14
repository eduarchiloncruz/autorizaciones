package com.echc.autorizaciones.services;

import com.echc.autorizaciones.commons.dtos.RequerimientoDTO;
import com.echc.autorizaciones.commons.enums.EstadoEnum;
import com.echc.autorizaciones.exceptions.customs.EntityModelNotFoundException;
import com.echc.autorizaciones.persistances.models.requerimiento.Requerimiento;
import com.echc.autorizaciones.persistances.models.requerimiento.RequerimientoId;
import com.echc.autorizaciones.persistances.repositories.RequerimientoRepository;
import com.echc.autorizaciones.persistances.repositories.SolicitudRepository;
import com.echc.autorizaciones.services.impl.RequerimientoServiceImpl;
import com.echc.autorizaciones.services.impl.SolicitudServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.echc.autorizaciones.mocks.RequerimientoMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RequerimientoServiceTest {

    private RequerimientoRepository requerimientoRepository;
    private RequerimientoService requerimientoService;

    @BeforeEach
    void setUp() {
        requerimientoRepository = mock(RequerimientoRepository.class);
        requerimientoService = new RequerimientoServiceImpl(requerimientoRepository);
    }

    @Test
    public void getRequerimientoByIdSuccessTest() {
        Integer idSolicitud = 1;
        Integer numero = 1;
        RequerimientoId requerimientoId = new RequerimientoId(idSolicitud, numero);
        Requerimiento requerimiento = getRequerimientoMock_1();

        when(requerimientoRepository.findById(requerimientoId)).thenReturn(Optional.ofNullable(requerimiento));

        RequerimientoDTO result = requerimientoService.getRequerimientoById(idSolicitud, numero);
        assertNotNull(result);
    }

    @Test
    public void getRequerimientoByIdExceptionTest() {
        Integer idSolicitud = 1;
        Integer numero = 1;
        RequerimientoId requerimientoId = new RequerimientoId(idSolicitud, numero);

        when(requerimientoRepository.findById(requerimientoId)).thenReturn(Optional.empty());

        EntityModelNotFoundException exception = assertThrows(EntityModelNotFoundException.class,
                ()-> requerimientoService.getRequerimientoById(idSolicitud, numero));

        assertEquals("No existe requerimiento numero 1 de la solicitud 1.", exception.getMessage());
    }

    @Test
    public void changeEstadoSuccessTest() {
        Integer idSolicitud = 1;
        Integer numero = 1;
        RequerimientoId requerimientoId = new RequerimientoId(idSolicitud, numero);
        Requerimiento requerimiento = getRequerimientoMock_1();

        when(requerimientoRepository.findById(requerimientoId)).thenReturn(Optional.ofNullable(requerimiento));

        requerimiento.setEstado(String.valueOf(EstadoEnum.APROBADO));
        when(requerimientoRepository.save(requerimiento)).thenReturn(requerimiento);

        RequerimientoDTO result = requerimientoService.changeEstado(idSolicitud, numero, EstadoEnum.APROBADO);

        assertNotNull(result);
        assertEquals(EstadoEnum.APROBADO, result.getEstado());
    }

    @Test
    public void changeEstadoExceptionTest() {
        Integer idSolicitud = 1;
        Integer numero = 1;
        RequerimientoId requerimientoId = new RequerimientoId(idSolicitud, numero);

        when(requerimientoRepository.findById(requerimientoId)).thenReturn(Optional.empty());

        EntityModelNotFoundException exception = assertThrows(EntityModelNotFoundException.class,
                ()-> requerimientoService.changeEstado(idSolicitud, numero, EstadoEnum.APROBADO));

        assertEquals("No existe requerimiento numero 1 de la solicitud 1.", exception.getMessage());
    }

    @Test
    public void getRequerimientosTest() {
        List<Requerimiento> requerimientoList = Arrays.asList(getRequerimientoMock_1(), getRequerimientoMock_2());

        when(requerimientoRepository.findAll()).thenReturn(requerimientoList);

        List<RequerimientoDTO> result = requerimientoService.getRequerimientos();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void getRequerimientosByEstadoTest() {
        Requerimiento requerimiento1 = getRequerimientoMock_1();
        requerimiento1.setEstado(String.valueOf(EstadoEnum.FINALIZADO));

        Requerimiento requerimiento2 = getRequerimientoMock_2();
        requerimiento2.setEstado(String.valueOf(EstadoEnum.FINALIZADO));

        List<Requerimiento> requerimientoList = Arrays.asList(requerimiento1, requerimiento2);

        when(requerimientoRepository.findAllByEstado(EstadoEnum.FINALIZADO)).thenReturn(requerimientoList);

        List<RequerimientoDTO> result =  requerimientoService.getRequerimientosByEstado(EstadoEnum.FINALIZADO);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}

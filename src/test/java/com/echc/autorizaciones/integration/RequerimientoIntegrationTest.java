package com.echc.autorizaciones.integration;

import com.echc.autorizaciones.commons.dtos.RequerimientoDTO;
import com.echc.autorizaciones.commons.dtos.SolicitudDTO;
import com.echc.autorizaciones.commons.enums.EstadoEnum;
import com.echc.autorizaciones.controllers.RequerimientoController;
import com.echc.autorizaciones.services.RequerimientoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.echc.autorizaciones.mocks.RequerimientoDTOMock.getRequerimientoDTOMock_2;
import static com.echc.autorizaciones.mocks.SolicitudDTOMock.getSolicitudDTOMock_1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static com.echc.autorizaciones.mocks.RequerimientoDTOMock.getRequerimientoDTOMock_1;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration(classes = RequerimientoController.class)
@WebMvcTest(RequerimientoController.class)
public class RequerimientoIntegrationTest {

    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RequerimientoService requerimientoService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void changeEstadoRequerimientoTest() throws Exception {
        Integer idSolicitud = 1;
        Integer numero = 1;
        RequerimientoDTO requerimientoDTO = getRequerimientoDTOMock_1();
        requerimientoDTO.setEstado(EstadoEnum.APROBADO);

        when(requerimientoService.changeEstado(idSolicitud, numero, EstadoEnum.APROBADO)).thenReturn(requerimientoDTO);

        String body = objectMapper.writeValueAsString(EstadoEnum.APROBADO);
        String result = objectMapper.writeValueAsString(requerimientoDTO);

        mockMvc.perform(put("/requerimiento/{idSolicitud}/{numero}", idSolicitud, numero)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void getAllRequerimientosTest() throws Exception {
        List<RequerimientoDTO> requerimientoDTOList = Arrays.asList(getRequerimientoDTOMock_1(), getRequerimientoDTOMock_2());
        when(requerimientoService.getRequerimientos()).thenReturn(requerimientoDTOList);

        mockMvc.perform(get("/requerimiento/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(requerimientoDTOList)));
    }

    @Test
    void getAllRequerimientosByEstadoTest() throws Exception {
        List<RequerimientoDTO> requerimientoDTOList = Arrays.asList(getRequerimientoDTOMock_1(), getRequerimientoDTOMock_2());
        when(requerimientoService.getRequerimientosByEstado(EstadoEnum.INICIADO)).thenReturn(requerimientoDTOList);

        mockMvc.perform(get("/requerimiento")
                        .param("estado", EstadoEnum.INICIADO.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(requerimientoDTOList)));
    }

    @Test
    void getRequerimientosByIdSolicitudAndNumeroTest() throws Exception {
        SolicitudDTO solicitudDTO = getSolicitudDTOMock_1();
        RequerimientoDTO requerimientoDTO1 = getRequerimientoDTOMock_1();
        RequerimientoDTO requerimientoDTO2 = getRequerimientoDTOMock_2();
        solicitudDTO.setRequerimientos(Arrays.asList(requerimientoDTO1, requerimientoDTO2));

        when(requerimientoService.getRequerimientoById(solicitudDTO.getIdSolicitud(),
                requerimientoDTO1.getNumero())).thenReturn(requerimientoDTO1);

        mockMvc.perform(get("/requerimiento/{idSolicitud}/{numero}", 1, 1)
                        .param("estado", EstadoEnum.INICIADO.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(requerimientoDTO1)));
    }
}

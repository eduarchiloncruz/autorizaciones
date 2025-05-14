package com.echc.autorizaciones.integration;

import com.echc.autorizaciones.commons.dtos.AutorizacionGrupedDTO;
import com.echc.autorizaciones.commons.dtos.RequerimientoDTO;
import com.echc.autorizaciones.commons.dtos.SolicitudDTO;
import com.echc.autorizaciones.controllers.SolicitudController;
import com.echc.autorizaciones.services.SolicitudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.echc.autorizaciones.mocks.RequerimientoDTOMock.*;
import static com.echc.autorizaciones.mocks.SolicitudDTOMock.getSolicitudDTOMock_1;
import static com.echc.autorizaciones.mocks.SolicitudDTOMock.getSolicitudDTOMock_2;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = SolicitudController.class)
@WebMvcTest(SolicitudController.class)
public class SolicitudIntegrationTest {

    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SolicitudService solicitudService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createAutorizacion() throws Exception {
        SolicitudDTO solicitudDTO = getSolicitudDTOMock_1();
        RequerimientoDTO reqDTO1 = getRequerimientoDTOMock_1();
        RequerimientoDTO reqDTO2 = getRequerimientoDTOMock_2();

        AutorizacionGrupedDTO autorizacionGrupedDTO = new AutorizacionGrupedDTO();
        autorizacionGrupedDTO.setSolicitud(solicitudDTO);
        autorizacionGrupedDTO.setRequerimientos(Arrays.asList(reqDTO1, reqDTO2));

        SolicitudDTO response = getSolicitudDTOMock_2();
        response.setIdSolicitud(1);
        response.setRequerimientos(Arrays.asList(reqDTO1, reqDTO2));

        when(solicitudService.createAutorizacion(autorizacionGrupedDTO)).thenReturn(response);

        mockMvc.perform(post("/solicitud/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(autorizacionGrupedDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void getSolicitudTest() throws Exception {
        Integer idSolicitud = 1;
        SolicitudDTO response = getSolicitudDTOMock_1();

        when(solicitudService.getSolicitud(idSolicitud)).thenReturn(response);

        mockMvc.perform(get("/solicitud/{idSolicitud}", idSolicitud))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void getAllSolicitudTest() throws Exception {
        SolicitudDTO solicitud1 = getSolicitudDTOMock_1();
        solicitud1.setRequerimientos(Arrays.asList(getRequerimientoDTOMock_1(), getRequerimientoDTOMock_2()));
        SolicitudDTO solicitud2 = getSolicitudDTOMock_1();
        solicitud2.setRequerimientos(Arrays.asList(getRequerimientoDTOMock_3()));

        List<SolicitudDTO> solicitudes = Arrays.asList(solicitud1, solicitud2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<SolicitudDTO> result = new PageImpl<>(solicitudes, pageable, solicitudes.size());

        when(solicitudService.getAllSolicitudPaged(pageable)).thenReturn(result);

        mockMvc.perform(get("/solicitud")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void updateSolicitudTest() throws Exception {
        Integer idSolicitud = 1;
        SolicitudDTO body = new SolicitudDTO();
        body.setBeneficiario("Lionel Messi");

        SolicitudDTO response = getSolicitudDTOMock_1();
        response.setBeneficiario("Lionel Messi");

        when(solicitudService.updateSolicitud(idSolicitud, body)).thenReturn(response);

        mockMvc.perform(put("/solicitud/{idSolicitud}", idSolicitud)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

    }

    @Test
    void deleteSolicitudTest() throws Exception {
        Integer idSolicitud = 1;

        doNothing().when(solicitudService).deleteSolicitud(idSolicitud);

        mockMvc.perform(delete("/solicitud/{idSolicitud}", idSolicitud))
                .andExpect(status().isNoContent());

        verify(solicitudService).deleteSolicitud(idSolicitud);
    }
}

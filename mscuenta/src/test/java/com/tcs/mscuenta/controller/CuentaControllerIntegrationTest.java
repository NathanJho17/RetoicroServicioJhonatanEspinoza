package com.tcs.mscuenta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.mscuenta.application.dto.cuenta.CuentaCrearDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CuentaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCuentaSuccessfully() throws Exception {
        CuentaCrearDTO dto = new CuentaCrearDTO();
        dto.setCliente("Jhonatan Espinoza");
        dto.setTipoCuenta("Ahorro");
        dto.setSaldoInicial(100.0);
        dto.setSaldoDisponible(100.0);
        dto.setNumeroCuenta("44444444333555");

        mockMvc.perform(post("/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.satisfactorio").value(true))
                .andExpect(jsonPath("$.datos").isNumber());
    }
}

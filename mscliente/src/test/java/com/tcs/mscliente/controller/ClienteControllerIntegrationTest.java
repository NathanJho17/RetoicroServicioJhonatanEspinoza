package com.tcs.mscliente.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.mscliente.application.dto.ClienteCrearDTO;
import com.tcs.mscliente.domain.events.IClienteEventPublisher;
import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.domain.repository.IClienteRepository;

import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Limpia la DB después de cada test

public class ClienteControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;
        @Autowired
        private IClienteRepository clienteRepository;
        @MockitoBean // solo si deseas simular el publisher
        private IClienteEventPublisher clienteEventPublisher;

        @Nested
        @DisplayName("POST /clientes")
        class CrearClienteTests {
                ClienteCrearDTO dto;
                Cliente cliente;

                @BeforeEach
                void setUp() {
                        dto = new ClienteCrearDTO();
                        dto.setNombreApellido("Jhonatan Espinoza");
                        dto.setEdad(30);
                        dto.setGenero("Masculino");
                        dto.setDireccion("Av Siempre Viva");
                        dto.setContrasena("12345");

                        cliente = new Cliente();
                        cliente.setNombre(dto.getNombreApellido());
                        cliente.setEdad(dto.getEdad());
                        cliente.setGenero(dto.getGenero());
                        cliente.setDireccion(dto.getDireccion());
                        cliente.setContrasena(dto.getContrasena());
                        cliente.setIdentificacion(dto.getIdentificacion());
                        cliente.setTelefono(dto.getTelefono());

                }

                @Test
                void debeCrearClienteExitosamente() throws Exception {

                        // A
                        dto.setIdentificacion("12345678");
                        dto.setTelefono("0987654321");

                        // A & A
                        mockMvc.perform(post("/clientes")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(dto)))
                                        .andDo(print())
                                        .andExpect(status().isOk())
                                        .andExpect(jsonPath("$.satisfactorio").value(true))
                                        .andExpect(jsonPath("$.mensaje").value("Cliente creado existosamente"));

                        // Validar que el cliente fue guardado en la base de datos (opcional)
                        boolean existe = clienteRepository.getAll().stream()
                                        .anyMatch(c -> c.getIdentificacion().equals("12345678"));
                        assertTrue(existe, "El cliente debería existir en la base de datos");
                        // Verifica que se haya publicado el evento
                        verify(clienteEventPublisher, times(1)).clienteCreadoPublicar(anyInt(),
                                        eq("Jhonatan Espinoza"));
                }

                /*
                 * @Test
                 * void debeRetornarErrorBadRequestCuandoFaltanDatos() throws Exception {
                 * 
                 * // A
                 * 
                 * // A
                 * mockMvc.perform(post("/clientes")
                 * .contentType(MediaType.APPLICATION_JSON)
                 * .content(objectMapper.writeValueAsString(dto)))
                 * .andExpect(status().isBadRequest())
                 * .andExpect(jsonPath("$.identificacion")
                 * .value("La identificación es obligatoria"))
                 * .andExpect(jsonPath("$.telefono").value("El telefono es obligatorio"));
                 * 
                 * }
                 * 
                 * @Test
                 * void debeRetornarErrorBadRequestIdentificacionLongitud() throws Exception {
                 * 
                 * // A
                 * 
                 * dto.setIdentificacion("171717");
                 * 
                 * // A
                 * mockMvc.perform(post("/clientes")
                 * .contentType(MediaType.APPLICATION_JSON)
                 * .content(objectMapper.writeValueAsString(dto)))
                 * .andExpect(status().isBadRequest())
                 * .andExpect(jsonPath("$.identificacion")
                 * .value("La identificación debe tener al menos 8 caracteres"));
                 * 
                 * }
                 * 
                 * @Test
                 * void debeRetornarErrorBadRequestTelefonoLongitud() throws Exception {
                 * 
                 * // A
                 * dto.setIdentificacion("1717171717");
                 * dto.setTelefono("0987654");
                 * // A
                 * mockMvc.perform(post("/clientes")
                 * .contentType(MediaType.APPLICATION_JSON)
                 * .content(objectMapper.writeValueAsString(dto)))
                 * .andExpect(status().isBadRequest())
                 * .andExpect(jsonPath("$.telefono")
                 * .value("El teléfono debe tener al menos 9 caracteres"));
                 * 
                 * }
                 * 
                 * @Test
                 * void debeRetornarErrorBadRequestClienteNoCreado() throws Exception {
                 * 
                 * // A
                 * dto.setIdentificacion("1717171717");
                 * dto.setTelefono("0981012345");
                 * 
                 * // A
                 * mockMvc.perform(post("/clientes")
                 * .contentType(MediaType.APPLICATION_JSON)
                 * .content(objectMapper.writeValueAsString(dto)))
                 * .andExpect(status().isBadRequest())
                 * .andExpect(jsonPath("$.satisfactorio").value(false))
                 * .andExpect(jsonPath("$.mensaje").value("No se pudo crear el cliente"));
                 * 
                 * }
                 * }
                 * 
                 * @Nested
                 * 
                 * @DisplayName("PUT /clientes/{id}")
                 * class EditarClienteTests {
                 * Integer id;
                 * ClienteVerDTO clienteVerDTOMock;
                 * ClienteCrearDTO dto;
                 * 
                 * @BeforeEach
                 * void setUp() {
                 * id = 17;
                 * clienteVerDTOMock = new ClienteVerDTO();
                 * clienteVerDTOMock.setId(id);
                 * clienteVerDTOMock.setNombreApellido("Jhonatan Espinoza");
                 * clienteVerDTOMock.setEdad(30);
                 * clienteVerDTOMock.setGenero("Masculino");
                 * clienteVerDTOMock.setDireccion("Av Siempre Viva");
                 * clienteVerDTOMock.setTelefono("0987654321");
                 * clienteVerDTOMock.setIdentificacion("12345678");
                 * clienteVerDTOMock.setContrasena("12345");
                 * 
                 * dto = new ClienteCrearDTO();
                 * dto.setNombreApellido("Jhonatan Espinoza");
                 * dto.setEdad(30);
                 * dto.setGenero("Masculino");
                 * dto.setDireccion("Av Siempre Viva");
                 * dto.setContrasena("12345");
                 * }
                 * 
                 * @Test
                 * void debeEditarClientePorIdExitosamente() throws Exception {
                 * // A
                 * 
                 * 
                 * // A
                 * 
                 * mockMvc.perform(put("/clientes/{id}", id)
                 * .contentType(MediaType.APPLICATION_JSON)
                 * .content(objectMapper.writeValueAsString(dto)))
                 * .andExpect(status().isOk())
                 * .andExpect(jsonPath("$.satisfactorio").value(true))
                 * .andExpect(jsonPath("$.mensaje").value("Cliente actualizado existosamente"))
                 * .andExpect(jsonPath("$.datos.id").value(clienteVerDTOMock.getId()));
                 * 
                 * }
                 */

        }
}

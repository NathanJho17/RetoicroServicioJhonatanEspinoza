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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.mscliente.application.dto.ClienteCrearDTO;
import com.tcs.mscliente.application.dto.ClienteEditarDTO;
import com.tcs.mscliente.domain.events.IClienteEventPublisher;
import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.domain.repository.IClienteRepository;

import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ActiveProfiles("test")
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
                void debeCrearClienteExitosamenteEnH2() throws Exception {

                        // A
                        dto.setIdentificacion("1717171717");
                        dto.setTelefono("0984577444");

                        // A
                        // A
                        mockMvc.perform(post("/clientes")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(dto)))
                                        .andExpect(status().isCreated())
                                        .andExpect(jsonPath("$.satisfactorio").value(true))
                                        .andExpect(jsonPath("$.mensaje").value("Cliente creado existosamente"));

                        // Validar que el cliente fue guardado en la base de datos (opcional)
                        boolean existe = clienteRepository.getAll().stream()
                                        .anyMatch(c -> c.getIdentificacion().equals("1717171717")
                                                        && c.getTelefono().equals("0984577444")
                                                        && c.getNombre().equals("Jhonatan Espinoza"));
                        assertTrue(existe, "El cliente debería existir en la base de datos");
                        // Verifica que se haya publicado el evento
                        verify(clienteEventPublisher, times(1)).clienteCreadoPublicar(anyInt(),
                                        eq("Jhonatan Espinoza"));
                }

                @Test
                void debeRetornarErrorBadRequestCuandoFaltanDatos() throws Exception {

                        // A

                        // A
                        mockMvc.perform(post("/clientes")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(dto)))
                                        .andExpect(status().isBadRequest())
                                        .andExpect(jsonPath("$.identificacion")
                                                        .value("La identificación es obligatoria"))
                                        .andExpect(jsonPath("$.telefono").value("El telefono es obligatorio"));

                }

                @Test
                void debeRetornarErrorBadRequestIdentificacionLongitud() throws Exception {

                        // A
                        dto.setIdentificacion("171717");
                        // A
                        mockMvc.perform(post("/clientes")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(dto)))
                                        .andExpect(status().isBadRequest())
                                        .andExpect(jsonPath("$.identificacion")
                                                        .value("La identificación debe tener al menos 8 caracteres"));

                }

                @Test
                void debeRetornarErrorBadRequestTelefonoLongitud() throws Exception {

                        // A
                        dto.setIdentificacion("1717171717");
                        dto.setTelefono("0987654");
                        // A
                        mockMvc.perform(post("/clientes")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(dto)))
                                        .andExpect(status().isBadRequest())
                                        .andExpect(jsonPath("$.telefono")
                                                        .value("El teléfono debe tener al menos 9 caracteres"));

                }

                @Test
                void debeRetornarErrorBadRequestClienteNoCreado() throws Exception {

                        // A
                        dto.setIdentificacion("17171717170000000000000000000000000000");
                        dto.setTelefono("0981012345");

                        // A
                        mockMvc.perform(post("/clientes")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(dto)))
                                        .andExpect(status().isBadRequest())
                                        .andExpect(jsonPath("$.satisfactorio").value(false))
                                        .andExpect(jsonPath("$.mensaje").value("No se pudo crear el cliente"))
                                        .andExpect(jsonPath("$.error").exists());
                }
        }

        @Nested
        @DisplayName("PUT /clientes/{id}")

        class EditarClienteTests {
                ClienteEditarDTO dto;
                Integer clienteId;

                @BeforeEach
                void setUp() {

                        dto = new ClienteEditarDTO();
                        dto.setNombreApellido("Jhonatan Espinoza Actualizado");
                        dto.setEdad(30);
                        dto.setGenero("Masculino");
                        dto.setDireccion("Av Siempre Viva");
                        dto.setContrasena("12345");
                        dto.setTelefono("0981022255");
                        clienteId = 0;
                }

                @Test
                void debeEditarClientePorIdExitosamenteEnH2() throws Exception {
                        // A
                        Cliente clienteGuardado = new Cliente();
                        clienteGuardado.setIdentificacion("1234567897");
                        clienteGuardado.setTelefono("0987654321");
                        clienteGuardado.setNombre("Jhona");
                        clienteId = clienteRepository.save(clienteGuardado);

                        dto.setIdentificacion("1717171717");

                        // A
                        mockMvc.perform(put("/clientes/{id}", clienteId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(dto)))
                                        .andExpect(status().isOk())
                                        .andExpect(jsonPath("$.satisfactorio").value(true))
                                        .andExpect(jsonPath("$.mensaje").value("Cliente actualizado existosamente"));

                        Cliente clienteActualizado = clienteRepository.getById(clienteId);
                        assertNotNull(clienteActualizado);
                        assertEquals("0981022255", clienteActualizado.getTelefono());
                        assertEquals("Jhonatan Espinoza Actualizado", clienteActualizado.getNombre());

                }

                @Test
                void debeRetornarErrorBadRequestClienteNoActualizado_ClienteNoEnontrado() throws Exception {

                        // A
                        Integer idNoExistente = 9999;

                        // A & A
                        mockMvc.perform(put("/clientes/{id}", idNoExistente)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(dto)))
                                        .andExpect(status().isBadRequest())
                                        .andExpect(jsonPath("$.satisfactorio").value(false))
                                        .andExpect(jsonPath("$.mensaje")
                                                        .value("Cliente no encontrado con id " + idNoExistente))
                                        .andExpect(jsonPath("$.error").doesNotExist());
                }

        }

        @Nested
        @DisplayName("DELETE /clientes/{id}")
        class BorrarClienteTests {
                Integer clienteId;

                @BeforeEach
                void setUp() {
                        Cliente clienteGuardado = new Cliente();
                        clienteGuardado.setIdentificacion("1234567897");
                        clienteGuardado.setTelefono("0987654321");
                        clienteGuardado.setNombre("Jhona");
                        clienteId = clienteRepository.save(clienteGuardado);
                }

                @Test
                void debeBorrarClientePorIdExitosamenteEnH2() throws Exception {

                        // A
                        mockMvc.perform(delete("/clientes/{id}", clienteId))
                                        .andExpect(status().isOk())
                                        .andExpect(jsonPath("$.satisfactorio").value(true))
                                        .andExpect(jsonPath("$.mensaje").value("Cliente eliminado exitosamente"));

                        Cliente existeId = clienteRepository.getById(clienteId);
                        assertEquals(clienteId, existeId.getId());

                }

                @Test
                void debeRetornarErrorBadRequestClienteNoEliminado_ClienteNoEncontrado() throws Exception {

                        // A
                        Integer idNoExistente = 9999;

                        // A & A
                        mockMvc.perform(delete("/clientes/{id}", idNoExistente))
                                        .andExpect(status().isNotFound())
                                        .andExpect(jsonPath("$.satisfactorio").value(false))
                                        .andExpect(jsonPath("$.mensaje")
                                                        .value("Cliente no encontrado con id " + idNoExistente))
                                        .andExpect(jsonPath("$.error").doesNotExist());
                }

              
        }

        @Nested
        @DisplayName("GET /clientes")
        class VerClientesTests {

                @BeforeEach
                void setUp() {
                        // Asegúrate de que haya al menos un cliente en la base de datos
                        if (clienteRepository.getAll().isEmpty()) {
                                Cliente cliente = new Cliente();
                                cliente.setIdentificacion("12345678");
                                cliente.setTelefono("0987654321");
                                cliente.setNombre("Cliente Test");
                                clienteRepository.save(cliente);
                        }
                }

                @Test
                void debeListarTodosLosClientesExitosamenteEnH2() throws Exception {

                        // A
                        mockMvc.perform(get("/clientes"))
                                        .andExpect(status().isOk())
                                        .andExpect(jsonPath("$.satisfactorio").value(true))
                                        .andExpect(jsonPath("$.mensaje").value("Clientes obtenidos exitosamente"))
                                        .andExpect(jsonPath("$.datos").isArray());

                        // Validar que al menos un cliente exista
                        assertTrue(clienteRepository.getAll().size() > 0,
                                        "Debe haber al menos un cliente en la base de datos");
                }

               
        }
}

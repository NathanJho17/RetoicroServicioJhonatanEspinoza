package com.tcs.mscliente.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcs.mscliente.application.dto.ClienteCrearDTO;
import com.tcs.mscliente.application.mapper.ClienteDTOMapper;
import com.tcs.mscliente.application.response.RespuestaGenerica;
import com.tcs.mscliente.application.usecase.CrearClienteCase;
import com.tcs.mscliente.domain.events.IClienteEventPublisher;
import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.domain.repository.IClienteRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("CrearClienteCase Test")
public class CrearClientesCaseTest {

    @Mock
    private IClienteRepository clienteRepository;

    @Mock
    private ClienteDTOMapper clienteDTOMapper;

    @Mock
    private IClienteEventPublisher eventPublisher;

    @InjectMocks
    private CrearClienteCase crearClienteCase;

    private Integer id;

    private ClienteCrearDTO dto;

    @BeforeEach
    void setUp() {
        id = 17;
        dto = new ClienteCrearDTO();
        dto.setNombreApellido("Jhonatan Espinoza");
        dto.setEdad(31);
        dto.setIdentificacion("1234567890");
        dto.setDireccion("Av Florida");
        dto.setGenero("Masculino");
        dto.setTelefono("09999999999");
        dto.setContrasena("hello.world");
        // This method is called before each test to set up the mocks and the use case
        // instance
    }

    @Test
    void debeCrearClienteYResponderExitosamente() {

        // A

        Cliente clienteModel = new Cliente();
        clienteModel.setNombre("Jhonatan Espinoza");
        clienteModel.setEdad(31);
        clienteModel.setIdentificacion("1234567890");
        clienteModel.setDireccion("Av Florida");
        clienteModel.setGenero("Masculino");
        clienteModel.setTelefono("09999999999");
        clienteModel.setContrasena("hello.world");

        when(clienteDTOMapper.toModel(dto)).thenReturn(clienteModel);
        when(clienteRepository.save(clienteModel)).thenReturn(id);
        // A
        RespuestaGenerica<Integer> respuesta = crearClienteCase.create(dto);

        // A
        assertTrue(respuesta.isSatisfactorio());
        assertEquals(id, respuesta.getDatos());
        assertEquals("Cliente creado existosamente", respuesta.getMensaje());

        // Call publisher
        verify(eventPublisher).clienteCreadoPublicar(id, "Jhonatan Espinoza");
    }

    @Test
    void debeRetornarExcepcionAlCrearCliente(){

        //A
        String exceptionErrorMessage = "Error al crear cliente";
         Cliente clienteModel = new Cliente();
        clienteModel.setNombre("Jhonatan Espinoza");
        clienteModel.setEdad(31);
        clienteModel.setIdentificacion("1234567890");
        clienteModel.setDireccion("Av Florida");
        clienteModel.setGenero("Masculino");
        clienteModel.setTelefono("09999999999");
        clienteModel.setContrasena("hello.world");

        when(clienteDTOMapper.toModel(dto)).thenReturn(clienteModel);
        when(clienteRepository.save(clienteModel)).thenThrow(new RuntimeException(exceptionErrorMessage));

        //A
        RespuestaGenerica<Integer> crearCliente=crearClienteCase.create(dto);

        //A

        assertFalse(crearCliente.isSatisfactorio());
        assertEquals("No se pudo crear el cliente", crearCliente.getMensaje());
        assertEquals(exceptionErrorMessage, crearCliente.getError());

    }

}

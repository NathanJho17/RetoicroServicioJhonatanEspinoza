package com.tcs.mscliente.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcs.mscliente.application.dto.ClienteEditarDTO;
import com.tcs.mscliente.application.dto.ClienteVerDTO;
import com.tcs.mscliente.application.mapper.ClienteDTOMapper;
import com.tcs.mscliente.application.response.RespuestaGenerica;
import com.tcs.mscliente.application.usecase.EditarClienteCase;
import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.domain.repository.IClienteRepository;

@ExtendWith(MockitoExtension.class)
public class EditarClienteCaseTest {

    @Mock
    private IClienteRepository clienteRepository;

    @Mock
    private ClienteDTOMapper clienteDTOMapper;

    @InjectMocks
    private EditarClienteCase editarClienteCase;

    ClienteEditarDTO dto;
    Integer id;

    @BeforeEach
    void setUp() {
        // A
        id = 1;
        dto = new ClienteEditarDTO();
        dto.setNombreApellido("Jhonatan Espinoza");
        dto.setEdad(31);
        dto.setIdentificacion("1234567890");
        dto.setDireccion("Av Florida");
        dto.setGenero("Masculino");
        dto.setTelefono("09999999999");
        dto.setContrasena("hello.world");
    }

    @Test
    void debeExistirClientesAntesDeEditar() {

        // A
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre(dto.getNombreApellido());
        when(clienteRepository.getById(id)).thenReturn(cliente);

        // A
        Cliente clienteEncontrado = clienteRepository.getById(id);

        // A
        assertNotNull(clienteEncontrado);
        assertEquals("Jhonatan Espinoza", clienteEncontrado.getNombre());

    }

    @Test
    void debeRetornarClienteNoEncontrado() {

        // A
        when(clienteRepository.getById(id)).thenReturn(null);
        // A
        Cliente clienteEncontrado = clienteRepository.getById(id);

        // A
        assertNull(clienteEncontrado);
    }

    @Test
    void debeEditarClienteExitosamente() {

        // A
        Cliente clienteModel = new Cliente();
        clienteModel.setNombre("Jhonatan Espinoza");
        clienteModel.setEdad(31);
        clienteModel.setIdentificacion("1234567890");
        clienteModel.setDireccion("Av Florida");
        clienteModel.setGenero("Masculino");
        clienteModel.setTelefono("09999999999");
        clienteModel.setContrasena("hello.world");
        when(clienteRepository.getById(id)).thenReturn(clienteModel);
        when(clienteRepository.update(clienteModel)).thenReturn(clienteModel);
        // A
        RespuestaGenerica<ClienteVerDTO> clienteEditado = editarClienteCase.update(id, dto);
        // A
        assertTrue(clienteEditado.isSatisfactorio());
        assertEquals("Cliente actualizado existosamente", clienteEditado.getMensaje());

    }

}

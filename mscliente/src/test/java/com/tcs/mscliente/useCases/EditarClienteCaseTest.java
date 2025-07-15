package com.tcs.mscliente.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("EditarClienteCase Test")
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
        RespuestaGenerica<Integer> respuestaGenerica = new RespuestaGenerica<Integer>(false,
                "Cliente no encontrado con id " + id, null);
        when(clienteRepository.getById(id)).thenReturn(null);

        // A
        Cliente cliente = clienteRepository.getById(id);
        RespuestaGenerica<ClienteVerDTO> clienteEncontrado = editarClienteCase.update(id, dto);

        // A
        assertNull(cliente);
        assertEquals(respuestaGenerica, clienteEncontrado);
        assertEquals("Cliente no encontrado con id " + id, clienteEncontrado.getMensaje());

    }

    @Test
    void debeRetornarExcepcionAlEditarCliente() {
        // A
        String exceptionErrorMessage = "Error al editar cliente";
        RespuestaGenerica<ClienteVerDTO> respuestaGenerica = new RespuestaGenerica<ClienteVerDTO>(false,
                "No se pudo actualizar el cliente", null,exceptionErrorMessage);
        Cliente cliente = new Cliente();
        cliente.setId(id);

        ClienteVerDTO clienteVerDTO = new ClienteVerDTO();
        clienteVerDTO.setId(id);
        clienteVerDTO.setNombreApellido(dto.getNombreApellido());

        when(clienteRepository.getById(id)).thenReturn(cliente);
        when(clienteRepository.update(cliente)).thenThrow(new RuntimeException(exceptionErrorMessage));
/*         when(clienteDTOMapper.toVerDTO(cliente)).thenReturn(clienteVerDTO);
 */        // A
        RespuestaGenerica<ClienteVerDTO> editarCliente = editarClienteCase.update(id, dto);

        // A

        assertEquals(respuestaGenerica, editarCliente);
        assertFalse(editarCliente.isSatisfactorio());
        assertEquals("No se pudo actualizar el cliente", editarCliente.getMensaje());
        assertEquals(exceptionErrorMessage, editarCliente.getError());
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

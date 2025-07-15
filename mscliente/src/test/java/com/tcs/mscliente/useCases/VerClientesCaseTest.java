package com.tcs.mscliente.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcs.mscliente.application.dto.ClienteVerDTO;
import com.tcs.mscliente.application.mapper.ClienteDTOMapper;
import com.tcs.mscliente.application.response.RespuestaGenerica;
import com.tcs.mscliente.application.usecase.VerClientesCase;
import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.domain.repository.IClienteRepository;


@DisplayName("VerClientesCase Test")    
@ExtendWith(MockitoExtension.class)
public class VerClientesCaseTest {

    @Mock
    private IClienteRepository clienteRepository;
    @Mock
    private ClienteDTOMapper clienteDTOMapper;

    @InjectMocks
    private VerClientesCase verClientesCase;



    @Test
    void debeRetornarListadoClientes() {
       
        //A
        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setNombre("Cliente 1");
        Cliente cliente2 = new Cliente();
        cliente2.setId(2);  
        cliente2.setNombre("Cliente 2");
        List<Cliente> clientes = List.of(cliente1, cliente2);

        ClienteVerDTO clienteVerDTO1 = new ClienteVerDTO();
        clienteVerDTO1.setId(cliente1.getId());
        clienteVerDTO1.setNombreApellido(cliente1.getNombre());
        ClienteVerDTO clienteVerDTO2 = new ClienteVerDTO(); 
        clienteVerDTO2.setId(cliente2.getId());
        clienteVerDTO2.setNombreApellido(cliente2.getNombre());
        List<ClienteVerDTO> clienteVerDTOs = List.of(clienteVerDTO1, clienteVerDTO2);

        when(clienteRepository.getAll()).thenReturn(clientes);
        when(clienteDTOMapper.toVerDTOList(clientes)).thenReturn(clienteVerDTOs);

        //A
        RespuestaGenerica<List<ClienteVerDTO>> listClientes=verClientesCase.listAll();

        //A
        assertTrue(listClientes.isSatisfactorio());
        assertTrue(listClientes.getDatos().size() == 2);
        assertEquals("Clientes obtenidos exitosamente", listClientes.getMensaje());
    }

    @Test
    void debeRetornarExcepcionAlObtenerListadoClientes(){

        //A
        String mensajeError = "No se pudo obtener el listado de clientes";
        when(clienteRepository.getAll()).thenThrow(new RuntimeException(mensajeError));

        //A
        RespuestaGenerica<List<ClienteVerDTO>> listClientes = verClientesCase.listAll();

        //A
        assertFalse(listClientes.isSatisfactorio());
        assertEquals(mensajeError, listClientes.getError());
    }
    
}

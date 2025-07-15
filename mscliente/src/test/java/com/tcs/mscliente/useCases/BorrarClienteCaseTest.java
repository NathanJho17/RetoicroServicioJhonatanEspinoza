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

import com.tcs.mscliente.application.response.RespuestaGenerica;
import com.tcs.mscliente.application.usecase.BorrarClienteCase;
import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.domain.repository.IClienteRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("BorrarClienteCase Test")
public class BorrarClienteCaseTest {

    @Mock
    private IClienteRepository clienteRepository;

    @InjectMocks
    private BorrarClienteCase borrarClienteCase;

    private Integer id;

    @BeforeEach
    void setUp() {
        id = 17;
    }

    @Test
    void debeObtenerClienteAntesDeBorrar() {
        // A
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre("Jhonatan Espinoza");
        when(clienteRepository.getById(id)).thenReturn(cliente);

        // A
        Cliente respuesta = clienteRepository.getById(id);

        // A
        assertNotNull(respuesta);
        assertEquals(cliente.getNombre(), respuesta.getNombre());
    }

    @Test
    void debeRetornarClienteNoEncontrado() {
        // A
        RespuestaGenerica<Integer> respuestaGenerica = new RespuestaGenerica<Integer>(false,
                "Cliente no encontrado con id " + id, null);
        when(clienteRepository.getById(id)).thenReturn(null);

        // A
        Cliente clienteNoEncontrado = clienteRepository.getById(id);
        RespuestaGenerica<Integer> respuesta = borrarClienteCase.delete(id);
        // A
        assertNull(clienteNoEncontrado);
        assertEquals(respuestaGenerica, respuesta);
        assertEquals("Cliente no encontrado con id " + id, respuesta.getMensaje());

    }

    @Test
    void debeEliminarClienteExitosamente() {
        // A
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre("Jhonatan");

        when(clienteRepository.getById(id)).thenReturn(cliente);
        when(clienteRepository.delete(id)).thenReturn(id);

        // A
        RespuestaGenerica<Integer> respuestaDelete = borrarClienteCase.delete(id);

        // A
        assertTrue(respuestaDelete.isSatisfactorio());

        assertEquals(cliente.getId(), respuestaDelete.getDatos());
    }

    @Test
    void debeRetornarExcepcionAlEliminarCliente() {
        // A
        Cliente cliente = new Cliente();
        cliente.setId(id);
        when(clienteRepository.getById(id)).thenReturn(cliente);
        when(clienteRepository.delete(id)).thenThrow(new RuntimeException("Error al eliminar cliente"));

        // A
        RespuestaGenerica<Integer> respuesta = borrarClienteCase.delete(id);

        // A
        assertFalse(respuesta.isSatisfactorio());
        assertEquals("No se puedo eliminar al cliente con id " + id, respuesta.getMensaje());
    }
}

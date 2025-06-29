package com.tcs.mscliente.application.usecase;

import org.springframework.stereotype.Service;

import com.tcs.mscliente.application.response.RespuestaGenerica;
import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.domain.repository.IClienteRepository;

@Service
public class BorrarClienteCase {

    private IClienteRepository _clienteRepository;

    public BorrarClienteCase(IClienteRepository clienteRepository) {
        _clienteRepository = clienteRepository;

    }

    public RespuestaGenerica<Integer> delete(Integer id) {

        try {
            Cliente foundCliente = _clienteRepository.getById(id);
            if (foundCliente == null) {
                return new RespuestaGenerica<Integer>(false, "Cliente no encontrado con id "+id, null);
            }

            Integer sofDeleteCliente = _clienteRepository.delete(id);
            return new RespuestaGenerica<Integer>(true, "Cliente eliminado exitosamente", sofDeleteCliente);
        } catch (Exception e) {
            return new RespuestaGenerica<Integer>(false, "No se puedo eliminar al cliente con id "+id, null, e.getMessage());

        }
    }
}

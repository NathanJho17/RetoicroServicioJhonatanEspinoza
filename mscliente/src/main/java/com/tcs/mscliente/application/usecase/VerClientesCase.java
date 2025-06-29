package com.tcs.mscliente.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcs.mscliente.application.dto.ClienteVerDTO;
import com.tcs.mscliente.application.mapper.ClienteDTOMapper;
import com.tcs.mscliente.application.response.RespuestaGenerica;
import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.domain.repository.IClienteRepository;

@Service
public class VerClientesCase {

    private IClienteRepository _clienteRepository;
    private ClienteDTOMapper _clienteDTOMapper;

    public VerClientesCase(IClienteRepository clienteRepository, ClienteDTOMapper clienteDTOMapper) {
        _clienteRepository = clienteRepository;
        _clienteDTOMapper = clienteDTOMapper;

    }

    public RespuestaGenerica<List<ClienteVerDTO>> listAll() {

        try {
            List<Cliente> listClientes = _clienteRepository.getAll();
            return new RespuestaGenerica<List<ClienteVerDTO>>(true, "Clientes obtenidos exitosamente",
                    _clienteDTOMapper.toVerDTOList(listClientes), null);
        } catch (Exception e) {
            return new RespuestaGenerica<List<ClienteVerDTO>>(false, "No se pudo obtener el listado de clientes",
                    null, e.getMessage());

        }
    }

}

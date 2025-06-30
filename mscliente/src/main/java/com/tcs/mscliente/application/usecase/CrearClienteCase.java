package com.tcs.mscliente.application.usecase;

import org.springframework.stereotype.Service;

import com.tcs.mscliente.application.dto.ClienteCrearDTO;
import com.tcs.mscliente.application.mapper.ClienteDTOMapper;
import com.tcs.mscliente.application.response.RespuestaGenerica;
import com.tcs.mscliente.domain.events.IClienteEventPublisher;
import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.domain.repository.IClienteRepository;

@Service
public class CrearClienteCase {

    private IClienteRepository _clienteRepository;
    private ClienteDTOMapper _clienteDTOMapper;
    private IClienteEventPublisher _eventPublisher;

    public CrearClienteCase(IClienteRepository clienteRepository, ClienteDTOMapper clienteDTOMapper,
    IClienteEventPublisher eventPublisher) {
        _clienteRepository = clienteRepository;
        _clienteDTOMapper = clienteDTOMapper;
        _eventPublisher= eventPublisher;

    }

    public RespuestaGenerica<Integer> create(ClienteCrearDTO dto) {

        try {
            Cliente cliente = _clienteDTOMapper.toModel(dto);
            Integer created = _clienteRepository.save(cliente);
            //Event publisher message
            _eventPublisher.clienteCreadoPublicar(created, cliente.getNombre());
            return new RespuestaGenerica<Integer>(true, "Cliente creado existosamente", created, null);
        } catch (Exception e) {

            return new RespuestaGenerica<Integer>(false, "No se pudo crear el cliente", null, e.getMessage());
        }

    }
}

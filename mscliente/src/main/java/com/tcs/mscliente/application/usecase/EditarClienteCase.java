package com.tcs.mscliente.application.usecase;

import org.springframework.stereotype.Service;

import com.tcs.mscliente.application.dto.ClienteEditarDTO;
import com.tcs.mscliente.application.dto.ClienteVerDTO;
import com.tcs.mscliente.application.mapper.ClienteDTOMapper;
import com.tcs.mscliente.application.response.RespuestaGenerica;
import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.domain.repository.IClienteRepository;

@Service
public class EditarClienteCase {

    private IClienteRepository _clienteRepository;
    private ClienteDTOMapper _clienteDTOMapper;

    public EditarClienteCase(IClienteRepository clienteRepository, ClienteDTOMapper clienteDTOMapper) {
        _clienteRepository = clienteRepository;
        _clienteDTOMapper = clienteDTOMapper;

    }

    public RespuestaGenerica<ClienteVerDTO> update(Integer id, ClienteEditarDTO dto) {

        try {
            Cliente foundCliente = _clienteRepository.getById(id);
            if (foundCliente == null) {
                return new RespuestaGenerica<ClienteVerDTO>(false, "Cliente no encontrado con id " + id, null);
            }

            foundCliente.setNombre(dto.getNombreApellido());
            foundCliente.setIdentificacion(dto.getIdentificacion());
            foundCliente.setContrasena(dto.getContrasena());
            foundCliente.setDireccion(dto.getDireccion());
            foundCliente.setEdad(dto.getEdad());
            foundCliente.setGenero(dto.getGenero());
            foundCliente.setTelefono(dto.getTelefono());

            Cliente created = _clienteRepository.update(foundCliente);
            ClienteVerDTO updated = _clienteDTOMapper.toVerDTO(created);
            return new RespuestaGenerica<ClienteVerDTO>(true, "Cliente actualizado existosamente", updated, null);
        } catch (Exception e) {

            return new RespuestaGenerica<ClienteVerDTO>(false, "No se pudo actualizar el cliente", null,
                    e.getMessage());
        }

    }
}

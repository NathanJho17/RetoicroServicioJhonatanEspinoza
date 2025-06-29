package com.tcs.mscliente.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.domain.repository.IClienteRepository;
import com.tcs.mscliente.infrastructure.mapper.ClienteMapper;
import com.tcs.mscliente.infrastructure.persistence.entity.ClienteEntity;
import com.tcs.mscliente.infrastructure.persistence.jpa.IJpaClienteRepository;

@Repository
public class ClienteRepositoryImpl implements IClienteRepository {

    private IJpaClienteRepository _jpaClienteRepository;
    private ClienteMapper _clienteMapper;

    public ClienteRepositoryImpl(IJpaClienteRepository jpaClienteRepository, ClienteMapper clienteMapper) {
        _jpaClienteRepository = jpaClienteRepository;
        _clienteMapper = clienteMapper;

    }

    @Override
    public Integer save(Cliente cliente) {
        cliente.setEstado("Activo");
        ClienteEntity entity = _clienteMapper.toEntity(cliente);
        _jpaClienteRepository.save(entity);
        return entity.getId();
    }

    @Override
    public List<Cliente> getAll() {

        List<ClienteEntity> listClients = _jpaClienteRepository.findAllByEstado("Activo");
        return _clienteMapper.toDomainList(listClients);
    }

    @Override
    public Integer delete(Integer id) {

        return _jpaClienteRepository.softDeleteById(id);

    }

    @Override
    public Cliente update(Cliente cliente) {
        ClienteEntity entity = _clienteMapper.toEntity(cliente);
        _jpaClienteRepository.save(entity);
        return _clienteMapper.toDomain(entity);
    }

    @Override
    public Cliente getById(Integer id) {

        Optional<ClienteEntity> foundCliente= _jpaClienteRepository.findById(id);
        if (foundCliente.isPresent()) {
            return _clienteMapper.toDomain(foundCliente.get());
        }
        return null;

    }

}

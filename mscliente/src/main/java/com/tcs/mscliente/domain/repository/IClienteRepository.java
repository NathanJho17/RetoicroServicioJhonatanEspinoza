package com.tcs.mscliente.domain.repository;

import java.util.List;

import com.tcs.mscliente.domain.model.Cliente;

public interface IClienteRepository {

  Integer save(Cliente cliente);

  Integer delete(Integer id);

  Cliente update(Cliente cliente);

  Cliente getById(Integer id);

  List<Cliente> getAll();
}

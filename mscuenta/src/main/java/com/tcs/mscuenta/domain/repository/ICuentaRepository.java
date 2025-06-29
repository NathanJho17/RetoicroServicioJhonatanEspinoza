package com.tcs.mscuenta.domain.repository;

import java.util.List;

import com.tcs.mscuenta.domain.model.Cuenta;

public interface ICuentaRepository {
    Integer save(Cuenta cuenta);

    List<Cuenta> getAll();

    Cuenta getByCuenta(String numeroCuenta);

    Integer delete(Integer id);

    Cuenta getById(Integer id);

     Cuenta update(Cuenta cliente);

}

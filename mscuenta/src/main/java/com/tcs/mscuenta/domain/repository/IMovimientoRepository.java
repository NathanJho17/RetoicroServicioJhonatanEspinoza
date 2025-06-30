package com.tcs.mscuenta.domain.repository;

import java.util.List;

import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.model.Movimiento;

public interface IMovimientoRepository {
    Movimiento save(Movimiento movimiento, Cuenta cuenta);

    List<Movimiento> getAll();

    List<Movimiento> getAllByCuenta(Integer cuentaId);

    List<Movimiento> getAllByNumeroCuenta(String numeroCuenta);

    void delete(Integer id);

    Movimiento getById(Integer id);

    Movimiento update(Movimiento movimiento);
}

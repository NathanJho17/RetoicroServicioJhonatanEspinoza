package com.tcs.mscuenta.domain.repository;

import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.model.Movimiento;

public interface IMovimientoRepository {
    Integer save(Movimiento movimiento,Cuenta cuenta);
}

package com.tcs.mscuenta.domain.repository;

import java.time.LocalDate;
import java.util.List;

import com.tcs.mscuenta.domain.model.Movimiento;

public interface IReporteRepository {

    List<Movimiento> getAllByCuenta(LocalDate fechaInicio, LocalDate fechaFin);
}

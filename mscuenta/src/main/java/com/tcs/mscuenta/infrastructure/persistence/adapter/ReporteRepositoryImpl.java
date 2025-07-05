package com.tcs.mscuenta.infrastructure.persistence.adapter;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tcs.mscuenta.domain.model.Movimiento;
import com.tcs.mscuenta.domain.repository.IReporteRepository;
import com.tcs.mscuenta.infrastructure.mapper.MovimientoMapper;
import com.tcs.mscuenta.infrastructure.persistence.entity.MovimientoEntity;
import com.tcs.mscuenta.infrastructure.persistence.jpa.IJpaReporteRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReporteRepositoryImpl implements IReporteRepository {

    private final IJpaReporteRepository _jpaReporteRepository;
    private final MovimientoMapper _movimientoMapper;


    @Override
    public List<Movimiento> getAllByCuenta(LocalDate fechaInicio, LocalDate fechaFin) {
        List<MovimientoEntity> listMovimientos = _jpaReporteRepository.findMovimientosByFechas(fechaInicio, fechaFin);
        return _movimientoMapper.toDomainList(listMovimientos);
    }

}

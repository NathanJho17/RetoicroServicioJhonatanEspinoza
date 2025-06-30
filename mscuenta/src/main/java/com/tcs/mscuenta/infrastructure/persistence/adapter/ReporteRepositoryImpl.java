package com.tcs.mscuenta.infrastructure.persistence.adapter;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tcs.mscuenta.domain.model.Movimiento;
import com.tcs.mscuenta.domain.repository.IReporteRepository;
import com.tcs.mscuenta.infrastructure.mapper.MovimientoMapper;
import com.tcs.mscuenta.infrastructure.persistence.entity.MovimientoEntity;
import com.tcs.mscuenta.infrastructure.persistence.jpa.IJpaReporteRepository;

@Repository
public class ReporteRepositoryImpl implements IReporteRepository {

    private IJpaReporteRepository _jpaReporteRepository;
    private MovimientoMapper _movimientoMapper;

    public ReporteRepositoryImpl(IJpaReporteRepository jpaReporteRepository,
            MovimientoMapper movimientoMapper) {
        _jpaReporteRepository = jpaReporteRepository;
       _movimientoMapper = movimientoMapper;

    }

    @Override
    public List<Movimiento> getAllByCuenta(LocalDate fechaInicio, LocalDate fechaFin) {
        List<MovimientoEntity> listMovimientos = _jpaReporteRepository.findMovimientosByFechas(fechaInicio, fechaFin);
        return _movimientoMapper.toDomainList(listMovimientos);
    }

}

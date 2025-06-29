package com.tcs.mscuenta.infrastructure.persistence.adapter;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.model.Movimiento;
import com.tcs.mscuenta.domain.repository.IMovimientoRepository;
import com.tcs.mscuenta.infrastructure.mapper.CuentaMapper;
import com.tcs.mscuenta.infrastructure.mapper.MovimientoMapper;
import com.tcs.mscuenta.infrastructure.persistence.entity.CuentaEntity;
import com.tcs.mscuenta.infrastructure.persistence.entity.MovimientoEntity;
import com.tcs.mscuenta.infrastructure.persistence.jpa.IJpaMovimientoRepository;

@Repository
public class MovimientoRepositoryImpl implements IMovimientoRepository {

    private IJpaMovimientoRepository _jpaMovimientoRepository;
    private MovimientoMapper _movimientoMapper;
    private CuentaMapper _cuentaMapper;

    public MovimientoRepositoryImpl(IJpaMovimientoRepository jpaMovimientoRepository,
            MovimientoMapper movimientoMapper, CuentaMapper cuentaMapper) {
        _jpaMovimientoRepository = jpaMovimientoRepository;
        _movimientoMapper = movimientoMapper;
        _cuentaMapper = cuentaMapper;

    }

    @Override
    public Integer save(Movimiento movimiento, Cuenta cuenta) {
        MovimientoEntity movimientoEntity = _movimientoMapper.toEntity(movimiento);
        CuentaEntity cuentaEntity = _cuentaMapper.toEntity(cuenta);
        movimientoEntity.setCuenta(cuentaEntity);
        movimientoEntity.setFecha(LocalDate.now());
        _jpaMovimientoRepository.save(movimientoEntity);
        return movimientoEntity.getId();
    }

}

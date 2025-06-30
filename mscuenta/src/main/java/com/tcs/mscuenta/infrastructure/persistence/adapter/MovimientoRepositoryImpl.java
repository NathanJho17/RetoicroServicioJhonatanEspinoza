package com.tcs.mscuenta.infrastructure.persistence.adapter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.model.Movimiento;
import com.tcs.mscuenta.domain.repository.IMovimientoRepository;
import com.tcs.mscuenta.infrastructure.mapper.CuentaMapper;
import com.tcs.mscuenta.infrastructure.mapper.MovimientoMapper;
import com.tcs.mscuenta.infrastructure.persistence.entity.CuentaEntity;
import com.tcs.mscuenta.infrastructure.persistence.entity.MovimientoEntity;
import com.tcs.mscuenta.infrastructure.persistence.jpa.IJpaCuentaRepository;
import com.tcs.mscuenta.infrastructure.persistence.jpa.IJpaMovimientoRepository;

@Repository
public class MovimientoRepositoryImpl implements IMovimientoRepository {

    private IJpaMovimientoRepository _jpaMovimientoRepository;
    private MovimientoMapper _movimientoMapper;
    private CuentaMapper _cuentaMapper;
    private IJpaCuentaRepository _jpaCuentaRepository;

    public MovimientoRepositoryImpl(IJpaMovimientoRepository jpaMovimientoRepository,
            MovimientoMapper movimientoMapper, CuentaMapper cuentaMapper,
            IJpaCuentaRepository jpaCuentaRepository) {
        _jpaMovimientoRepository = jpaMovimientoRepository;
        _movimientoMapper = movimientoMapper;
        _cuentaMapper = cuentaMapper;
        _jpaCuentaRepository = jpaCuentaRepository;

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

    @Override
    public List<Movimiento> getAll() {
        List<MovimientoEntity> listMovimientos = _jpaMovimientoRepository.findAll();
        return _movimientoMapper.toDomainList(listMovimientos);
    }

    @Override
    public List<Movimiento> getAllByCuenta(Integer cuentaId) {

        Optional<CuentaEntity> foundCuenta = _jpaCuentaRepository.findById(cuentaId);
        if (foundCuenta.isPresent()) {
            List<MovimientoEntity> listMovimientosCuenta = _jpaMovimientoRepository.findAllByCuenta(foundCuenta.get());
            return _movimientoMapper.toDomainList(listMovimientosCuenta);
        }
        return null;

    }

    @Override
    public List<Movimiento> getAllByNumeroCuenta(String numeroCuenta) {

        List<MovimientoEntity> listMovimientoEntities = _jpaMovimientoRepository.findByNumeroCuenta(numeroCuenta);
        return _movimientoMapper.toDomainList(listMovimientoEntities);
    }

    @Override
    public void delete(Integer id) {
        _jpaMovimientoRepository.deleteById(id);
    }

    @Override
    public Movimiento getById(Integer id) {

        Optional<MovimientoEntity> foundMovimiento = _jpaMovimientoRepository.findById(id);
        if (foundMovimiento.isPresent()) {
            return _movimientoMapper.toDomain(foundMovimiento.get());
        }
        return null;
    }

    @Override
    public Movimiento update(Movimiento movimiento) {

        MovimientoEntity entity = _movimientoMapper.toEntity(movimiento);
        entity.setFecha(LocalDate.now());
        _jpaMovimientoRepository.save(entity);
        return _movimientoMapper.toDomain(entity);
    }

}

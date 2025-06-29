package com.tcs.mscuenta.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.repository.ICuentaRepository;
import com.tcs.mscuenta.infrastructure.mapper.CuentaMapper;
import com.tcs.mscuenta.infrastructure.persistence.entity.CuentaEntity;
import com.tcs.mscuenta.infrastructure.persistence.jpa.IJpaCuentaRepository;

@Repository
public class CuentaRepositoryImpl implements ICuentaRepository {

    private IJpaCuentaRepository _jpaCuentaRepository;
    private CuentaMapper _cuentaMapper;

    public CuentaRepositoryImpl(IJpaCuentaRepository jpaCuentaRepository, CuentaMapper cuentaMapper) {
        _jpaCuentaRepository = jpaCuentaRepository;
        _cuentaMapper = cuentaMapper;

    }

    @Override
    public Integer save(Cuenta cuenta) {
        CuentaEntity cuentaEntity = _cuentaMapper.toEntity(cuenta);
        cuentaEntity.setEstado(true);
        _jpaCuentaRepository.save(cuentaEntity);
        return cuentaEntity.getId();
    }

    @Override
    public List<Cuenta> getAll() {
        List<CuentaEntity> listCuentas = _jpaCuentaRepository.findAllByEstado(true);
        return _cuentaMapper.toDomainList(listCuentas);
    }

    @Override
    public Cuenta getByCuenta(String numeroCuenta) {

        CuentaEntity foundCuenta = _jpaCuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (foundCuenta.getId() != null) {
            return _cuentaMapper.toDomain(foundCuenta);
        } else {
            return null;
        }
    }

    @Override
    public Integer delete(Integer id) {
        return _jpaCuentaRepository.softDeleteById(id);
    }

    @Override
    public Cuenta getById(Integer id) {

        Optional<CuentaEntity> foundCuenta = _jpaCuentaRepository.findById(id);
        if (foundCuenta.isPresent()) {
            return _cuentaMapper.toDomain(foundCuenta.get());
        }
        return null;
    }

    @Override
    public Cuenta update(Cuenta cliente) {
        CuentaEntity entity = _cuentaMapper.toEntity(cliente);
        _jpaCuentaRepository.save(entity);
        return _cuentaMapper.toDomain(entity);
    }

}

package com.tcs.mscuenta.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.infrastructure.persistence.entity.CuentaEntity;

@Mapper(componentModel = "spring")
public interface CuentaMapper {
    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    Cuenta toDomain(CuentaEntity entity);
    List<Cuenta> toDomainList(List<CuentaEntity> entity);
    CuentaEntity toEntity(Cuenta cuenta);

}

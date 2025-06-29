package com.tcs.mscuenta.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.tcs.mscuenta.domain.model.Movimiento;
import com.tcs.mscuenta.infrastructure.persistence.entity.MovimientoEntity;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

     MovimientoMapper INSTANCE = Mappers.getMapper(MovimientoMapper.class);

    Movimiento toDomain(MovimientoEntity entity);
    List<Movimiento> toDomainList(List<MovimientoEntity> entity);
    MovimientoEntity toEntity(Movimiento movimiento);
}

package com.tcs.mscuenta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tcs.mscuenta.application.dto.movimiento.MovimientoCrearDTO;
import com.tcs.mscuenta.domain.model.Movimiento;

@Mapper(componentModel = "spring")
public interface MovimientoDTOMapper {

    @Mapping(source = "idCuenta", target = "cuentaId")
    MovimientoCrearDTO toDTO(Movimiento cuenta);

    @Mapping(source = "cuentaId", target = "idCuenta")

    Movimiento toDomain(MovimientoCrearDTO dto);

    // CuentaVerDTO toVerDTO(Cuenta cuenta);

    // List<CuentaVerDTO> toVerDTOList(List<Cuenta> cuentas);
}

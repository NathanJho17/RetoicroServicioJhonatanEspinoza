package com.tcs.mscuenta.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tcs.mscuenta.application.dto.cuenta.CuentaCrearDTO;
import com.tcs.mscuenta.application.dto.cuenta.CuentaVerDTO;
import com.tcs.mscuenta.domain.model.Cuenta;

@Mapper(componentModel = "spring")
public interface CuentaDTOMapper {

    CuentaCrearDTO toDTO(Cuenta cuenta);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Cuenta toDomain(CuentaCrearDTO dto);

    CuentaVerDTO toVerDTO(Cuenta cuenta);

    List<CuentaVerDTO> toVerDTOList(List<Cuenta> cuentas);

}

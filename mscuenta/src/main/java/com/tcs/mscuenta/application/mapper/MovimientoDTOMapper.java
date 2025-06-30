package com.tcs.mscuenta.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tcs.mscuenta.application.dto.movimiento.MovimientoCrearDTO;
import com.tcs.mscuenta.application.dto.movimiento.MovimientoVerDTO;
import com.tcs.mscuenta.domain.model.Movimiento;

@Mapper(componentModel = "spring")
public interface MovimientoDTOMapper {

    @Mapping(source = "idCuenta", target = "cuentaId")
    MovimientoCrearDTO toDTO(Movimiento cuenta);

    @Mapping(source = "cuentaId", target = "idCuenta")
    @Mapping(target = "saldo", ignore = true)
    Movimiento toDomain(MovimientoCrearDTO dto);

    MovimientoVerDTO toVerDTO(Movimiento movimiento);

    List<MovimientoVerDTO> toVerDTOList(List<Movimiento> movimientos);
}

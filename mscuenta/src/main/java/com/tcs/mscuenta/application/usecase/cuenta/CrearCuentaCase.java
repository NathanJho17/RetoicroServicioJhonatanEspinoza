package com.tcs.mscuenta.application.usecase.cuenta;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.dto.cuenta.CuentaCrearDTO;
import com.tcs.mscuenta.application.mapper.CuentaDTOMapper;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.repository.ICuentaRepository;

@Service
public class CrearCuentaCase {

    private ICuentaRepository _cuentaRepository;
    private CuentaDTOMapper _cuentaDTOMapper;

    public CrearCuentaCase(ICuentaRepository cuentaRepository, CuentaDTOMapper cuentaDTOMapper) {
        _cuentaRepository = cuentaRepository;
        _cuentaDTOMapper = cuentaDTOMapper;

    }

    public RespuestaGenerica<Integer> saveCuenta(CuentaCrearDTO dto) {

        try {
            Cuenta cuenta = _cuentaDTOMapper.toDomain(dto);
            Integer createdCuenta = _cuentaRepository.save(cuenta);
            return new RespuestaGenerica<Integer>(true, "Cuenta creada exitosamente", createdCuenta);
        } catch (Exception e) {
            return new RespuestaGenerica<Integer>(false, "No se pudo crear la cuenta", null, e.getMessage());
        }
    }
}

package com.tcs.mscuenta.application.usecase.cuenta;


import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.dto.cuenta.CuentaVerDTO;
import com.tcs.mscuenta.application.mapper.CuentaDTOMapper;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.repository.ICuentaRepository;

@Service
public class VerCuentaPorNumeroCase {

    private ICuentaRepository _cuentaRepository;
    private CuentaDTOMapper _cuentaDTOMapper;

     public VerCuentaPorNumeroCase(ICuentaRepository cuentaRepository, CuentaDTOMapper cuentaDTOMapper) {
        _cuentaRepository = cuentaRepository;
        _cuentaDTOMapper = cuentaDTOMapper;

    }

     public RespuestaGenerica<CuentaVerDTO> getByNumeroCuenta(String numeroCuenta) {

        try {
            Cuenta cuenta = _cuentaRepository.getByCuenta(numeroCuenta);
            return new RespuestaGenerica<CuentaVerDTO>(true, "Cuenta obtenida exitosamente",
                    _cuentaDTOMapper.toVerDTO(cuenta), null);
        } catch (Exception e) {
            return new RespuestaGenerica<CuentaVerDTO>(false, "No se pudo obtener la cuenta "+numeroCuenta,
                    null, e.getMessage());

        }
    }
}

package com.tcs.mscuenta.application.usecase.cuenta;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.dto.cuenta.CuentaVerDTO;
import com.tcs.mscuenta.application.mapper.CuentaDTOMapper;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.repository.ICuentaRepository;

@Service
public class VerCuentasCase {

    private ICuentaRepository _cuentaRepository;
    private CuentaDTOMapper _cuentaDTOMapper;

    public VerCuentasCase(ICuentaRepository cuentaRepository, CuentaDTOMapper cuentaDTOMapper) {
        _cuentaRepository = cuentaRepository;
        _cuentaDTOMapper = cuentaDTOMapper;

    }

     public RespuestaGenerica<List<CuentaVerDTO>> listAll() {

        try {
            List<Cuenta> listCuentas = _cuentaRepository.getAll();
            return new RespuestaGenerica<List<CuentaVerDTO>>(true, "Cuentas obtenidos exitosamente",
                    _cuentaDTOMapper.toVerDTOList(listCuentas), null);
        } catch (Exception e) {
            return new RespuestaGenerica<List<CuentaVerDTO>>(false, "No se pudo obtener el listado de cuentas",
                    null, e.getMessage());

        }
    }

}

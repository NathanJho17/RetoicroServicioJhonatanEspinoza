package com.tcs.mscuenta.application.usecase.cuenta;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.dto.cuenta.CuentaEditarDTO;
import com.tcs.mscuenta.application.dto.cuenta.CuentaVerDTO;
import com.tcs.mscuenta.application.mapper.CuentaDTOMapper;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.repository.ICuentaRepository;

@Service
public class EditarCuentaCase {

    private ICuentaRepository _cuentaRepository;
    private CuentaDTOMapper _cuentaDTOMapper;

    public EditarCuentaCase(ICuentaRepository cuentaRepository, CuentaDTOMapper cuentaDTOMapper) {
        _cuentaRepository = cuentaRepository;
        _cuentaDTOMapper = cuentaDTOMapper;

    }

    public RespuestaGenerica<CuentaVerDTO> update(Integer id, CuentaEditarDTO dto) {

        try {
            Cuenta foundCuenta = _cuentaRepository.getById(id);
            if (foundCuenta == null) {
                return new RespuestaGenerica<CuentaVerDTO>(false, "Cuenta no encontrada con id " + id, null);
            }

            foundCuenta.setNumeroCuenta(dto.getNumeroCuenta());
            foundCuenta.setSaldoInicial(dto.getSaldoInicial());
            foundCuenta.setTipoCuenta(dto.getTipoCuenta());
           

            Cuenta created = _cuentaRepository.update(foundCuenta);
            CuentaVerDTO updated = _cuentaDTOMapper.toVerDTO(created);
            return new RespuestaGenerica<CuentaVerDTO>(true, "Cuenta actualizada existosamente", updated, null);
        } catch (Exception e) {

            return new RespuestaGenerica<CuentaVerDTO>(false, "No se pudo actualizar la cuenta", null,
                    e.getMessage());
        }

    }
}

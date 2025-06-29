package com.tcs.mscuenta.application.usecase.cuenta;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.repository.ICuentaRepository;

@Service
public class BorrarCuentaCase {

    private ICuentaRepository _cuentaRepository;

    public BorrarCuentaCase(ICuentaRepository cuentaRepository) {
        _cuentaRepository = cuentaRepository;

    }

    public RespuestaGenerica<Integer> delete(Integer id) {

        try {
            Cuenta foundCuenta = _cuentaRepository.getById(id);
            if (foundCuenta == null) {
                return new RespuestaGenerica<Integer>(false, "Cuenta no encontrada con id " + id, null);
            }

            Integer sofDeleteCliente = _cuentaRepository.delete(id);
            return new RespuestaGenerica<Integer>(true, "Cuenta eliminada exitosamente", sofDeleteCliente);
        } catch (Exception e) {
            return new RespuestaGenerica<Integer>(false, "No se puedo eliminar la cuenta con id " + id, null,
                    e.getMessage());

        }
    }
}

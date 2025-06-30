package com.tcs.mscuenta.application.usecase.movimiento;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.model.Movimiento;
import com.tcs.mscuenta.domain.repository.ICuentaRepository;
import com.tcs.mscuenta.domain.repository.IMovimientoRepository;

@Service
public class BorrarMovimientoCase {

    private IMovimientoRepository _movimientoRepository;
    private ICuentaRepository _cuentaRepository;
    private double _restoreSaldo;

    public BorrarMovimientoCase(IMovimientoRepository movimientoRepository,
            ICuentaRepository cuentaRepository) {
        _movimientoRepository = movimientoRepository;
        _cuentaRepository = cuentaRepository;
        _restoreSaldo = 0;
    }

    public RespuestaGenerica<Boolean> hardDelete(Integer id) {

        try {
            Movimiento foundMovimiento = _movimientoRepository.getById(id);
            if (foundMovimiento == null) {
                return new RespuestaGenerica<Boolean>(false, "Movimiento no encontrado con id " + id,
                        null);
            }
            // Get cuenta
            Cuenta foundCuenta = _cuentaRepository.getById(foundMovimiento.getIdCuenta());
            // New saldo value if 'Retiro' or 'Deposito'
            _restoreSaldo = foundCuenta.getSaldoDisponible() + foundMovimiento.getValor();
            _movimientoRepository.delete(id);
            foundCuenta.setSaldoDisponible(_restoreSaldo);
            Cuenta updateCuenta = _cuentaRepository.update(foundCuenta);
            return new RespuestaGenerica<Boolean>(true,
                    "Movimiento eliminado exitosamente, no se puede recuperar, saldo restaurado a "
                            + updateCuenta.getSaldoDisponible(),
                    true, null);
        } catch (Exception e) {
            return new RespuestaGenerica<Boolean>(false, "No se pudo eliminar el movimiento",
                    null, e.getMessage());

        }
    }
}

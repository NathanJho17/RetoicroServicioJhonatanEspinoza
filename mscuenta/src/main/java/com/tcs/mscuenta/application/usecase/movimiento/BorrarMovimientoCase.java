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
    private double _restoreSaldo;
    private ICuentaRepository _cuentaRepository;

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
            switch (foundMovimiento.getTipoMovimiento()) {
                case "Retiro":
                    _restoreSaldo = foundCuenta.getSaldoInicial() + foundMovimiento.getValor();
                    break;
                case "Deposito":
                    _restoreSaldo = foundCuenta.getSaldoInicial() - foundMovimiento.getValor();
                    break;
            }
            _movimientoRepository.delete(id);
            foundCuenta.setSaldoInicial(_restoreSaldo);
            Cuenta updateCuenta = _cuentaRepository.update(foundCuenta);
            return new RespuestaGenerica<Boolean>(true,
                    "Movimiento eliminado exitosamente, no se puede recuperar, saldo restaurado a "
                            + updateCuenta.getSaldoInicial(),
                    true, null);
        } catch (Exception e) {
            return new RespuestaGenerica<Boolean>(false, "No se pudo eliminar el movimiento",
                    null, e.getMessage());

        }
    }
}

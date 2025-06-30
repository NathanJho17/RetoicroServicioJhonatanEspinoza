package com.tcs.mscuenta.application.usecase.movimiento;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.dto.movimiento.MovimientoEditarDTO;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.model.Movimiento;
import com.tcs.mscuenta.domain.repository.ICuentaRepository;
import com.tcs.mscuenta.domain.repository.IMovimientoRepository;

@Service
public class EditarMovimientoCase {

    private IMovimientoRepository _movimientoRepository;
    private ICuentaRepository _cuentaRepository;
    private double _newSaldo;

    public EditarMovimientoCase(IMovimientoRepository movimientoRepository,
            ICuentaRepository cuentaRepository) {
        _movimientoRepository = movimientoRepository;
        _cuentaRepository = cuentaRepository;
        _newSaldo = 0;
    }

    public RespuestaGenerica<Boolean> updateMovimiento(MovimientoEditarDTO dto, Integer id) {

        try {

            Cuenta foundCuenta = _cuentaRepository.getById(dto.getCuentaId());
            if (foundCuenta == null) {
                return new RespuestaGenerica<Boolean>(false, "Cuenta no encontrada con id " + dto.getCuentaId(), null);
            }
            if (foundCuenta.getSaldoInicial() == 0 && dto.getTipoMovimiento().equals("Retiro")) {
                return new RespuestaGenerica<Boolean>(false, "Saldo no disponible en cuenta", null);
            }

            Movimiento foundMovimiento = _movimientoRepository.getById(id);
            if (foundMovimiento == null) {
                return new RespuestaGenerica<Boolean>(false, "Movimiento no encontrado con id " + id, null);
            }

            // New saldo value if 'Retiro' or 'Deposito'
            switch (dto.getTipoMovimiento()) {
                case "Retiro":
                    _newSaldo = (foundCuenta.getSaldoInicial() - foundMovimiento.getValor()) - dto.getValor();
                    break;
                case "Deposito":
                    _newSaldo = (foundCuenta.getSaldoInicial() - foundMovimiento.getValor()) + dto.getValor();
                    break;
            }

            foundMovimiento.setSaldo(_newSaldo);
            foundMovimiento.setValor(dto.getValor());
            foundMovimiento.setTipoMovimiento(dto.getTipoMovimiento());
            _movimientoRepository.update(foundMovimiento);

            foundCuenta.setSaldoInicial(_newSaldo);
            Cuenta updateCuenta = _cuentaRepository.update(foundCuenta);
            return new RespuestaGenerica<Boolean>(true,
                    "Se ha realizado una actualización al movimiento tipo " + dto.getTipoMovimiento()
                            + " exitosamente. Saldo actual: "
                            + updateCuenta.getSaldoInicial(),
                    true);
        } catch (Exception e) {
            return new RespuestaGenerica<Boolean>(false,
                    "No se pudo actualizar el movimiento tipo " + dto.getTipoMovimiento(), null, e.getMessage());
        }
    }
}

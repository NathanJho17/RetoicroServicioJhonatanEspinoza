package com.tcs.mscuenta.application.usecase.movimiento;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.dto.movimiento.MovimientoCrearDTO;
import com.tcs.mscuenta.application.mapper.MovimientoDTOMapper;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.model.Movimiento;
import com.tcs.mscuenta.domain.repository.ICuentaRepository;
import com.tcs.mscuenta.domain.repository.IMovimientoRepository;

@Service
public class CrearMovimientoCase {

    private IMovimientoRepository _movimientoRepository;
    private MovimientoDTOMapper _movimientoDTOMapper;
    private ICuentaRepository _cuentaRepository;
    private double _newSaldo;

    public CrearMovimientoCase(IMovimientoRepository movimientoRepository, MovimientoDTOMapper movimientoDTOMapper,
            ICuentaRepository cuentaRepository) {
        _movimientoRepository = movimientoRepository;
        _movimientoDTOMapper = movimientoDTOMapper;
        _cuentaRepository = cuentaRepository;
        _newSaldo = 0;
    }

    public RespuestaGenerica<Integer> saveMovimiento(MovimientoCrearDTO dto) {

        try {

            Cuenta foundCuenta = _cuentaRepository.getById(dto.getCuentaId());
            if (foundCuenta == null) {
                return new RespuestaGenerica<Integer>(false, "Cuenta no encontrada con id " + dto.getCuentaId(), null);
            }
            if (foundCuenta.getSaldoInicial() == 0 && dto.getTipoMovimiento().equals("Retiro")) {
                return new RespuestaGenerica<Integer>(false, "Saldo no disponible en cuenta", null);
            }
            // New saldo value if 'Retiro' or 'Deposito'
            switch (dto.getTipoMovimiento()) {
                case "Retiro":
                    _newSaldo = foundCuenta.getSaldoInicial() - dto.getValor();
                    break;
                case "Deposito":
                    _newSaldo = foundCuenta.getSaldoInicial() + dto.getValor();
                    break;
            }
            // Create a new 'Movimiento'
            Movimiento movimiento = _movimientoDTOMapper.toDomain(dto);
            movimiento.setSaldo(_newSaldo);
            Integer createdMovimiento = _movimientoRepository.save(movimiento, foundCuenta);

            foundCuenta.setSaldoInicial(_newSaldo);
            Cuenta updateCuenta = _cuentaRepository.update(foundCuenta);
            return new RespuestaGenerica<Integer>(true,
                    "Se ha realizado un movimiento tipo " + dto.getTipoMovimiento() + " exitosamente. Saldo actual: "
                            + updateCuenta.getSaldoInicial(),
                    createdMovimiento);
        } catch (Exception e) {
            return new RespuestaGenerica<Integer>(false,
                    "No se pudo crear el movimiento tipo " + dto.getTipoMovimiento(), null, e.getMessage());
        }
    }
}

package com.tcs.mscuenta.application.usecase.movimiento;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.dto.movimiento.MovimientoCrearDTO;
import com.tcs.mscuenta.application.dto.movimiento.MovimientoVerDTO;
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

    public RespuestaGenerica<MovimientoVerDTO> saveMovimiento(MovimientoCrearDTO dto) {

        try {

            Cuenta foundCuenta = _cuentaRepository.getById(dto.getCuentaId());
            if (foundCuenta == null) {
                return new RespuestaGenerica<MovimientoVerDTO>(false, "Cuenta no encontrada con id " + dto.getCuentaId(), null);
            }
            if (foundCuenta.getSaldoInicial() == 0 && dto.getTipoMovimiento().equals("Retiro")) {
                return new RespuestaGenerica<MovimientoVerDTO>(false, "Saldo no disponible en cuenta", null);
            }
            // New saldo value if 'Retiro' or 'Deposito'
            _newSaldo = foundCuenta.getSaldoDisponible() + dto.getValor();
           
            //Update cuente
            foundCuenta.setSaldoDisponible(_newSaldo);
            Cuenta updateCuenta = _cuentaRepository.update(foundCuenta);
             // Create a new 'Movimiento'
            Movimiento movimiento = _movimientoDTOMapper.toDomain(dto);
            movimiento.setSaldo(_newSaldo);
            Movimiento createdMovimiento = _movimientoRepository.save(movimiento, updateCuenta);
            MovimientoVerDTO movimientoVerDTO=_movimientoDTOMapper.toVerDTO(createdMovimiento);
            
            return new RespuestaGenerica<MovimientoVerDTO>(true,
                    "Se ha realizado un movimiento tipo " + dto.getTipoMovimiento() + " exitosamente. Saldo actual: "
                            +updateCuenta.getSaldoDisponible(),
                    movimientoVerDTO);
        } catch (Exception e) {
            return new RespuestaGenerica<MovimientoVerDTO>(false,
                    "No se pudo crear el movimiento tipo " + dto.getTipoMovimiento(), null, e.getMessage());
        }
    }
}

package com.tcs.mscuenta.application.usecase.movimiento;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.dto.movimiento.MovimientoEditarDTO;
import com.tcs.mscuenta.application.dto.movimiento.MovimientoVerDTO;
import com.tcs.mscuenta.application.mapper.MovimientoDTOMapper;
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
    private MovimientoDTOMapper _movimientoDTOMapper;

    public EditarMovimientoCase(IMovimientoRepository movimientoRepository,
            ICuentaRepository cuentaRepository,MovimientoDTOMapper movimientoDTOMapper) {
        _movimientoRepository = movimientoRepository;
        _cuentaRepository = cuentaRepository;
        _movimientoDTOMapper = movimientoDTOMapper;
        _newSaldo = 0;
    }

    public RespuestaGenerica<MovimientoVerDTO> updateMovimiento(MovimientoEditarDTO dto, Integer id) {

        try {

            Cuenta foundCuenta = _cuentaRepository.getById(dto.getCuentaId());
            if (foundCuenta == null) {
                return new RespuestaGenerica<MovimientoVerDTO>(false, "Cuenta no encontrada con id " + dto.getCuentaId(), null);
            }
            if (foundCuenta.getSaldoInicial() == 0 && dto.getTipoMovimiento().equals("Retiro")) {
                return new RespuestaGenerica<MovimientoVerDTO>(false, "Saldo no disponible en cuenta", null);
            }

            Movimiento foundMovimiento = _movimientoRepository.getById(id);
            if (foundMovimiento == null) {
                return new RespuestaGenerica<MovimientoVerDTO>(false, "Movimiento no encontrado con id " + id, null);
            }

            // New saldo value if 'Retiro' or 'Deposito'
           _newSaldo = (foundCuenta.getSaldoDisponible() - foundMovimiento.getValor()) + dto.getValor();

             foundCuenta.setSaldoDisponible(_newSaldo);
            Cuenta updateCuenta = _cuentaRepository.update(foundCuenta); 
            
            foundMovimiento.setSaldo(_newSaldo);
            foundMovimiento.setValor(dto.getValor());
            foundMovimiento.setTipoMovimiento(dto.getTipoMovimiento());
            Movimiento updatedMovimiento=_movimientoRepository.update(foundMovimiento);
            MovimientoVerDTO movimientoVerDTO=_movimientoDTOMapper.toVerDTO(updatedMovimiento);

           
            return new RespuestaGenerica<MovimientoVerDTO>(true,
                    "Se ha realizado una actualización al movimiento tipo " + dto.getTipoMovimiento()
                            + " exitosamente. Saldo actual: "
                            + updateCuenta.getSaldoDisponible(),
                    movimientoVerDTO);
        } catch (Exception e) {
            return new RespuestaGenerica<MovimientoVerDTO>(false,
                    "No se pudo actualizar el movimiento tipo " + dto.getTipoMovimiento(), null, e.getMessage());
        }
    }
}

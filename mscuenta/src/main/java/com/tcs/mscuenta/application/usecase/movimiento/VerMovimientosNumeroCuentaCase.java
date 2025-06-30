package com.tcs.mscuenta.application.usecase.movimiento;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.dto.movimiento.MovimientoVerDTO;
import com.tcs.mscuenta.application.mapper.MovimientoDTOMapper;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.domain.model.Cuenta;
import com.tcs.mscuenta.domain.model.Movimiento;
import com.tcs.mscuenta.domain.repository.ICuentaRepository;
import com.tcs.mscuenta.domain.repository.IMovimientoRepository;

@Service
public class VerMovimientosNumeroCuentaCase {

     private IMovimientoRepository _movimientoRepository;
    private MovimientoDTOMapper _movimientoDTOMapper;
    private ICuentaRepository _cuentaRepository;

    public VerMovimientosNumeroCuentaCase(IMovimientoRepository movimientoRepository,
            MovimientoDTOMapper movimientoDTOMapper,
            ICuentaRepository cuentaRepository) {
        _movimientoRepository = movimientoRepository;
        _movimientoDTOMapper = movimientoDTOMapper;
        _cuentaRepository = cuentaRepository;

    }

    public RespuestaGenerica<List<MovimientoVerDTO>> listAllByNumeroCuenta(String numeroCuenta) {

        try {

            Cuenta foundCuenta = _cuentaRepository.getByCuenta(numeroCuenta);
            if (foundCuenta == null) {
                return new RespuestaGenerica<List<MovimientoVerDTO>>(false, "Cuenta no encontrada " + numeroCuenta,
                        null);
            }
            List<Movimiento> listMovimientos = _movimientoRepository.getAllByNumeroCuenta(numeroCuenta);
            return new RespuestaGenerica<List<MovimientoVerDTO>>(true, "Movimientos obtenidos exitosamente con la cuenta "+numeroCuenta,
                    _movimientoDTOMapper.toVerDTOList(listMovimientos), null);
        } catch (Exception e) {
            return new RespuestaGenerica<List<MovimientoVerDTO>>(false, "No se pudo obtener el listado de movimientos para la cuenta "+numeroCuenta,
                    null, e.getMessage());

        }
    }

}

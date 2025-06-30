package com.tcs.mscuenta.application.usecase.movimiento;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.dto.movimiento.MovimientoVerDTO;
import com.tcs.mscuenta.application.mapper.MovimientoDTOMapper;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.domain.model.Movimiento;
import com.tcs.mscuenta.domain.repository.IMovimientoRepository;

@Service
public class VerMovimientosCase {

    private IMovimientoRepository _movimientoRepository;
    private MovimientoDTOMapper _movimientoDTOMapper;

    public VerMovimientosCase(IMovimientoRepository movimientoRepository, MovimientoDTOMapper movimientoDTOMapper) {
        _movimientoRepository = movimientoRepository;
        _movimientoDTOMapper = movimientoDTOMapper;

    }

     public RespuestaGenerica<List<MovimientoVerDTO>> listAll() {

        try {
            List<Movimiento> listMovimientos= _movimientoRepository.getAll();
            return new RespuestaGenerica<List<MovimientoVerDTO>>(true, "Movimientos obtenidos exitosamente",
                    _movimientoDTOMapper.toVerDTOList(listMovimientos), null);
        } catch (Exception e) {
            return new RespuestaGenerica<List<MovimientoVerDTO>>(false, "No se pudo obtener el listado de movimientos",
                    null, e.getMessage());

        }
    }

}

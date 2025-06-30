package com.tcs.mscuenta.application.usecase.reporte;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tcs.mscuenta.application.dto.reporte.ReporteVerDTO;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.domain.model.Movimiento;
import com.tcs.mscuenta.domain.repository.IReporteRepository;

@Service
public class BuscarReporteMovimientosCuentasCase {

    private IReporteRepository _reporteRepository;

    public BuscarReporteMovimientosCuentasCase(IReporteRepository reporteRepository) {
        _reporteRepository = reporteRepository;

    }

    public RespuestaGenerica<List<ReporteVerDTO>> findReporteByFechas(LocalDate fechaInicio, LocalDate fechaFin) {

        try {
            List<ReporteVerDTO> reporte = new ArrayList<ReporteVerDTO>();
            List<Movimiento> listMovimientos = _reporteRepository.getAllByCuenta(fechaInicio, fechaFin);

            for (Movimiento movimiento : listMovimientos) {
                ReporteVerDTO dto = new ReporteVerDTO();
                dto.setFecha(movimiento.getFecha());
                dto.setCliente(movimiento.getCuenta().getCliente());
                dto.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
                dto.setTipo(movimiento.getCuenta().getTipoCuenta());
                dto.setSaldoInicial(movimiento.getCuenta().getSaldoInicial());
                dto.setEstado(movimiento.getCuenta().getEstado());
                dto.setMovimiento(movimiento.getValor());
                dto.setSaldoDisponible(String.valueOf(movimiento.getSaldo()));
                reporte.add(dto);
            }

            return new RespuestaGenerica<List<ReporteVerDTO>>(true, "Reporte obtenido exitosamente",
                    reporte, null);
        } catch (Exception e) {
            return new RespuestaGenerica<List<ReporteVerDTO>>(false, "No se pudo obtener el reporte",
                    null, e.getMessage());

        }
    }
}

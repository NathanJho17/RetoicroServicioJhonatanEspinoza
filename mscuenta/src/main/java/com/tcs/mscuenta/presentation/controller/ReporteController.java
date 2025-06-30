package com.tcs.mscuenta.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.mscuenta.application.dto.reporte.ReporteVerDTO;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.application.usecase.reporte.BuscarReporteMovimientosCuentasCase;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private BuscarReporteMovimientosCuentasCase _buscarReporteMovimientosCuentasCase;

    public ReporteController(BuscarReporteMovimientosCuentasCase buscarReporteMovimientosCuentasCase) {
        _buscarReporteMovimientosCuentasCase = buscarReporteMovimientosCuentasCase;

    }

    @GetMapping()
    public ResponseEntity<RespuestaGenerica<List<ReporteVerDTO>>> getAllMovimientos(
            @RequestParam(name = "fechaInicio") LocalDate fechaInicio,
            @RequestParam(name = "fechaFin") LocalDate fechaFin) {

        RespuestaGenerica<List<ReporteVerDTO>> response = _buscarReporteMovimientosCuentasCase
                .findReporteByFechas(fechaInicio, fechaFin);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

}

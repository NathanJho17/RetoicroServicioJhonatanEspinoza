package com.tcs.mscuenta.application.dto.reporte;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReporteVerDTO {

    private LocalDate fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipo;
    private double saldoInicial;
    private boolean estado;
    private double movimiento;
    private String saldoDisponible;

}

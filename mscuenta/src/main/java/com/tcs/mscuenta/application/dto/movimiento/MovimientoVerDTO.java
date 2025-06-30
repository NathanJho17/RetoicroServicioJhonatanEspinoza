package com.tcs.mscuenta.application.dto.movimiento;

import com.tcs.mscuenta.application.dto.cuenta.CuentaVerDTO;

import lombok.Data;

@Data
public class MovimientoVerDTO {

    private Integer id;
    private double valor;
    private double saldo;
    private CuentaVerDTO cuenta;
    private String tipoMovimiento;
    
}

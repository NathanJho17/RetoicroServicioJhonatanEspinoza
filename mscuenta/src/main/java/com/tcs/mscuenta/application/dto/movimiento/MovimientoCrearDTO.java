package com.tcs.mscuenta.application.dto.movimiento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MovimientoCrearDTO {

    @NotNull(message = "La cuenta es obligatoria")
    private Integer cuentaId;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    @Pattern(regexp = "Retiro|Deposito", message = "El tipo de movimiento debe ser Retiro o Deposito")
    private String tipoMovimiento;

    @NotNull(message = "El valor de movimiento es obligatorio")
    private double valor;
}

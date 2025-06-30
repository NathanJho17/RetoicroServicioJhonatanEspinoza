package com.tcs.mscuenta.application.dto.cuenta;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CuentaCrearDTO {

    @NotNull(message = "El número de cuenta es obligatorio")
    private String numeroCuenta;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    @Pattern(regexp = "Ahorro|Corriente", message = "La debe ser Ahorro o Corriente")
    private String tipoCuenta;

    @DecimalMin(value = "0.0", message = "El saldo inicial debe ser mayor o igual a 0")
    private double saldoInicial;

    private String cliente;

}

package com.tcs.mscuenta.application.dto.cuenta;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CuentaVerDTO extends CuentaCrearDTO {
    private Integer id;
    private boolean estado;
}

package com.tcs.mscuenta.application.dto.cuenta;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CuentaVerDTO extends CuentaCrearDTO {
    private Integer id;
    private boolean estado;
}

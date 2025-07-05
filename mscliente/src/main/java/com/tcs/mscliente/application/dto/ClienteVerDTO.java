package com.tcs.mscliente.application.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ClienteVerDTO extends ClienteCrearDTO {
    private Integer id;
    private String nombreApellido; 
    private String estado;

}

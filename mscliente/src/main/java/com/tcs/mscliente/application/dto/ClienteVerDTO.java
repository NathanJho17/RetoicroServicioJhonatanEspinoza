package com.tcs.mscliente.application.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClienteVerDTO extends ClienteCrearDTO {
    private Integer id;
    private String nombreApellido; 
    private String estado;

}

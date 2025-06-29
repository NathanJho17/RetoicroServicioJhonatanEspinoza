package com.tcs.mscliente.infrastructure.persistence.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class PersonaEntity {

    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String telefono;
    private String direccion;
}

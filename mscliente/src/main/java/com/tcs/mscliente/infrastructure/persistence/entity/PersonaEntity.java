package com.tcs.mscliente.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class PersonaEntity {

    private String nombre;
    private String genero;
    private Integer edad;
    @Column(length = 20)
    private String identificacion;
    private String telefono;
    private String direccion;
}

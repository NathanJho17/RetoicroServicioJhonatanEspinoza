package com.tcs.mscliente.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteCrearDTO {

    @NotNull(message = "El nombre es obligatoria")
    private String nombreApellido;
    private String genero;
    private Integer edad;
    @NotNull(message = "La identificación es obligatoria")
    @Size(min = 8, message = "La identificación debe tener al menos 8 caracteres")
    private String identificacion;
    @NotNull(message = "El telefono es obligatorio")
    @Size(min = 9, message = "El teléfono debe tener al menos 9 caracteres")
    private String telefono;
    private String direccion;
    private String contrasena;

}

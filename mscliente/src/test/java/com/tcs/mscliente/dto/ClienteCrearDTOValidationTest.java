package com.tcs.mscliente.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tcs.mscliente.application.dto.ClienteCrearDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ClienteCrearDTOValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }



    @Test
    void debeHaberCeroErroresClienteDTO() {

        //A
         ClienteCrearDTO dto = new ClienteCrearDTO();
        dto.setNombreApellido("Jhonatan Espinoza");
        dto.setEdad(31);
        dto.setIdentificacion("1234567890");
        dto.setDireccion("Av Florida");
        dto.setGenero("Masculino");
        dto.setTelefono("09999999999");
        dto.setContrasena("hello.world");

        //A
        Set<ConstraintViolation<ClienteCrearDTO>> sinErrores = validator.validate(dto);
        //A
        assertEquals(true, sinErrores.isEmpty());
      
    }

    @Test
    void debeFallarIdentificacionMuyCorta() {

        // A
        ClienteCrearDTO dto = new ClienteCrearDTO();
        dto.setNombreApellido("Test");
        dto.setIdentificacion("171717");
        dto.setTelefono("0981023456");
        // A
        Set<ConstraintViolation<ClienteCrearDTO>> errores = validator.validate(dto);

        // A
        assertEquals(1, errores.size());

        assertTrue(errores.stream().anyMatch(e -> e.getPropertyPath().toString().equals("identificacion") &&
                e.getMessage().contains("al menos 8 caracteres")));

    }

    @Test
    void debeFallarTelefonoMuyCorto() {

        // A
        ClienteCrearDTO dto = new ClienteCrearDTO();
        dto.setNombreApellido("Test");
        dto.setIdentificacion("1717171717");
        dto.setTelefono("098102");

        // A
        Set<ConstraintViolation<ClienteCrearDTO>> errores = validator.validate(dto);

        // A
        assertEquals(1, errores.size());

        assertTrue(errores.stream().anyMatch(e -> e.getPropertyPath().toString().equals("telefono") &&
        e.getMessage().contains("al menos 9 caracteres")));

    }

}

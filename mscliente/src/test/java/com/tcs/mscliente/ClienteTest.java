package com.tcs.mscliente;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.tcs.mscliente.infrastructure.persistence.entity.ClienteEntity;

public class ClienteTest {

    @Test
    void debeCrearCliente() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(1);
        cliente.setContrasena("claveTest");
        cliente.setEstado("Activo");
        cliente.setNombre("Jhonatan");
        cliente.setTelefono("0981022255");

        assertEquals(1, cliente.getId());
        assertEquals("claveTest", cliente.getContrasena());
        assertEquals("Activo", cliente.getEstado());
        assertEquals("Jhonatan", cliente.getNombre());
        assertEquals("0981022255", cliente.getTelefono());
    }

    void debeTenerAlMenosNueveCaracteres(){}

    
}

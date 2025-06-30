package com.tcs.mscliente.infrastructure.messaging.dto;

import java.io.Serializable;

public class ClienteEvent implements Serializable {

    private Integer idCliente;
    private String nombre;

    public ClienteEvent() {

    }

    public ClienteEvent(Integer idCliente, String nombre) {
        this.idCliente = idCliente;
        this.nombre = nombre;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

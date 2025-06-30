package com.tcs.mscuenta.infrastructure.messaging.dto;

public class ClienteCreadoEvent {

    private Integer idCliente;
    private String nombre;

    public ClienteCreadoEvent() {}

    public ClienteCreadoEvent(Integer idCliente, String nombre) {
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

package com.tcs.mscliente.domain.model;

public class Cliente extends Persona {
    private Integer id;
    private String contrasena;
    private String estado;



    public Integer getId() {
        return id;
    }

     public void setId(Integer id) {
        this.id=id;
    }


    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contreasena) {
        this.contrasena = contreasena;
    }

     public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}

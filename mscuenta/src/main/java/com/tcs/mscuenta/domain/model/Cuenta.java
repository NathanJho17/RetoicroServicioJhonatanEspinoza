package com.tcs.mscuenta.domain.model;

public class Cuenta {

    private Integer id;

    private String numeroCuenta;

    private String tipoCuenta;

    private double saldoInicial;

    private double saldoDisponible;

    private boolean estado;

    private String cliente;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return this.numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return this.tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public double getSaldoInicial() {
        return this.saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

      public double getSaldoDisponible() {
        return this.saldoDisponible;
    }

    public void setSaldoDisponible(double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public boolean getEstado() {
        return this.estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getCliente() {
        return this.cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

}

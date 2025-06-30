package com.tcs.mscuenta.domain.model;

import java.time.LocalDate;

public class Movimiento {

    private Integer id;
    private Integer idCuenta;

    private String tipoMovimiento;

    private LocalDate fecha;

    private double valor;

    private double saldo;

    private Cuenta cuenta;

    public void setId(Integer id) {
        this.id = id;
    }

     public Integer getId() {
        return this.id;
    }

    public Integer getIdCuenta() {
        return this.idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getTipoMovimiento() {
        return this.tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Cuenta getCuenta() {
        return this.cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

}

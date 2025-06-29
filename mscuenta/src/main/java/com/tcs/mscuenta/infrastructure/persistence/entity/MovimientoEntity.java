package com.tcs.mscuenta.infrastructure.persistence.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "movimiento")
public class MovimientoEntity {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimientoid")
    private Integer Id;

    @Column(name = "cuenta_id")
    private Integer idCuenta;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    private LocalDate fecha;

    private double valor;

    private double saldo;

    @ManyToOne
    @JoinColumn(name = "cuenta_id",insertable = false,updatable = false)
    private CuentaEntity cuenta;
}

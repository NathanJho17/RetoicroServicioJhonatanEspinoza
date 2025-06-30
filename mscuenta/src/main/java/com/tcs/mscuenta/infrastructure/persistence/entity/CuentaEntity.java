package com.tcs.mscuenta.infrastructure.persistence.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cuenta")
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuentaid")
    private Integer Id;

    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @Column(name = "tipo_cuenta")

    private String tipoCuenta;

    @Column(name = "saldo_inicial")

    private double saldoInicial;

    @Column(name = "saldo_disponible")

    private double saldoDisponible;

    private boolean estado;

     private String cliente;

    @OneToMany(mappedBy = "cuenta")
    private List<MovimientoEntity> movimientos;
}

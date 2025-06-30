package com.tcs.mscuenta.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcs.mscuenta.infrastructure.persistence.entity.CuentaEntity;
import com.tcs.mscuenta.infrastructure.persistence.entity.MovimientoEntity;

public interface IJpaMovimientoRepository extends JpaRepository<MovimientoEntity, Integer> {

    List<MovimientoEntity> findAllByCuenta(CuentaEntity cuenta);

    @Query("SELECT m FROM MovimientoEntity m WHERE m.cuenta.numeroCuenta = :numero")
    List<MovimientoEntity> findByNumeroCuenta(@Param("numero") String numero);

}

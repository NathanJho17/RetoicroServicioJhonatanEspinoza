package com.tcs.mscuenta.infrastructure.persistence.jpa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcs.mscuenta.infrastructure.persistence.entity.MovimientoEntity;

public interface IJpaReporteRepository  extends JpaRepository<MovimientoEntity, Integer> {


     @Query("SELECT m FROM MovimientoEntity m WHERE m.fecha BETWEEN :fechaInicial AND :fechaFinal")
    List<MovimientoEntity> findMovimientosByFechas(@Param("fechaInicial") LocalDate  fechaInicial,
    @Param("fechaFinal") LocalDate  fechaFinal);
}
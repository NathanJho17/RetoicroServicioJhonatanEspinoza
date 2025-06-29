package com.tcs.mscuenta.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.mscuenta.infrastructure.persistence.entity.MovimientoEntity;

public interface IJpaMovimientoRepository  extends JpaRepository<MovimientoEntity,Integer>{

}

package com.tcs.mscuenta.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcs.mscuenta.infrastructure.persistence.entity.CuentaEntity;

import jakarta.transaction.Transactional;

public interface IJpaCuentaRepository extends JpaRepository<CuentaEntity,Integer> {

     List<CuentaEntity> findAllByEstado(boolean estado);

     CuentaEntity findByNumeroCuenta(String numeroCuenta);

     //Soft delete for client, update state to 'Inactivo'
    @Modifying
    @Transactional
    @Query("UPDATE CuentaEntity c SET c.estado = false WHERE c.id = :id")
    int softDeleteById(@Param("id") Integer id);
}


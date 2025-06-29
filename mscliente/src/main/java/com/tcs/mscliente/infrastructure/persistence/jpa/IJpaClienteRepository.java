package com.tcs.mscliente.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcs.mscliente.infrastructure.persistence.entity.ClienteEntity;

import jakarta.transaction.Transactional;

public interface IJpaClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    List<ClienteEntity> findAllByEstado(String estado);

    //Soft delete for client, update state to 'Inactivo'
    @Modifying
    @Transactional
    @Query("UPDATE ClienteEntity c SET c.estado = 'Inactivo' WHERE c.id = :id")
    int softDeleteById(@Param("id") Integer id);
}

package com.tcs.mscliente.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.tcs.mscliente.domain.model.Cliente;
import com.tcs.mscliente.infrastructure.persistence.entity.ClienteEntity;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Cliente toDomain(ClienteEntity entity);
    List<Cliente> toDomainList(List<ClienteEntity> entity);
    ClienteEntity toEntity(Cliente cliente);
}

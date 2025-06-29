package com.tcs.mscliente.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.tcs.mscliente.application.dto.ClienteCrearDTO;
import com.tcs.mscliente.application.dto.ClienteVerDTO;
import com.tcs.mscliente.domain.model.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteDTOMapper {

        @Mappings({
        @Mapping(source = "nombre", target = "nombreApellido")
        })
        ClienteCrearDTO toDTO(Cliente cliente);

        @Mappings({
        @Mapping(source = "nombre", target = "nombreApellido"),
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "estado", target = "estado")
        })
        ClienteVerDTO toVerDTO(Cliente clientes);

        List<ClienteVerDTO> toVerDTOList(List<Cliente> clientes);

        @Mappings({
        @Mapping(source = "nombreApellido", target = "nombre")
        })
         Cliente toModel(ClienteCrearDTO dto);
}

package br.com.mcapucho.mssysfinancadastro.dataprovider.mapper;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.dataprovider.model.ReceitaDB;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ReceitaDBMapper {

    @Mapping(target = "transactionId", source = "transactionId")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    ReceitaDB entityToDB(ReceitaEntity receitaEntity);

    @Mapping(target = "transactionId", source = "transactionId")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    ReceitaEntity dbToEntity(ReceitaDB receitaDB);
}

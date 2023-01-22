package br.com.mcapucho.mssysfinancadastro.entrypoint.mapper;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.entrypoint.http.ReceitaHttpRequest;
import br.com.mcapucho.mssysfinancadastro.entrypoint.http.ReceitaHttpResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ReceitaHttpMapper {

    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    ReceitaEntity httpToEntity(ReceitaHttpRequest receitaHttpRequest);

    @Mapping(target = "transactionId", source = "transactionId")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    ReceitaHttpResponse entityToHttp(ReceitaEntity receitaEntity);
}

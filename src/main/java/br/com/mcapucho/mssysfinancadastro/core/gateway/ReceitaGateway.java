package br.com.mcapucho.mssysfinancadastro.core.gateway;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.util.FilterPageable;

import java.util.List;

public interface ReceitaGateway {

    ReceitaEntity save(ReceitaEntity receitaEntity);
    List<ReceitaEntity> findAll();
    List<ReceitaEntity> findAll(FilterPageable filterPageable);
    ReceitaEntity findById(String transactionId);
    void deleteById(String transactionId);
    Boolean findByDescription(String description);
}

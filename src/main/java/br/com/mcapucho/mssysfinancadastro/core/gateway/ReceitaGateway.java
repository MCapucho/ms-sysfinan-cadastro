package br.com.mcapucho.mssysfinancadastro.core.gateway;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;

import java.util.List;

public interface ReceitaGateway {

    ReceitaEntity save(ReceitaEntity receitaEntity);
    List<ReceitaEntity> findAll();
    ReceitaEntity findById(String transactionId);
    void deleteById(String transactionId);
}

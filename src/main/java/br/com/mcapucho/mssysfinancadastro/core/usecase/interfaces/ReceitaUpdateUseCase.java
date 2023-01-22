package br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;

public interface ReceitaUpdateUseCase {

    ReceitaEntity execute(String transactionId, ReceitaEntity receitaEntity);
}

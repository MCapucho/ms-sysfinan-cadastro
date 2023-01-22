package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaFindByIdUseCase;
import org.springframework.stereotype.Service;

@Service
public class ReceitaFindByIdUseCaseImpl implements ReceitaFindByIdUseCase {

    private final ReceitaGateway receitaGateway;

    public ReceitaFindByIdUseCaseImpl(ReceitaGateway receitaGateway) {
        this.receitaGateway = receitaGateway;
    }

    @Override
    public ReceitaEntity execute(String transactionId) {
        ReceitaEntity receitaEntity = receitaGateway.findById(transactionId);
        return receitaEntity;
    }
}

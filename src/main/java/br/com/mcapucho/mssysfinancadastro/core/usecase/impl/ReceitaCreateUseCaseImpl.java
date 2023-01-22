package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaCreateUseCase;
import org.springframework.stereotype.Service;

@Service
public class ReceitaCreateUseCaseImpl implements ReceitaCreateUseCase {

    private final ReceitaGateway receitaGateway;

    public ReceitaCreateUseCaseImpl(ReceitaGateway receitaGateway) {
        this.receitaGateway = receitaGateway;
    }

    @Override
    public ReceitaEntity execute(ReceitaEntity receitaEntity) {
        ReceitaEntity receitaEntityCreated = receitaGateway.save(receitaEntity);
        return receitaEntityCreated;
    }
}

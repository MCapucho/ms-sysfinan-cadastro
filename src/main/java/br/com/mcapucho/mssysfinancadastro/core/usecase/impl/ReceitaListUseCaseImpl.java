package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaListUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaListUseCaseImpl implements ReceitaListUseCase {

    private final ReceitaGateway receitaGateway;

    public ReceitaListUseCaseImpl(ReceitaGateway receitaGateway) {
        this.receitaGateway = receitaGateway;
    }

    @Override
    public List<ReceitaEntity> execute() {
        List<ReceitaEntity> receitaEntityList = receitaGateway.findAll();
        return receitaEntityList;
    }
}

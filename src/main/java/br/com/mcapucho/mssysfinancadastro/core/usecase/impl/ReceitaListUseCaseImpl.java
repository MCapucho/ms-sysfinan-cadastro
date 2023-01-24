package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.exception.ResourceListEmptyException;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaListUseCase;
import br.com.mcapucho.mssysfinancadastro.util.FilterPageable;
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

        if (receitaEntityList.isEmpty()) {
            throw new ResourceListEmptyException("A lista de receita está vazia");
        }

        return receitaEntityList;
    }

    @Override
    public List<ReceitaEntity> execute(FilterPageable filterPageable) {
        List<ReceitaEntity> receitaEntityList = receitaGateway.findAll(filterPageable);

        if (receitaEntityList.isEmpty()) {
            throw new ResourceListEmptyException("A lista de receita está vazia");
        }

        return receitaEntityList;
    }
}

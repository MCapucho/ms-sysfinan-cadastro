package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaFindByDescriptionUseCase;
import org.springframework.stereotype.Service;

@Service
public class ReceitaFindByDescriptionUseCaseImpl implements ReceitaFindByDescriptionUseCase {

    private final ReceitaGateway receitaGateway;

    public ReceitaFindByDescriptionUseCaseImpl(ReceitaGateway receitaGateway) {
        this.receitaGateway = receitaGateway;
    }

    @Override
    public Boolean execute(String description) {
        return receitaGateway.findByDescription(description);
    }
}

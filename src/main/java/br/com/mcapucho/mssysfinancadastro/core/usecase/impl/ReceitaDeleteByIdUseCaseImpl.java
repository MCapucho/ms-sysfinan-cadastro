package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaDeleteByIdUseCase;
import org.springframework.stereotype.Service;

@Service
public class ReceitaDeleteByIdUseCaseImpl implements ReceitaDeleteByIdUseCase {

    private final ReceitaGateway receitaGateway;

    public ReceitaDeleteByIdUseCaseImpl(ReceitaGateway receitaGateway) {
        this.receitaGateway = receitaGateway;
    }

    @Override
    public void execute(String transactionId) {
        receitaGateway.deleteById(transactionId);
    }
}

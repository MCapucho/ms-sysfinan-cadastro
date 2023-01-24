package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaDeleteByIdUseCase;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaFindByIdUseCase;
import org.springframework.stereotype.Service;

@Service
public class ReceitaDeleteByIdUseCaseImpl implements ReceitaDeleteByIdUseCase {

    private final ReceitaGateway receitaGateway;
    private final ReceitaFindByIdUseCase receitaFindByIdUseCase;

    public ReceitaDeleteByIdUseCaseImpl(ReceitaGateway receitaGateway,
                                        ReceitaFindByIdUseCase receitaFindByIdUseCase) {
        this.receitaGateway = receitaGateway;
        this.receitaFindByIdUseCase = receitaFindByIdUseCase;
    }

    @Override
    public void execute(String transactionId) {
        ReceitaEntity receitaEntityFound = receitaFindByIdUseCase.execute(transactionId);
        receitaGateway.deleteById(receitaEntityFound.getTransactionId());
    }
}

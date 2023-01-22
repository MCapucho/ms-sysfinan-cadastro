package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaFindByIdUseCase;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaUpdateUseCase;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReceitaUpdateUseCaseImpl implements ReceitaUpdateUseCase {

    private final ReceitaGateway receitaGateway;
    private final ReceitaFindByIdUseCase receitaFindByIdUseCase;

    public ReceitaUpdateUseCaseImpl(ReceitaGateway receitaGateway,
                                    ReceitaFindByIdUseCase receitaFindByIdUseCase) {
        this.receitaGateway = receitaGateway;
        this.receitaFindByIdUseCase = receitaFindByIdUseCase;
    }

    @Override
    @Transactional
    public ReceitaEntity execute(String transactionId, ReceitaEntity receitaEntity) {
        ReceitaEntity receitaEntityFound = receitaFindByIdUseCase.execute(transactionId);
        receitaEntityFound.setDescription(receitaEntity.getDescription() != null ?
                receitaEntity.getDescription() : receitaEntityFound.getDescription());
        receitaEntityFound.setStatus(receitaEntity.getStatus() != null ?
                receitaEntity.getStatus() : receitaEntityFound.getStatus());
        return receitaGateway.save(receitaEntityFound);
    }
}

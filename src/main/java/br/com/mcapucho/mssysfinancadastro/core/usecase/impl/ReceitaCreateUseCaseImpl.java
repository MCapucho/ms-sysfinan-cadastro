package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.exception.ResourceDuplicatedException;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaCreateUseCase;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaFindByDescriptionUseCase;
import org.springframework.stereotype.Service;

@Service
public class ReceitaCreateUseCaseImpl implements ReceitaCreateUseCase {

    private final ReceitaGateway receitaGateway;
    private final ReceitaFindByDescriptionUseCase receitaFindByDescriptionUseCase;

    public ReceitaCreateUseCaseImpl(ReceitaGateway receitaGateway,
                                    ReceitaFindByDescriptionUseCase receitaFindByDescriptionUseCase) {
        this.receitaGateway = receitaGateway;
        this.receitaFindByDescriptionUseCase = receitaFindByDescriptionUseCase;
    }

    @Override
    public ReceitaEntity execute(ReceitaEntity receitaEntity) {
        if (!receitaFindByDescriptionUseCase.execute(receitaEntity.getDescription())) {
            return receitaGateway.save(receitaEntity);
        } else {
            throw new ResourceDuplicatedException("A receita já está cadastrada");
        }
    }
}

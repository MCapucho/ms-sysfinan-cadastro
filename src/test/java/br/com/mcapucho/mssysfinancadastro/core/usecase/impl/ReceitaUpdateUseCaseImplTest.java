package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.exception.ResourceNotFoundException;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaFindByIdUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ReceitaUpdateUseCaseImplTest {

    @InjectMocks
    private ReceitaUpdateUseCaseImpl receitaUpdateUseCaseImpl;
    @Mock
    private ReceitaFindByIdUseCase receitaFindByIdUseCase;
    @Mock
    private ReceitaGateway receitaGateway;

    private ReceitaEntity receitaEntity;

    @BeforeEach
    void setUp() {
        receitaEntity = ReceitaEntity.builder()
                .transactionId(UUID.randomUUID().toString())
                .description("Teste receita 01")
                .status(true)
                .build();
    }

    @Test
    void execute_success() {
        Mockito.when(receitaFindByIdUseCase.execute(Mockito.anyString()))
                .thenReturn(receitaEntity);

        Mockito.when(receitaGateway.save(Mockito.any(ReceitaEntity.class)))
                .thenReturn(receitaEntity);

        ReceitaEntity receitaEntityUpdated =
                receitaUpdateUseCaseImpl.execute(receitaEntity.getTransactionId(), receitaEntity);

        Assertions.assertNotNull(receitaEntityUpdated);
        Assertions.assertEquals(receitaEntity.getTransactionId(), receitaEntityUpdated.getTransactionId());
        Assertions.assertEquals(receitaEntity.getDescription(), receitaEntityUpdated.getDescription());
        Assertions.assertEquals(receitaEntity.getStatus(), receitaEntityUpdated.getStatus());
    }

    @Test
    void execute_success_null() {
        Mockito.when(receitaFindByIdUseCase.execute(Mockito.anyString()))
                .thenReturn(receitaEntity);

        Mockito.when(receitaGateway.save(Mockito.any(ReceitaEntity.class)))
                .thenReturn(receitaEntity);

        ReceitaEntity receitaEntityUpdated =
                receitaUpdateUseCaseImpl.execute(receitaEntity.getTransactionId(), new ReceitaEntity());

        Assertions.assertNotNull(receitaEntityUpdated);
        Assertions.assertEquals(receitaEntity.getTransactionId(), receitaEntityUpdated.getTransactionId());
        Assertions.assertEquals(receitaEntity.getDescription(), receitaEntityUpdated.getDescription());
        Assertions.assertEquals(receitaEntity.getStatus(), receitaEntityUpdated.getStatus());
    }

    @Test
    void execute_error() {
        Mockito.doThrow(new ResourceNotFoundException("A receita com ID "+ receitaEntity.getTransactionId() + " não foi encontrada"))
                .when(receitaFindByIdUseCase).execute(Mockito.anyString());

        try {
            receitaUpdateUseCaseImpl.execute(receitaEntity.getTransactionId(), receitaEntity);
        } catch (ResourceNotFoundException exception) {
            Assertions.assertEquals("A receita com ID "+ receitaEntity.getTransactionId() + " não foi encontrada", exception.getMessage());
        }
    }
}
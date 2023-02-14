package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.exception.ResourceNotFoundException;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
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
class ReceitaFindByIdUseCaseImplTest {

    @InjectMocks
    private ReceitaFindByIdUseCaseImpl receitaFindByIdUseCaseImpl;
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
        Mockito.when(receitaGateway.findById(Mockito.anyString()))
                .thenReturn(receitaEntity);

        ReceitaEntity receitaEntityFound = receitaFindByIdUseCaseImpl.execute(receitaEntity.getTransactionId());

        Assertions.assertNotNull(receitaEntityFound);
        Assertions.assertEquals(receitaEntity.getTransactionId(), receitaEntityFound.getTransactionId());
        Assertions.assertEquals(receitaEntity.getDescription(), receitaEntityFound.getDescription());
        Assertions.assertEquals(receitaEntity.getStatus(), receitaEntityFound.getStatus());
    }

    @Test
    void execute_error() {
        Mockito.when(receitaGateway.findById(Mockito.anyString()))
                .thenReturn(null);

        try {
            receitaFindByIdUseCaseImpl.execute(receitaEntity.getTransactionId());
        } catch (ResourceNotFoundException exception) {
            Assertions.assertEquals("A receita com ID " + receitaEntity.getTransactionId() + " não foi encontrada", exception.getMessage());
        }
    }
}
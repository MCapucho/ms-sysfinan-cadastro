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
class ReceitaDeleteByIdUseCaseImplTest {

    @InjectMocks
    private ReceitaDeleteByIdUseCaseImpl receitaDeleteByIdUseCaseImpl;
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

        Mockito.doNothing().when(receitaGateway)
                .deleteById(Mockito.anyString());

        receitaDeleteByIdUseCaseImpl.execute(receitaEntity.getTransactionId());

        Mockito.verify(receitaGateway, Mockito.times(1))
                .deleteById(Mockito.anyString());
    }

    @Test
    void execute_error() {
        Mockito.doThrow(new ResourceNotFoundException("A receita com ID "+ receitaEntity.getTransactionId() + " não foi encontrada"))
                .when(receitaFindByIdUseCase).execute(Mockito.anyString());

        try {
            receitaDeleteByIdUseCaseImpl.execute(receitaEntity.getTransactionId());
        } catch (ResourceNotFoundException exception) {
            Assertions.assertEquals("A receita com ID "+ receitaEntity.getTransactionId() + " não foi encontrada", exception.getMessage());
        }
    }
}
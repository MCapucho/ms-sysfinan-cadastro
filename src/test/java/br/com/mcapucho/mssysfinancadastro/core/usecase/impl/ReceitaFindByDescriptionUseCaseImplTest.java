package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
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
class ReceitaFindByDescriptionUseCaseImplTest {

    @InjectMocks
    private ReceitaFindByDescriptionUseCaseImpl receitaFindByDescriptionUseCaseImpl;
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
    void execute_true() {
        Mockito.when(receitaGateway.findByDescription(Mockito.anyString()))
                .thenReturn(Boolean.TRUE);

        Boolean result = receitaFindByDescriptionUseCaseImpl.execute(receitaEntity.getDescription());

        Assertions.assertTrue(result);
    }

    @Test
    void execute_false() {
        Mockito.when(receitaGateway.findByDescription(Mockito.anyString()))
                .thenReturn(Boolean.FALSE);

        Boolean result = receitaFindByDescriptionUseCaseImpl.execute(receitaEntity.getDescription());

        Assertions.assertFalse(result);
    }
}
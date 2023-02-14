package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.exception.ResourceDuplicatedException;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.ReceitaFindByDescriptionUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReceitaCreateUseCaseImplTest {

    @InjectMocks
    private ReceitaCreateUseCaseImpl receitaCreateUseCaseImpl;
    @Mock
    private ReceitaFindByDescriptionUseCase receitaFindByDescriptionUseCase;
    @Mock
    private ReceitaGateway receitaGateway;

    private ReceitaEntity receitaEntity;

    @BeforeEach
    void setUp() {
        receitaEntity = ReceitaEntity.builder()
                .description("Teste receita 01")
                .status(true)
                .build();
    }

    @Test
    void execute_success() {
        Mockito.when(receitaFindByDescriptionUseCase.execute(Mockito.anyString()))
                .thenReturn(Boolean.FALSE);

        Mockito.when(receitaGateway.save(Mockito.any(ReceitaEntity.class)))
                .thenReturn(receitaEntity);

        ReceitaEntity receitaEntityCreated = receitaCreateUseCaseImpl.execute(receitaEntity);

        Assertions.assertNotNull(receitaEntity);
        Assertions.assertEquals(receitaEntity.getDescription(), receitaEntityCreated.getDescription());
        Assertions.assertEquals(receitaEntity.getStatus(), receitaEntityCreated.getStatus());
    }

    @Test
    void execute_error() {
        Mockito.when(receitaFindByDescriptionUseCase.execute(Mockito.anyString()))
                .thenReturn(Boolean.TRUE);

        ResourceDuplicatedException exception = Assertions.assertThrows(ResourceDuplicatedException.class, () ->
            receitaCreateUseCaseImpl.execute(receitaEntity)
        );

        Assertions.assertEquals("A receita já está cadastrada", exception.getMessage());
    }
}
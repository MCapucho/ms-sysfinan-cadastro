package br.com.mcapucho.mssysfinancadastro.core.usecase.impl;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.exception.ResourceListEmptyException;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.util.FilterPageable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ReceitaListUseCaseImplTest {

    @InjectMocks
    private ReceitaListUseCaseImpl receitaListUseCaseImpl;
    @Mock
    private ReceitaGateway receitaGateway;

    private ReceitaEntity receitaEntity;
    private FilterPageable filterPageable;

    @BeforeEach
    void setUp() {
        receitaEntity = ReceitaEntity.builder()
                .transactionId(UUID.randomUUID().toString())
                .description("Teste receita 01")
                .status(true)
                .build();

        filterPageable = FilterPageable.builder()
                .direction("ASC")
                .linesPerPage(10)
                .orderBy("description")
                .page(0)
                .build();
    }

    @Test
    void execute_success_no_filter() {
        Mockito.when(receitaGateway.findAll()).thenReturn(List.of(receitaEntity));

        List<ReceitaEntity> list = receitaListUseCaseImpl.execute();

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(receitaEntity.getTransactionId(), list.get(0).getTransactionId());
        Assertions.assertEquals(receitaEntity.getDescription(), list.get(0).getDescription());
        Assertions.assertEquals(receitaEntity.getStatus(), list.get(0).getStatus());
    }

    @Test
    void execute_error_no_filter() {
        Mockito.when(receitaGateway.findAll()).thenReturn(List.of());

        ResourceListEmptyException exception = Assertions.assertThrows(ResourceListEmptyException.class, () ->
                receitaListUseCaseImpl.execute()
        );

        Assertions.assertEquals("A lista de receita está vazia", exception.getMessage());
    }

    @Test
    void execute_success_filter() {
        Mockito.when(receitaGateway.findAll(Mockito.any(FilterPageable.class)))
                .thenReturn(List.of(receitaEntity));

        List<ReceitaEntity> list = receitaListUseCaseImpl.execute(filterPageable);

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(receitaEntity.getTransactionId(), list.get(0).getTransactionId());
        Assertions.assertEquals(receitaEntity.getDescription(), list.get(0).getDescription());
        Assertions.assertEquals(receitaEntity.getStatus(), list.get(0).getStatus());
    }

    @Test
    void execute_error_filter() {
        Mockito.when(receitaGateway.findAll(Mockito.any(FilterPageable.class)))
                .thenReturn(List.of());

        ResourceListEmptyException exception = Assertions.assertThrows(ResourceListEmptyException.class, () ->
                receitaListUseCaseImpl.execute(filterPageable)
        );

        Assertions.assertEquals("A lista de receita está vazia", exception.getMessage());
    }
}
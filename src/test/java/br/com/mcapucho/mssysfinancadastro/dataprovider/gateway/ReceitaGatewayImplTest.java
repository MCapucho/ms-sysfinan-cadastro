package br.com.mcapucho.mssysfinancadastro.dataprovider.gateway;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.dataprovider.mapper.ReceitaDBMapper;
import br.com.mcapucho.mssysfinancadastro.dataprovider.model.ReceitaDB;
import br.com.mcapucho.mssysfinancadastro.dataprovider.repository.ReceitaRepository;
import br.com.mcapucho.mssysfinancadastro.util.FilterPageable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ReceitaGatewayImplTest {

    private static final String TRANSACTION_ID = UUID.randomUUID().toString();

    @InjectMocks
    private ReceitaGatewayImpl receitaGatewayImpl;
    @Mock
    private ReceitaRepository receitaRepository;
    @Mock
    private ReceitaDBMapper receitaDBMapper;

    private ReceitaEntity receitaEntity;
    private ReceitaDB receitaDB;
    private FilterPageable filterPageable;

    @BeforeEach
    void setUp() {
        receitaEntity = ReceitaEntity.builder()
                .description("Teste receita 01")
                .status(true)
                .build();

        receitaDB = ReceitaDB.builder()
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
    void save() {
        Mockito.when(receitaDBMapper.entityToDB(Mockito.any(ReceitaEntity.class)))
                        .thenReturn(receitaDB);

        Mockito.when(receitaRepository.save(Mockito.any(ReceitaDB.class)))
                .thenReturn(receitaDB);

        Mockito.when(receitaDBMapper.dbToEntity(Mockito.any(ReceitaDB.class)))
                .thenReturn(receitaEntity);

        ReceitaEntity receitaEntityCreated = receitaGatewayImpl.save(receitaEntity);

        Assertions.assertNotNull(receitaEntityCreated);
        Assertions.assertEquals(receitaEntity.getTransactionId(), receitaEntityCreated.getTransactionId());
        Assertions.assertEquals(receitaEntity.getDescription(), receitaEntityCreated.getDescription());
        Assertions.assertEquals(receitaEntity.getStatus(), receitaEntityCreated.getStatus());
    }

    @Test
    void findAll() {
        Mockito.when(receitaRepository.findAll()).thenReturn(List.of(receitaDB));

        Mockito.when(receitaDBMapper.dbToEntity(Mockito.any(ReceitaDB.class)))
                .thenReturn(receitaEntity);

        List<ReceitaEntity> receitaEntityList = receitaGatewayImpl.findAll();

        Assertions.assertEquals(1, receitaEntityList.size());
        Assertions.assertEquals(receitaEntity.getTransactionId(), receitaEntityList.get(0).getTransactionId());
        Assertions.assertEquals(receitaEntity.getDescription(), receitaEntityList.get(0).getDescription());
        Assertions.assertEquals(receitaEntity.getStatus(), receitaEntityList.get(0).getStatus());
    }

    @Test
    void findAll_filter() {
        Mockito.when(receitaRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(receitaDB)));

        Mockito.when(receitaDBMapper.dbToEntity(Mockito.any(ReceitaDB.class)))
                .thenReturn(receitaEntity);

        List<ReceitaEntity> receitaEntityList = receitaGatewayImpl.findAll(filterPageable);

        Assertions.assertEquals(1, receitaEntityList.size());
        Assertions.assertEquals(receitaEntity.getTransactionId(), receitaEntityList.get(0).getTransactionId());
        Assertions.assertEquals(receitaEntity.getDescription(), receitaEntityList.get(0).getDescription());
        Assertions.assertEquals(receitaEntity.getStatus(), receitaEntityList.get(0).getStatus());
    }

    @Test
    void findById() {
        Mockito.when(receitaRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(receitaDB));

        Mockito.when(receitaDBMapper.dbToEntity(Mockito.any(ReceitaDB.class)))
                .thenReturn(receitaEntity);

        ReceitaEntity receitaEntityFound = receitaGatewayImpl.findById(TRANSACTION_ID);

        Assertions.assertNotNull(receitaEntityFound);
        Assertions.assertEquals(receitaEntity.getTransactionId(), receitaEntityFound.getTransactionId());
        Assertions.assertEquals(receitaEntity.getDescription(), receitaEntityFound.getDescription());
        Assertions.assertEquals(receitaEntity.getStatus(), receitaEntityFound.getStatus());
    }

    @Test
    void findById_null() {
        Mockito.when(receitaRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        ReceitaEntity receitaEntityFound = receitaGatewayImpl.findById(TRANSACTION_ID);

        Assertions.assertNull(receitaEntityFound);
    }

    @Test
    void deleteById() {
        Mockito.doNothing().when(receitaRepository)
                .deleteById(Mockito.anyString());

        receitaGatewayImpl.deleteById(TRANSACTION_ID);

        Mockito.verify(receitaRepository, Mockito.times(1))
                .deleteById(TRANSACTION_ID);
    }

    @Test
    void findByDescription() {
        Mockito.when(receitaRepository.existsByDescription(Mockito.anyString()))
                .thenReturn(Boolean.TRUE);

        Boolean result = receitaGatewayImpl.findByDescription(receitaEntity.getDescription());

        Assertions.assertTrue(result);
    }
}
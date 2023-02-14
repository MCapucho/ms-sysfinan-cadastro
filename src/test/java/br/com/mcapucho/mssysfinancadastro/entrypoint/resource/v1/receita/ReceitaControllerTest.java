package br.com.mcapucho.mssysfinancadastro.entrypoint.resource.v1.receita;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.*;
import br.com.mcapucho.mssysfinancadastro.entrypoint.http.ReceitaHttpRequest;
import br.com.mcapucho.mssysfinancadastro.entrypoint.http.ReceitaHttpResponse;
import br.com.mcapucho.mssysfinancadastro.entrypoint.mapper.ReceitaHttpMapper;
import br.com.mcapucho.mssysfinancadastro.handler.CustomExceptionHandler;
import br.com.mcapucho.mssysfinancadastro.util.FilterPageable;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class ReceitaControllerTest {

    private static final String TRANSACTION_ID = UUID.randomUUID().toString();

    private MockMvc mvc;

    private JacksonTester<ReceitaHttpRequest> jsonReceitaHttpRequest;

    @InjectMocks
    private ReceitaController receitaController;
    @Mock
    private ReceitaHttpMapper receitaHttpMapper;
    @Mock
    private ReceitaCreateUseCase receitaCreateUseCase;
    @Mock
    private ReceitaListUseCase receitaListUseCase;
    @Mock
    private ReceitaFindByIdUseCase receitaFindByIdUseCase;
    @Mock
    private ReceitaDeleteByIdUseCase receitaDeleteByIdUseCase;
    @Mock
    private ReceitaUpdateUseCase receitaUpdateUseCase;

    private ReceitaHttpRequest receitaHttpRequest;
    private ReceitaHttpResponse receitaHttpResponse;
    private ReceitaEntity receitaEntity;
    @SuppressWarnings("FieldCanBeLocal")
    private FilterPageable filterPageable;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(receitaController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();

        receitaHttpRequest = ReceitaHttpRequest.builder()
                .description("Teste receita 01")
                .status(true)
                .build();

        receitaHttpResponse = ReceitaHttpResponse.builder()
                .transactionId(TRANSACTION_ID)
                .description(receitaHttpRequest.getDescription())
                .status(receitaHttpRequest.getStatus())
                .build();

        receitaEntity = ReceitaEntity.builder()
                .transactionId(TRANSACTION_ID)
                .description("Teste receita 01")
                .status(true)
                .build();

        filterPageable = FilterPageable.builder()
                .page(0)
                .orderBy("description")
                .linesPerPage(10)
                .direction("ASC")
                .build();
    }

    @SneakyThrows
    @Test
    void create() {
        Mockito.when(receitaHttpMapper.httpToEntity(Mockito.any(ReceitaHttpRequest.class)))
                .thenReturn(receitaEntity);

        Mockito.when(receitaHttpMapper.entityToHttp(Mockito.any(ReceitaEntity.class)))
                .thenReturn(receitaHttpResponse);

        Mockito.when(receitaCreateUseCase.execute(Mockito.any(ReceitaEntity.class)))
                .thenReturn(receitaEntity);

        MockHttpServletResponse response =
                mvc.perform(
                    post("/receitas/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReceitaHttpRequest.write(receitaHttpRequest).getJson())
                ).andReturn().getResponse();

        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
    }

    @SneakyThrows
    @Test
    void findAll() {
        Mockito.when(receitaListUseCase.execute())
                .thenReturn(List.of(receitaEntity));

        Mockito.when(receitaHttpMapper.entityToHttp(Mockito.any(ReceitaEntity.class)))
                .thenReturn(receitaHttpResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        get("/receitas/v1")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void findAllPageable() {
        Mockito.when(receitaListUseCase.execute(Mockito.any(FilterPageable.class)))
                .thenReturn(List.of(receitaEntity));

        Mockito.when(receitaHttpMapper.entityToHttp(Mockito.any(ReceitaEntity.class)))
                .thenReturn(receitaHttpResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        get("/receitas/v1/page")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void findById() {
        Mockito.when(receitaFindByIdUseCase.execute(Mockito.anyString()))
                .thenReturn(receitaEntity);

        Mockito.when(receitaHttpMapper.entityToHttp(Mockito.any(ReceitaEntity.class)))
                .thenReturn(receitaHttpResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        get("/receitas/v1/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void deleteById() {
        Mockito.doNothing().when(receitaDeleteByIdUseCase).execute(Mockito.anyString());

        MockHttpServletResponse response =
                mvc.perform(
                        delete("/receitas/v1/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        Assertions.assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
    }

    @SneakyThrows
    @Test
    void updateById() {
        Mockito.when(receitaHttpMapper.httpToEntity(Mockito.any(ReceitaHttpRequest.class)))
                .thenReturn(receitaEntity);

        Mockito.when(receitaHttpMapper.entityToHttp(Mockito.any(ReceitaEntity.class)))
                .thenReturn(receitaHttpResponse);

        Mockito.when(receitaUpdateUseCase.execute(Mockito.anyString(), Mockito.any(ReceitaEntity.class)))
                .thenReturn(receitaEntity);

        MockHttpServletResponse response =
                mvc.perform(
                        patch("/receitas/v1/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonReceitaHttpRequest.write(receitaHttpRequest).getJson())
                ).andReturn().getResponse();

        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }
}
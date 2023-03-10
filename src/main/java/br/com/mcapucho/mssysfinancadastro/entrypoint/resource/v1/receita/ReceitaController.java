package br.com.mcapucho.mssysfinancadastro.entrypoint.resource.v1.receita;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.*;
import br.com.mcapucho.mssysfinancadastro.entrypoint.http.ReceitaHttpRequest;
import br.com.mcapucho.mssysfinancadastro.entrypoint.http.ReceitaHttpResponse;
import br.com.mcapucho.mssysfinancadastro.entrypoint.mapper.ReceitaHttpMapper;
import br.com.mcapucho.mssysfinancadastro.util.FilterPageable;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping(value = "/receitas/v1")
public class ReceitaController {

    private final ReceitaHttpMapper receitaHttpMapper;
    private final ReceitaCreateUseCase receitaCreateUseCase;
    private final ReceitaListUseCase receitaListUseCase;
    private final ReceitaFindByIdUseCase receitaFindByIdUseCase;
    private final ReceitaDeleteByIdUseCase receitaDeleteByIdUseCase;
    private final ReceitaUpdateUseCase receitaUpdateUseCase;

    public ReceitaController(ReceitaHttpMapper receitaHttpMapper, ReceitaCreateUseCase receitaCreateUseCase,
                             ReceitaListUseCase receitaListUseCase, ReceitaFindByIdUseCase receitaFindByIdUseCase,
                             ReceitaDeleteByIdUseCase receitaDeleteByIdUseCase, ReceitaUpdateUseCase receitaUpdateUseCase) {
        this.receitaHttpMapper = receitaHttpMapper;
        this.receitaCreateUseCase = receitaCreateUseCase;
        this.receitaListUseCase = receitaListUseCase;
        this.receitaFindByIdUseCase = receitaFindByIdUseCase;
        this.receitaDeleteByIdUseCase = receitaDeleteByIdUseCase;
        this.receitaUpdateUseCase = receitaUpdateUseCase;
    }

    @Operation(summary = "Endpoint para criar uma nova receita")
    @PostMapping()
    public ResponseEntity<ReceitaHttpResponse> create(@Valid @RequestBody ReceitaHttpRequest request) {
        ReceitaEntity receitaEntity = receitaCreateUseCase.execute(receitaHttpMapper.httpToEntity(request));
        ReceitaHttpResponse receitaHttpResponse = receitaHttpMapper.entityToHttp(receitaEntity);
        receitaHttpResponse.add(linkTo(methodOn(ReceitaController.class)
                .findById(receitaHttpResponse.getTransactionId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(receitaHttpMapper.entityToHttp(receitaEntity));
    }

    @Operation(summary = "Endpoint para listar todas as receitas")
    @GetMapping()
    public ResponseEntity<List<ReceitaHttpResponse>> findAll() {
        List<ReceitaEntity> receitaEntityList = receitaListUseCase.execute();
        List<ReceitaHttpResponse> receitaHttpResponseList = new ArrayList<>();

        receitaEntityList.forEach(result -> {
            ReceitaHttpResponse receitaHttpResponse = receitaHttpMapper.entityToHttp(result);
            receitaHttpResponse.add(linkTo(methodOn(ReceitaController.class)
                    .findById(receitaHttpResponse.getTransactionId())).withSelfRel());
            receitaHttpResponseList.add(receitaHttpResponse);
        });

        return ResponseEntity.status(HttpStatus.OK).body(receitaHttpResponseList);
    }

    @Operation(summary = "Endpoint para listar todas as receitas com pagina????o")
    @GetMapping("/page")
    public ResponseEntity<List<ReceitaHttpResponse>> findAllPageable(FilterPageable filterPageable) {
        List<ReceitaEntity> receitaEntityList = receitaListUseCase.execute(filterPageable);
        List<ReceitaHttpResponse> receitaHttpResponseList = new ArrayList<>();

        receitaEntityList.forEach(result -> {
            ReceitaHttpResponse receitaHttpResponse = receitaHttpMapper.entityToHttp(result);
            receitaHttpResponse.add(linkTo(methodOn(ReceitaController.class)
                    .findById(receitaHttpResponse.getTransactionId())).withSelfRel());
            receitaHttpResponseList.add(receitaHttpResponse);
        });

        return ResponseEntity.status(HttpStatus.OK).body(receitaHttpResponseList);
    }

    @Operation(summary = "Endpoint para listar uma receita atrav??s do ID informado")
    @GetMapping("/{id}")
    public ResponseEntity<ReceitaHttpResponse> findById(@PathVariable("id") String transactionId) {
        ReceitaEntity receitaEntity = receitaFindByIdUseCase.execute(transactionId);
        ReceitaHttpResponse receitaHttpResponse = receitaHttpMapper.entityToHttp(receitaEntity);
        receitaHttpResponse.add(linkTo(methodOn(ReceitaController.class).findAll()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(receitaHttpResponse);
    }

    @Operation(summary = "Endpoint para deletar uma receita atrav??s do ID informado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") String transactionId) {
        receitaDeleteByIdUseCase.execute(transactionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Endpoint para atualizar uma receita atrav??s do ID informado")
    @PatchMapping("/{id}")
    public ResponseEntity<ReceitaHttpResponse> updateById(@PathVariable("id") String transactionId,
                                                          @RequestBody ReceitaHttpRequest receitaHttpRequest) {
        ReceitaEntity receitaEntity = receitaUpdateUseCase.execute(
                transactionId, receitaHttpMapper.httpToEntity(receitaHttpRequest));
        return ResponseEntity.status(HttpStatus.OK).body(receitaHttpMapper.entityToHttp(receitaEntity));
    }
}

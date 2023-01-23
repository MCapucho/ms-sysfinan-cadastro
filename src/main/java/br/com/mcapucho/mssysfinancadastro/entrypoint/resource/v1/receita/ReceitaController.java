package br.com.mcapucho.mssysfinancadastro.entrypoint.resource.v1.receita;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces.*;
import br.com.mcapucho.mssysfinancadastro.entrypoint.http.ReceitaHttpRequest;
import br.com.mcapucho.mssysfinancadastro.entrypoint.http.ReceitaHttpResponse;
import br.com.mcapucho.mssysfinancadastro.entrypoint.mapper.ReceitaHttpMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @PostMapping()
    public ResponseEntity<ReceitaHttpResponse> cadastrar(@Valid @RequestBody ReceitaHttpRequest request) {
        ReceitaEntity receitaEntity = receitaCreateUseCase.execute(receitaHttpMapper.httpToEntity(request));
        ReceitaHttpResponse receitaHttpResponse = receitaHttpMapper.entityToHttp(receitaEntity);
        receitaHttpResponse.add(linkTo(methodOn(ReceitaController.class)
                .listarPorId(receitaHttpResponse.getTransactionId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(receitaHttpMapper.entityToHttp(receitaEntity));
    }

    @GetMapping()
    public ResponseEntity<List<ReceitaHttpResponse>> listar() {
        List<ReceitaEntity> receitaEntityList = receitaListUseCase.execute();
        List<ReceitaHttpResponse> receitaHttpResponseList = new ArrayList<>();

        receitaEntityList.forEach(result -> {
            ReceitaHttpResponse receitaHttpResponse = receitaHttpMapper.entityToHttp(result);
            receitaHttpResponse.add(linkTo(methodOn(ReceitaController.class)
                    .listarPorId(receitaHttpResponse.getTransactionId())).withSelfRel());
            receitaHttpResponseList.add(receitaHttpResponse);
        });

        return ResponseEntity.status(HttpStatus.OK).body(receitaHttpResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaHttpResponse> listarPorId(@PathVariable("id") String transactionId) {
        ReceitaEntity receitaEntity = receitaFindByIdUseCase.execute(transactionId);
        ReceitaHttpResponse receitaHttpResponse = receitaHttpMapper.entityToHttp(receitaEntity);
        receitaHttpResponse.add(linkTo(methodOn(ReceitaController.class).listar()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(receitaHttpResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPorId(@PathVariable("id") String transactionId) {
        receitaDeleteByIdUseCase.execute(transactionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReceitaHttpResponse> atualizarPorId(@PathVariable("id") String transactionId,
                                                              @RequestBody ReceitaHttpRequest receitaHttpRequest) {
        ReceitaEntity receitaEntity = receitaUpdateUseCase.execute(
                transactionId, receitaHttpMapper.httpToEntity(receitaHttpRequest));
        return ResponseEntity.status(HttpStatus.OK).body(receitaHttpMapper.entityToHttp(receitaEntity));
    }
}

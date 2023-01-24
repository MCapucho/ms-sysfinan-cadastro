package br.com.mcapucho.mssysfinancadastro.core.usecase.interfaces;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.util.FilterPageable;

import java.util.List;

public interface ReceitaListUseCase {

    List<ReceitaEntity> execute();
    List<ReceitaEntity> execute(FilterPageable filterPageable);
}

package br.com.mcapucho.mssysfinancadastro.dataprovider.gateway;

import br.com.mcapucho.mssysfinancadastro.core.entity.ReceitaEntity;
import br.com.mcapucho.mssysfinancadastro.core.gateway.ReceitaGateway;
import br.com.mcapucho.mssysfinancadastro.dataprovider.mapper.ReceitaDBMapper;
import br.com.mcapucho.mssysfinancadastro.dataprovider.model.ReceitaDB;
import br.com.mcapucho.mssysfinancadastro.dataprovider.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceitaGatewayImpl implements ReceitaGateway {

    private final ReceitaDBMapper receitaDBMapper;
    private final ReceitaRepository receitaRepository;

    public ReceitaGatewayImpl(ReceitaDBMapper receitaDBMapper, ReceitaRepository receitaRepository) {
        this.receitaDBMapper = receitaDBMapper;
        this.receitaRepository = receitaRepository;
    }

    @Override
    public ReceitaEntity save(ReceitaEntity receitaEntity) {
        ReceitaDB receitaDB = receitaRepository.save(receitaDBMapper.entityToDB(receitaEntity));
        return receitaDBMapper.dbToEntity(receitaDB);
    }

    @Override
    public List<ReceitaEntity> findAll() {
        List<ReceitaDB> receitaDBList = receitaRepository.findAll();
        List<ReceitaEntity> receitaEntityList = new ArrayList<>();

        receitaDBList.forEach(result -> {
            ReceitaEntity receitaEntity = receitaDBMapper.dbToEntity(result);
            receitaEntityList.add(receitaEntity);
        });

        return receitaEntityList;
    }

    @Override
    public ReceitaEntity findById(String transactionId) {
        return receitaRepository.findById(transactionId)
                .stream()
                .map(receitaDBMapper::dbToEntity)
                .findAny()
                .orElseThrow();
    }

    @Override
    public void deleteById(String transactionId) {
        receitaRepository.deleteById(transactionId);
    }
}

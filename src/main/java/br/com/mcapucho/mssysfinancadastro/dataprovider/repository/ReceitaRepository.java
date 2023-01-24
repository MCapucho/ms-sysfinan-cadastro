package br.com.mcapucho.mssysfinancadastro.dataprovider.repository;

import br.com.mcapucho.mssysfinancadastro.dataprovider.model.ReceitaDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends JpaRepository<ReceitaDB, String> {

    Boolean existsByDescription(String description);
}

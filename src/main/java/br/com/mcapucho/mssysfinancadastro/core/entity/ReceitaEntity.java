package br.com.mcapucho.mssysfinancadastro.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaEntity {

    public String transactionId;
    public String description;
    public Boolean status;
}

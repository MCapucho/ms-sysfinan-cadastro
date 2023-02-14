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

    private String transactionId;
    private String description;
    private Boolean status;
}

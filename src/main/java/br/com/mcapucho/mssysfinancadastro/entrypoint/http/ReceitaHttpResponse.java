package br.com.mcapucho.mssysfinancadastro.entrypoint.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaHttpResponse {

    public String transactionId;
    public String description;
    public Boolean status;
}

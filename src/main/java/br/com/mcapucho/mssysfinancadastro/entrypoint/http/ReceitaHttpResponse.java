package br.com.mcapucho.mssysfinancadastro.entrypoint.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaHttpResponse extends RepresentationModel<ReceitaHttpResponse> {

    private String transactionId;
    private String description;
    private Boolean status;
}

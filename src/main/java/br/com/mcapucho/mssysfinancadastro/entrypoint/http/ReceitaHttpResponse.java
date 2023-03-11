package br.com.mcapucho.mssysfinancadastro.entrypoint.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceitaHttpResponse extends RepresentationModel<ReceitaHttpResponse> {

    private String transactionId;
    private String description;
    private Boolean status;
}

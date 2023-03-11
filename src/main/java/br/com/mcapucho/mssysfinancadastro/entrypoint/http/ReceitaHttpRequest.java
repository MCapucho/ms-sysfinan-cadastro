package br.com.mcapucho.mssysfinancadastro.entrypoint.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceitaHttpRequest {

    @NotBlank(message = "O campo descrição não pode ser vazio ou nulo")
    private String description;
    private Boolean status;
}

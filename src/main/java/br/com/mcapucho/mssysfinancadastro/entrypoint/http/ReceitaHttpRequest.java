package br.com.mcapucho.mssysfinancadastro.entrypoint.http;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaHttpRequest {

    @NotBlank(message = "O campo descrição não pode ser vazio ou nulo")
    public String description;
    public Boolean status;
}

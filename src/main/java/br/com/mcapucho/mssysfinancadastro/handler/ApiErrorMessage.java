package br.com.mcapucho.mssysfinancadastro.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorMessage {

    private String dateTime;
    private HttpStatus status;
    private Integer code;
    private List<String> errors;
}

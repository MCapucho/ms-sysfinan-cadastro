package br.com.mcapucho.mssysfinancadastro.handler;

import br.com.mcapucho.mssysfinancadastro.core.exception.ResourceDuplicatedException;
import br.com.mcapucho.mssysfinancadastro.core.exception.ResourceListEmptyException;
import br.com.mcapucho.mssysfinancadastro.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @ExceptionHandler(ResourceListEmptyException.class)
    public ResponseEntity<ApiErrorMessage> handleListEmpty(ResourceListEmptyException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiErrorMessage apiErrorMessage = ApiErrorMessage.builder()
                .dateTime(LocalDateTime.now().format(formatter))
                .status(HttpStatus.NO_CONTENT)
                .code(HttpStatus.NO_CONTENT.value())
                .errors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiErrorMessage);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> handleNotFound(ResourceNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiErrorMessage apiErrorMessage = ApiErrorMessage.builder()
                .dateTime(LocalDateTime.now().format(formatter))
                .status(HttpStatus.NOT_FOUND)
                .code(HttpStatus.NOT_FOUND.value())
                .errors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiErrorMessage);
    }

    @ExceptionHandler(ResourceDuplicatedException.class)
    public ResponseEntity<ApiErrorMessage> handleDuplicated(ResourceDuplicatedException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiErrorMessage apiErrorMessage = ApiErrorMessage.builder()
                .dateTime(LocalDateTime.now().format(formatter))
                .status(HttpStatus.CONFLICT)
                .code(HttpStatus.CONFLICT.value())
                .errors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiErrorMessage);
    }
}

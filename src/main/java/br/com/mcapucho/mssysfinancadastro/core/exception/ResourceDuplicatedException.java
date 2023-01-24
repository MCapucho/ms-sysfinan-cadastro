package br.com.mcapucho.mssysfinancadastro.core.exception;

public class ResourceDuplicatedException extends RuntimeException {

    public ResourceDuplicatedException(String message) {
        super(message);
    }
}

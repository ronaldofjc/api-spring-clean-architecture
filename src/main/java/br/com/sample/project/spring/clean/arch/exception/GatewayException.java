package br.com.sample.project.spring.clean.arch.exception;

public class GatewayException extends RuntimeException {

    public GatewayException(final String message) {
        super(message);
    }
}

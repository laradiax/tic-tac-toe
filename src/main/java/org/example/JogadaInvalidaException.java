package org.example;

public class JogadaInvalidaException extends RuntimeException {
    private final TipoErro tipo;

    public JogadaInvalidaException(TipoErro tipo) {
        super(tipo.name());
        this.tipo = tipo;
    }

    public TipoErro getTipo() {
        return tipo;
    }
}
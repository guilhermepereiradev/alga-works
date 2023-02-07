package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradoException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradoException(Long id) {
        this(String.format("Não existe cozinha cadastrado para o código %d", id));
    }
}

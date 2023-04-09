package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String codigo) {
        super(String.format("Pedido não encontrado para o código %s", codigo));
    }
}

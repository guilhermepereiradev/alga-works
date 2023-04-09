package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Transactional
    public void confirmar(String codigo){
        Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigo);
        pedido.confirmar();
    }

    @Transactional
    public void entregar(String codigo){
        Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigo);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigo){
        Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigo);
        pedido.cancelar();
    }
}

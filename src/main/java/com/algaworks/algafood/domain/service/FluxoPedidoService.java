package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class FluxoPedidoService {

    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Transactional
    public void confirmar(Long id){
        Pedido pedido = cadastroPedidoService.buscarOuFalhar(id);
        pedido.confirmar();
    }

    @Transactional
    public void entregar(Long id){
        Pedido pedido = cadastroPedidoService.buscarOuFalhar(id);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(Long id){
        Pedido pedido = cadastroPedidoService.buscarOuFalhar(id);
        pedido.cancelar();
    }
}

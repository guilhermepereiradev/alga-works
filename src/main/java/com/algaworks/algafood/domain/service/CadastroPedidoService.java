package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroPedidoService {

    @Autowired
    private PedidoRepository repository;

    public List<Pedido> listar(){
        return repository.findAll();
    }

    public Pedido buscarOuFalhar(Long id){
        return repository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    public Pedido salvar(Pedido pedido) {
       return repository.save(pedido);
    }
}

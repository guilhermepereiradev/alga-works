package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {

    @Autowired
    private CadastroPedidoService pedidoService;

    @Autowired
    private CadastroRestauranteService restauranteService;

    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;

    @Autowired
    private CadastroProdutoService produtoService;

    @Autowired
    private CadastroCidadeService cidadeService;

    @Transactional

    public Pedido emitirPedido(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoService.salvar(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        Long restauranteId = pedido.getRestaurante().getId();
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        if(!restaurante.aceitaFormaPagamento(formaPagamento)){
            throw new NegocioException(String.format("Restaurante de código %d não aceita forma de pagamento: %s",
                    restauranteId, formaPagamento.getDescricao()));
        }

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
    }

    public void validarItens(Pedido pedido){
        pedido.getItens().forEach( item -> {
            Produto produto = produtoService.buscarOuFalhar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPrecoUnitario(produto.getPreco());
            item.setProduto(produto);
            item.setPedido(pedido);
        });
    }
}

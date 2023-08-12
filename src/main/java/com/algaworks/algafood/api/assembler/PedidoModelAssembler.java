package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

        modelMapper.map(pedido, pedidoModel);

        if (pedido.podeSerConfirmado()) {
            pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
        }

        if (pedido.podeSerCancelado()) {
            pedidoModel.add(algaLinks.linkToCacelarPedido(pedido.getCodigo(), "cancelar"));
        }

        if (pedido.podeSerEntregue()) {
            pedidoModel.add(algaLinks.linkToEntregarPedido(pedido.getCodigo(), "entregar"));
        }

        pedidoModel.add(algaLinks.linkToPedidos());

        pedidoModel.getRestaurante().add(algaLinks.linkToRestaurante(
                pedidoModel.getRestaurante().getId()));

        pedidoModel.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));

        pedidoModel.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(
                pedidoModel.getEnderecoEntrega().getCidade().getId()));

        pedidoModel.getFormaPagamento().add(algaLinks.linkToFomaPagamento(
                pedidoModel.getFormaPagamento().getId()));

        pedidoModel.getItens().forEach(item ->
            item.add(algaLinks.linkToProduto(pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"))
        );

        return pedidoModel;
    }
}

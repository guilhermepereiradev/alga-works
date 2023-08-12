package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);

        modelMapper.map(pedido, pedidoResumoModel);

        pedidoResumoModel.add(algaLinks.linkToPedidos("pedidos"));

        pedidoResumoModel.getRestaurante().add(algaLinks.linkToRestaurante(
                pedidoResumoModel.getRestaurante().getId()));

        pedidoResumoModel.getCliente().add(algaLinks.linkToUsuario(pedidoResumoModel.getCliente().getId(), "cliente"));
        
        return pedidoResumoModel;
    }

}

package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;


    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = new ProdutoModel();

        modelMapper.map(produto, produtoModel);

        produtoModel.add(algaLinks.linkToRestauranteProduto(produto.getRestaurante().getId(), produto.getId()));

        produtoModel.add(algaLinks.linkToRestauranteProdutos(produto.getRestaurante().getId(), "produtos"));

        produtoModel.add(algaLinks.linkToRestauranteProdutoFoto(produto.getRestaurante().getId(), produto.getId(), "foto"));

        return produtoModel;
    }

    public CollectionModel<ProdutoModel> toCollectionModel(Iterable<? extends Produto> entities, Long restauranteId) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestauranteProdutos(restauranteId));
    }
}

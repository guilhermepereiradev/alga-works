package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

        restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restauranteModel.getCozinha().getId()));

        restauranteModel.getEndereco().getCidade().add(
                algaLinks.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));

        if (restaurante.podeAbrir()) {
            restauranteModel.add(algaLinks.linkToAbrirRestaurante(restauranteModel.getId(), "abrir"));
        }

        if(restaurante.podeFechar()) {
            restauranteModel.add(algaLinks.linkToFecharRestaurante(restauranteModel.getId(), "fechar"));
        }

        if(restaurante.podeAtivar()) {
            restauranteModel.add(algaLinks.linkToAtivarRestaurante(restauranteModel.getId(), "ativar"));
        }

        if(restaurante.podeInativar()) {
            restauranteModel.add(algaLinks.linkToInativarRestaurante(restauranteModel.getId(), "inativar"));
        }

        restauranteModel.add(algaLinks.linkToRestaurantesFormasPagamento(
                restauranteModel.getId(), "formas-pagamento"));

        restauranteModel.add(algaLinks.linkToRestauranteResponsaveis(restauranteModel.getId(), "responsaveis"));

        return restauranteModel;
    }
}

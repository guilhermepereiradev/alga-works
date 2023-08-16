package com.algaworks.algafood.api.v1.model;

import com.algaworks.algafood.api.v1.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

    @Schema(example = "1")
    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @Schema(example = "Cozinha Indiana")
    @JsonView(RestauranteView.Resumo.class)
    private String nome;
}

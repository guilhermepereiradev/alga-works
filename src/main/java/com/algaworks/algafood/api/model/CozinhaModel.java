package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaModel {

    @Schema(example = "1")
    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @Schema(example = "Cozinha Indiana")
    @JsonView(RestauranteView.Resumo.class)
    private String nome;
}

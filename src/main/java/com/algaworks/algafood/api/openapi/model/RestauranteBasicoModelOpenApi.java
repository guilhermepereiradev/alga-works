package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.CozinhaModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Schema(name = "RestauranteBasicoModel")
@Getter
@Setter
public class RestauranteBasicoModelOpenApi {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Thai Gourmet")
    private String nome;

    @Schema(example = "12.00")
    private BigDecimal taxaFrete;

    private CozinhaModel cozinha;
}

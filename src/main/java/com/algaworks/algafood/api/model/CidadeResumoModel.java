package com.algaworks.algafood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Uberlândia")
    private String nome;
    @Schema(example = "Minas Gerais")
    private String estado;
}

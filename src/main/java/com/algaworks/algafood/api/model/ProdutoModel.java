package com.algaworks.algafood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoModel {


    @Schema(example = "1")
    private Long id;
    @Schema(example = "Porco com molho agridoce")
    private String nome;
    @Schema(example = "Deliciosa carne suína ao molho especial")
    private String descricao;
    @Schema(example = "78.9")
    private BigDecimal preco;
    @Schema(example = "false")
    private Boolean ativo;
}

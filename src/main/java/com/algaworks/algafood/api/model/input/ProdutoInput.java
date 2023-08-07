package com.algaworks.algafood.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {

    @Schema(example = "Porco com molho agridoce")
    @NotBlank
    private String nome;

    @Schema(example = "Deliciosa carne suína ao molho especial")
    @NotBlank
    private String descricao;

    @Schema(example = "78.9")
    @PositiveOrZero
    @NotNull
    private BigDecimal preco;

    @Schema(example = "false")
    @NotNull
    private Boolean ativo;
}

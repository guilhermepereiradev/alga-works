package com.algaworks.algafood.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

    @Schema(example = "3")
    @NotNull
    private Long produtoId;

    @Schema(example = "2")
    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    @Schema(example = "Observação")
    private String observacao;
}

package com.algaworks.algafood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoModel {

    @Schema(example = "3")
    private Long produtoId;
    @Schema(example = "Salada picante com carne grelhada")
    private String produtoNome;
    @Schema(example = "2")
    private Integer quantidade;
    @Schema(example = "87.20")
    private BigDecimal precoUnitario;
    @Schema(example = "174.40")
    private BigDecimal precoTotal;
    @Schema(example = "Observação")
    private String observacao;
}

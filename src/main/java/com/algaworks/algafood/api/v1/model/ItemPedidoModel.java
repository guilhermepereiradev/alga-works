package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "itens-pedido")
@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

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

package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {


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

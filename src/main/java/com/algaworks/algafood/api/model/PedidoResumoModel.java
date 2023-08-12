package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//@JsonFilter("pedidoFilter")
@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {

    @Schema(example = "5c621c9a-ba61-4454-8631-8aabefe58dc2")
    private String codigo;
    @Schema(example = "174.40")
    private BigDecimal subtotal;
    @Schema(example = "5.00")
    private BigDecimal taxaFrete;
    @Schema(example = "179.40")
    private BigDecimal valorTotal;
    @Schema(example = "ENTREGUE")
    private StatusPedido status;
    @Schema(example = "2023-11-02T20:34:04Z")
    private OffsetDateTime dataCriacao;
    private RestauranteApenasNomeModel restaurante;
    private UsuarioModel cliente;
}

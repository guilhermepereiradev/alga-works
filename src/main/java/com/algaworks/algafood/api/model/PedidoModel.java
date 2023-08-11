package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel> {

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
    @Schema(example = "2023-11-02T20:35:10Z")
    private OffsetDateTime dataConfirmacao;
    @Schema(example = "2023-11-02T20:35:10Z")
    private OffsetDateTime dataCancelamento;
    @Schema(example = "2023-11-02T21:10:32Z")
    private OffsetDateTime dataEntrega;
    private RestauranteResumoModel restaurante;
    private UsuarioModel cliente;
    private EnderecoModel enderecoEntrega;
    private FormaPagamentoModel formaPagamento;
    private List<ItemPedidoModel> itens = new ArrayList<>();
}

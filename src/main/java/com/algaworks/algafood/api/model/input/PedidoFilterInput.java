package com.algaworks.algafood.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PedidoFilterInput {

    @Schema(example = "1")
    private Long clienteId;

    @Schema(example = "1")
    private Long restauranteId;

    @Schema(example = "2023-08-06T20:01:51Z")
    private OffsetDateTime dataCriacaoInicio;

    @Schema(example = "2023-08-06T20:01:51Z")
    private OffsetDateTime dataCriacaoFim;

}

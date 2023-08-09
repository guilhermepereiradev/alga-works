package com.algaworks.algafood.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class VendaDiaria {

    @Schema(example = "2023-08-08")
    private Date data;
    @Schema(example = "1")
    private Long totalVendas;
    @Schema(example = "120")
    private BigDecimal totalFaturado;
}

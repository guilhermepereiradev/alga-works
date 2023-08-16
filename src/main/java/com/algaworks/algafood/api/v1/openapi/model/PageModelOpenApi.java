package com.algaworks.algafood.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(name = "Page")
@Getter
@Setter
public class PageModelOpenApi<T> {

    @Schema(name = "_embedded")
    private List<T> embedded;

    @Schema(name = "_links")
    private LinksModelOpenApi links;

    @Schema(example = "50", description = "Quantidade de registros por página")
    private int size;

    @Schema(example = "50", description = "Total de registros")
    private int totalElements;

    @Schema(example = "5", description = "Total de páginas")
    private int totalPages;

    @Schema(example = "0", description = "Número da página (começa em 0)")
    private int number;
}

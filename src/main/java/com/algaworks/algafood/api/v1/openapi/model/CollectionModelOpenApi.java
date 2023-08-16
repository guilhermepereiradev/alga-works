package com.algaworks.algafood.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CollectionModelOpenApi<T> {

    @Schema(name = "_embedded")
    private List<T> embedded;

    @Schema(name = "_links")
    private LinksModelOpenApi links;
}

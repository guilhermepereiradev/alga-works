package com.algaworks.algafood.api.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "_links")
public class LinksModelOpenApi {

    private LinkModel rel;

    @Getter
    @Setter
    @Schema(name = "_link")
    private class LinkModel {

        private String href;

        private Boolean templated;
    }
}

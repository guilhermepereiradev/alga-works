package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.api.openapi.model.PermissoesCollectionModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Permissões")
public interface PermissaoControllerOpenApi {


    @Operation(
            summary = "Lista das permissões",
            responses =
            @ApiResponse(
                    responseCode = "200",
                    description = "Permissões encontrado",
                    content = @Content(schema = @Schema(implementation = PermissoesCollectionModelOpenApi.class))
            )
    )
    ResponseEntity<CollectionModel<PermissaoModel>> listar();
}

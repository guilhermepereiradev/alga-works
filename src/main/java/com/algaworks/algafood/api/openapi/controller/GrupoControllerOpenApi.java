package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.api.openapi.model.GruposCollectionModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Grupos")
public interface GrupoControllerOpenApi {

    @Operation(
            summary = "Lista dos grupos",
            responses =
                    @ApiResponse(
                            responseCode = "200",
                            description = "Grupo encontrado",
                            content = @Content(schema = @Schema(implementation = GruposCollectionModelOpenApi.class))
                    )
    )
    ResponseEntity<CollectionModel<GrupoModel>> listar();

    @Operation(
            summary = "Buscar um grupo por ID",
            parameters = @Parameter(name = "id", description = "ID de um grupo", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Grupo encontrado",
                            content = @Content(schema = @Schema(implementation = GrupoModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do grupo inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Grupo não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<GrupoModel> buscar(Long id);

    @Operation(summary = "Cadastra um grupo",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Grupo criado",
                            content = @Content(schema = @Schema(implementation = GrupoModel.class))
                    )
            }
    )
    ResponseEntity<GrupoModel> salvar(GrupoInput grupoInput);

    @Operation(
            summary = "Atualiza um grupo por ID",
            parameters = @Parameter(name = "id", description = "ID de um grupo", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Grupo atualizado",
                            content = @Content(schema = @Schema(implementation = GrupoModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Grupo não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<GrupoModel> atualizar(Long id, GrupoInput grupoInput);

    @Operation(
            summary = "Exclui um grupo por ID",
            parameters = @Parameter(name = "id", description = "ID de um grupo", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Grupo excluído com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Grupo não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> deletar(Long id);
}

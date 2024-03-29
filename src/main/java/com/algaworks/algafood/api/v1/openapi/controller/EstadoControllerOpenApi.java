package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.openapi.model.EstadosCollectionModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Estados", description = "Gerencia os estados")
public interface EstadoControllerOpenApi {

    @Operation(
            summary = "Lista dos estados",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Estados encontrado",
                    content = @Content(schema = @Schema(implementation = EstadosCollectionModelOpenApi.class))
            )
    )
    ResponseEntity<CollectionModel<EstadoModel>> listar();

    @Operation(
            summary = "Buscar um estado por ID",
            parameters = @Parameter(name = "id", description = "ID de um estado", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estado encontrado",
                            content = @Content(schema = @Schema(implementation = EstadoModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do estado inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Estado não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<EstadoModel> buscar(Long id);

    @Operation(
            summary = "Cadastra um estado",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Estado criado",
                            content = @Content(schema = @Schema(implementation = EstadoModel.class))
                    )
            }
    )
    ResponseEntity<EstadoModel> salvar(EstadoInput estadoInput);

    @Operation(
            summary = "Atualiza um estado por ID",
            parameters = @Parameter(name = "id", description = "ID de um estado", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estado atualizado",
                            content = @Content(schema = @Schema(implementation = EstadoModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Estado não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<EstadoModel> atualizar(EstadoInput estadoInput, Long id);

    @Operation(
            summary = "Exclui um estado por ID",
            parameters = @Parameter(name = "id", description = "ID de um estado", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Estado excluído com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Estado não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> remover(Long id);
}

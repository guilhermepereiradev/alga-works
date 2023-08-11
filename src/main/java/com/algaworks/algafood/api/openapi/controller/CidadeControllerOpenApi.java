package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Cidades")
public interface CidadeControllerOpenApi {
    @Operation(summary = "Lista as cidades")
    ResponseEntity<CollectionModel<CidadeModel>> listar();

    @Operation(
            summary = "Busca uma cidade por ID",
            parameters = @Parameter(name = "id", description = "ID de uma cidade", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cidade encontrada",
                            content = @Content(schema = @Schema(implementation = CidadeModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID da cidade inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cidade não encontrada",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<CidadeModel> buscar(Long id);

    @Operation(
            summary = "Cadastra uma cidade",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cidade criada",
                            content = @Content(schema = @Schema(implementation = CidadeModel.class))
                    )
            }
    )
    ResponseEntity<CidadeModel> salvar(CidadeInput cidadeInput);

    @Operation(
            summary = "Atualiza uma cidade por ID",
            parameters = @Parameter(name = "id", description = "ID de uma cidade", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cidade atualizada",
                            content = @Content(schema = @Schema(implementation = CidadeModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cidade não encontrada",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<CidadeModel> atualizar(CidadeInput cidadeInput, Long id);

    @Operation(
            summary = "Exclui uma cidade por ID",
            parameters = @Parameter(name = "id", description = "ID de uma cidade", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Cidade excluída com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cidade não encontrada",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> remover(Long id);
}
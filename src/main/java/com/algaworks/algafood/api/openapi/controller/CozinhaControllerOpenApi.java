package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @Operation(
            summary = "Lista as cozinhas com paginação",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cozinhas encontradas",
                            content = @Content(schema = @Schema(implementation = CozinhasModelOpenApi.class))),
            }
    )
    ResponseEntity<Page<CozinhaModel>> listar(@ParameterObject Pageable pageable);


    @Operation(
            summary = "Buscar uma cozinha por ID",
            parameters = @Parameter(name = "id", description = "ID de uma cozinha", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cozinha encontrada",
                            content = @Content(schema = @Schema(implementation = CozinhaModel.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID da cozinha inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cozinha não encontrada",
                            content = @Content(schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<CozinhaModel> buscar(Long id);

    @Operation(summary = "Cadastra uma cozinha",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cozinha criada",
                            content = @Content(schema = @Schema(implementation = Cozinha.class)))
            }
    )
    ResponseEntity<CozinhaModel> salvar(CozinhaInput cozinhaInput);

    @Operation(
            summary = "Atualiza uma cozinha por ID",
            parameters = @Parameter(name = "id", description = "ID de uma cozinha", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cozinha atualizada",
                            content = @Content(schema = @Schema(implementation = CozinhaModel.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cozinha não encontrada",
                            content = @Content(schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<CozinhaModel> atualizar(Long id, CozinhaInput cozinhaInput);

    @Operation(
            summary = "Exclui uma cozinha por ID",
            parameters = @Parameter(name = "id", description = "ID de uma cozinha", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Cozinha excluída com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cozinha não encontrada",
                            content = @Content(schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<Void> deletar(Long id);
}

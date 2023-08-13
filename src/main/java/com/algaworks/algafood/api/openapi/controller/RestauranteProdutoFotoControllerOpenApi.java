package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {


    @Operation(
            summary = "Atualiza foto do produto de um restaurante",
            parameters = {
                    @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
                    @Parameter(name = "produtoId", description = "ID de um restaurante", example = "1"),
                    @Parameter(name = "descricao", description = "Descrição da foto do produto", example = "descricao"),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Foto cadastrada"
                    )
            }
    )
    ResponseEntity<FotoProdutoModel> atualizar(Long restauranteId, Long produtoId,
                                               @Parameter(hidden = true) FotoProdutoInput fotoProdutoInput,
                                               @Parameter(
                                                       name = "arquivo",
                                                       description = "Arquivo da foto do produto (máximo 5MB, apenas JPG ou PNG)",
                                                       schema = @Schema(type = "file")
                                               )
                                               MultipartFile arquivo) throws IOException;


    @Operation(
            summary = "Buscar a foto do produto de um restaurante",
            parameters = {
                    @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
                    @Parameter(name = "produtoId", description = "ID de um restaurante", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json"),
                                    @Content(mediaType = "image/jpeg"),
                                    @Content(mediaType = "image/png")
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Foto do produto não encontrada",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "Recurso não possui formato de representação válida",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<FotoProdutoModel> buscar(Long restauranteId, Long produtoId) throws IOException;

    @Operation(summary = "Buscar a foto do produto de um restaurante", hidden = true)
    ResponseEntity<?> servir(Long restauranteId, Long produtoId, @RequestHeader(name = "accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

    @Operation(
            summary = "Deleta a foto do produto de um restaurante",
            parameters = {
                    @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
                    @Parameter(name = "produtoId", description = "ID de um restaurante", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Foto deletada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Foto do produto não encontrada",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> deletar( Long restauranteId, Long produtoId);
}

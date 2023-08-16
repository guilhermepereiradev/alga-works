package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.api.v1.openapi.model.ProdutosCollectionModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Produtos", description = "Gerencia os restaurantes")
public interface RestauranteProdutoControllerOpenApi {


    @Operation(
            summary = "Lista os produtos de um restaurante",
            parameters = {
                    @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
                    @Parameter(
                            description = "Retorna produtos inativos",
                            name = "incluirInativos", in = ParameterIn.QUERY,
                            schema = @Schema(description = "true", type = "boolean", allowableValues = {"true", "false"})
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Produtos encontrado",
                            content = @Content(schema = @Schema(implementation = ProdutosCollectionModelOpenApi.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do restaurante ou parâmetros inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<CollectionModel<ProdutoModel>> listar(Long restauranteId, @RequestParam(required = false) Boolean incluirInativo);

    @Operation(
            summary = "Busca produto de um restaurante por id",
            parameters = {
                    @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
                    @Parameter(name = "produtoId", description = "ID de um produto", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Produto encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do restaurante ou produto inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante ou produto não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<ProdutoModel> buscar(Long restauranteId, Long produtoId);

    @Operation(summary = "Cadastra um produto",
            parameters = @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Produto criado"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<ProdutoModel> salvar(Long restauranteId, ProdutoInput produtoInput);

    @Operation(
            summary = "Busca produto de um restaurante por id",
            parameters = {
                    @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
                    @Parameter(name = "produtoId", description = "ID de um produto", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Produto alterado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do restaurante ou produto inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante ou produto não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<ProdutoModel> atualizar(Long restauranteId, Long produtoId, ProdutoInput produtoInput);
}

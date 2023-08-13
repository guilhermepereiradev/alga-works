package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.openapi.model.FormasPagamentoCollectionModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @Operation(
            summary = "Lista as formas de pagamento associadas a restaurante",
            parameters = @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Formas de pagamento encontradas",
                            content = @Content(schema = @Schema(implementation = FormasPagamentoCollectionModelOpenApi.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(Long restauranteId);


    @Operation(
            summary = "Associação de restaurante com forma de pagamento",
            parameters = {
                    @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
                    @Parameter(name = "formaPagamentoId", description = "ID de uma forma de pagamento", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Forma de pagamento associada ao restaurante"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante ou forma de pagamento não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> associarFormaPagamento(Long restauranteId, Long formaPagamentoId);


    @Operation(
            summary = "Desassociação de restaurante com forma de pagamento",
            parameters = {
                    @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
                    @Parameter(name = "formaPagamentoId", description = "ID de uma forma de pagamento", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Forma de pagamento desassociada ao restaurante"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante ou forma de pagamento não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId);
}

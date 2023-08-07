package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

    @Operation(
            summary = "Confirmação de pedido",
            parameters = @Parameter(name = "codigo", description = "Código de um pedido", example = "5c621c9a-ba61-4454-8631-8aabefe58dc2"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Pedido confirmado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Código do pedido inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pedido não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> confirmar(String codigo);

    @Operation(
            summary = "Registrar entrega de pedido",
            parameters = @Parameter(name = "codigo", description = "Código de um pedido", example = "5c621c9a-ba61-4454-8631-8aabefe58dc2"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Pedido entregue com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Código do pedido inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pedido não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> entregar(String codigo);

    @Operation(
            summary = "Cancelamento de pedido",
            parameters = @Parameter(name = "codigo", description = "Código de um pedido", example = "5c621c9a-ba61-4454-8631-8aabefe58dc2"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Pedido cancelado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Código do pedido inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pedido não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> cancelar(String codigo);
}

package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoFilterInput;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.openapi.model.PedidosModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;


@Tag(name = "Pedidos", description = "Gerencia os pedidos")
public interface PedidoControllerOpenApi {

    @Operation(
            summary = "Lista os pedidos com paginação",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pedidos encontrado",
                            content = @Content(schema = @Schema(implementation = PedidosModelOpenApi.class))
                    )
            }
    )
    ResponseEntity<PagedModel<PedidoResumoModel>> listar(@ParameterObject Pageable pageable, @ParameterObject PedidoFilterInput pedidoFilterInput);


    @Operation(
            summary = "Buscar um pedido por código",
            parameters = @Parameter(name = "codigo", description = "Código de um pedido", example = "5c621c9a-ba61-4454-8631-8aabefe58dc2"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pedido encontrado",
                            content = @Content(schema = @Schema(implementation = PedidoModel.class))
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
    ResponseEntity<PedidoModel> buscar(String codigo);

    @Operation(summary = "Cadastra um pedido",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Pedido criado",
                            content = @Content(schema = @Schema(implementation = PedidoModel.class))
                    )
            }
    )
    ResponseEntity<PedidoModel> emitir(PedidoInput pedidoInput);
}

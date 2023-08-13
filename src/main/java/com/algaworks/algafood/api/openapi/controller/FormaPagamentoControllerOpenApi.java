package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Tag(name = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

    @Operation(summary = "Lista as formas de pagamento")
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

    @Operation(
            summary = "Busca uma forma de pagamento por ID",
            parameters = @Parameter(name = "id", description = "ID de uma forma de pagamento", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Forma de pagamento encontrada",
                            content = @Content(schema = @Schema(implementation = FormaPagamentoModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID da forma de pagamento inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Forma de pagamento não encontrada",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<FormaPagamentoModel> buscar(Long id, ServletWebRequest request);

    @Operation(
            summary = "Cadastra uma forma de pagamento",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Forma de pagamento criada",
                            content = @Content(schema = @Schema(implementation = FormaPagamentoModel.class))
                    )
            }
    )
    ResponseEntity<FormaPagamentoModel> salvar(FormaPagamentoInput formaPagamentoInput);

    @Operation(
            summary = "Atualiza uma forma de pagamento por ID",
            parameters = @Parameter(name = "id", description = "ID de uma forma de pagamento", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Forma de pagamento atualizada",
                            content = @Content(schema = @Schema(implementation = FormaPagamentoModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Forma de pagamento não encontrada",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<FormaPagamentoModel> atualizar(Long id, FormaPagamentoInput formaPagamentoInput);

    @Operation(
            summary = "Exclui uma forma de pagamento por ID",
            parameters = @Parameter(name = "id", description = "ID de uma forma de pagamento", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Forma de pagamento excluída com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Forma de pagamento não encontrada",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> deletar(Long id);
}

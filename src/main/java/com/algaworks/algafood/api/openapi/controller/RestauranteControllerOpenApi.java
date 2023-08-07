package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @Operation(
            summary = "Lista de restaurantes",
            parameters = {
                    @Parameter(
                            description = "Nome da projeção de pedidos",
                            name = "projecao", in = ParameterIn.QUERY,
                            schema = @Schema(description = "apenas-nome", type = "string", allowableValues = {"apenas-nome"})
                    )
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Restaurantes encontrados",
                    content = @Content(schema = @Schema(implementation = RestauranteBasicoModelOpenApi.class)))
    )
    ResponseEntity<List<RestauranteModel>> listar();

    @Operation(summary = "Lista de restaurantes", hidden = true)
    ResponseEntity<List<RestauranteModel>> listarApenasNome();

    @Operation(
            summary = "Busca um restaurante por ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Restaurante encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do restaurante inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<RestauranteModel> buscar(Long id);

    @Operation(summary = "Cadastra uma restaurante",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Restaurante criado",
                            content = @Content(schema = @Schema(implementation = RestauranteModel.class)))
            }
    )
    ResponseEntity<RestauranteModel> adicionar(RestauranteInput restauranteInput);


    @Operation(
            summary = "Atualiza um restaurante por ID",
            parameters = @Parameter(name = "id", description = "ID de um restaurante", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Restaurante atualizado",
                            content = @Content(schema = @Schema(implementation = RestauranteModel.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<?> atualizar(Long id, RestauranteInput restauranteInput);

    @Operation(
            summary = "Ativa um restaurante por ID",
            parameters = @Parameter(name = "id", description = "ID de um restaurante", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Restaurante ativado com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<Void> ativar(Long id);


    @Operation(
            summary = "Inativa um restaurante por ID",
            parameters = @Parameter(name = "id", description = "ID de um restaurante", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Restaurante inativado com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<Void> inativar(Long id);


    @Operation(
            summary = "Ativa múltiplos restaurantes por ID",
            responses = @ApiResponse(responseCode = "200", description = "Restaurantes ativados com sucesso")
    )
    ResponseEntity<Void> ativarMultiplos(List<Long> restauranteIds);


    @Operation(
            summary = "Inativa múltiplos restaurantes por ID",
            responses = @ApiResponse(responseCode = "200", description = "Restaurantes inativados com sucesso")
    )
    ResponseEntity<Void> inativarMultiplos(List<Long> restauranteIds);


    @Operation(
            summary = "Abre um restaurante por ID",
            parameters = @Parameter(name = "id", description = "ID de um restaurante", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Restaurante aberto com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<Void> abrir(Long id);

    @Operation(
            summary = "Fecha um restaurante por ID",
            parameters = @Parameter(name = "id", description = "ID de um restaurante", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Restaurante fechado com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<Void> fechar(Long id);

    @Operation(hidden = true)
    ResponseEntity<?> atualizarParcial(Long id, Map<String, Object> dadosOrigem, HttpServletRequest request);
}

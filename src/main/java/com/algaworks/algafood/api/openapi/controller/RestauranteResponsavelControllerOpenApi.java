package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Restaurantes")
public interface RestauranteResponsavelControllerOpenApi {

    @Operation(
            summary = "Lista os usuários responsáveis associados a restaurante",
            parameters = @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuários encontradas"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<List<UsuarioModel>> listar(Long restauranteId);

    @Operation(
            summary = "Desassociação de restaurante com usuário responsável",
            parameters = {
                    @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
                    @Parameter(name = "responsavelId", description = "ID de um usuário responsável", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuário desassociado ao restaurante com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante ou usuário não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> desassociar(Long restauranteId, Long responsavelId) ;

    @Operation(
            summary = "Associação de restaurante com usuário responsável",
            parameters = {
                    @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
                    @Parameter(name = "responsavelId", description = "ID de um usuário responsável", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuário associado ao restaurante com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante ou usuário não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> associar( Long restauranteId, Long responsavelId);
}

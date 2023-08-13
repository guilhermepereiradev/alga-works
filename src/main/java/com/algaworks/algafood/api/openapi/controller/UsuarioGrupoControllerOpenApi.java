package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuários")
public interface UsuarioGrupoControllerOpenApi {


    @Operation(
            summary = "Lista os grupos associados a um usuário",
            parameters = @Parameter(name = "usuarioId", description = "ID de um usuário", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuários encontradas"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do usuário não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<CollectionModel<GrupoModel>> listar(Long usuarioId);

    @Operation(
            summary = "Associação de grupo com usuário",
            parameters = {
                    @Parameter(name = "usuarioId", description = "ID de um usuário", example = "1"),
                    @Parameter(name = "grupoId", description = "ID de um grupo", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Grupo associado ao usuário"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário ou grupo não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> associar(Long usuarioId, Long grupoId);

    @Operation(
            summary = "Desassociação de grupo com usuário",
            parameters = {
                    @Parameter(name = "usuarioId", description = "ID de um usuário", example = "1"),
                    @Parameter(name = "grupoId", description = "ID de um grupo", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Grupo desassociado ao usuário"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário ou grupo não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> desassociar(Long usuarioId, Long grupoId);
}

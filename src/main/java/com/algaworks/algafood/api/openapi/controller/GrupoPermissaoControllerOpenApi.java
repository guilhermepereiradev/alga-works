package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PermissaoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Grupos")
public interface GrupoPermissaoControllerOpenApi {


    @Operation(
            summary = "Lista as permissões",
            parameters = @Parameter(name = "grupoId", description = "ID de um grupo", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Permissões encontradas"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do grupo inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Grupo não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<List<PermissaoModel>> listar(Long grupoId);

    @Operation(
            summary = "Associação de permissão com grupo",
            parameters = {
                    @Parameter(name = "grupoId", description = "ID de um grupo", example = "1"),
                    @Parameter(name = "permissaoId", description = "ID da permissão", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Permissão associada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do grupo ou permissão inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Grupo ou Permissão não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> associar(Long grupoId, Long permissaoId);

    @Operation(
            summary = "Desassociação de permissão com grupo",
            parameters = {
                    @Parameter(name = "grupoId", description = "ID de um grupo", example = "1"),
                    @Parameter(name = "permissaoId", description = "ID da permissão", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Permissão desassociada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do grupo ou permissão inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Grupo ou Permissão não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> desassociar(Long grupoId, Long permissaoId);
}

package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.openapi.model.UsuariosCollectionModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuários", description = "Gerencia os usuários")
public interface UsuarioControllerOpenApi {

    @Operation(
            summary = "Lista os usuários",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuários encontrado",
                            content = @Content(schema = @Schema(implementation = UsuariosCollectionModelOpenApi.class))
                    )
            }
    )
    ResponseEntity<CollectionModel<UsuarioModel>> listar();

    @Operation(
            summary = "Buscar um usuário por ID",
            parameters = @Parameter(name = "id", description = "ID de um usuário", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário encontrado",
                            content = @Content(schema = @Schema(implementation = UsuariosCollectionModelOpenApi.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do usuário inválido",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<UsuarioModel> buscar(Long id);

    @Operation(summary = "Cadastra um usuário por ID",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário criado",
                            content = @Content(schema = @Schema(implementation = UsuarioModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Email já esta sendo utilizado por outro usuário",
                            content = @Content(schema = @Schema(implementation = UsuarioModel.class))
                    )
            }
    )
    ResponseEntity<UsuarioModel> salvar(UsuarioComSenhaInput usuarioInput);

    @Operation(summary = "Atualiza um usuário por ID",
            parameters = @Parameter(name = "id", description = "ID de um usuário", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário alterado",
                            content = @Content(schema = @Schema(implementation = UsuarioModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Email já esta sendo utilizado por outro usuário",
                            content = @Content(schema = @Schema(implementation = UsuarioModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<UsuarioModel> atualizar(UsuarioInput usuarioInput, Long id);

    @Operation(summary = "Deleta um usuário por ID",
            parameters = @Parameter(name = "id", description = "ID de um usuário", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuário deletado com sucesso",
                            content = @Content(schema = @Schema(implementation = UsuarioModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> deletar(Long id);


    @Operation(summary = "Deleta um usuário por ID",
            parameters = @Parameter(name = "id", description = "ID de um usuário", example = "1"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Senha alterada com sucesso",
                            content = @Content(schema = @Schema(implementation = UsuarioModel.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Senha informada não coincide com senha atual do usuário",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(schema = @Schema(implementation = Problem.class))
                    )
            }
    )
    ResponseEntity<Void> alterarSenha(SenhaInput senhaInput, Long id);
}

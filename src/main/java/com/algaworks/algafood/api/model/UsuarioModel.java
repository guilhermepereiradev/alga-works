package com.algaworks.algafood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Maria Joaquina")
    private String nome;
    @Schema(example = "maria.vnd@algafood.com")
    private String email;
}

package com.algaworks.algafood.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

    @Schema(example = "Sete Lagoas")
    @NotBlank
    private String nome;

    @NotNull
    private EstadoIdInput estado;
}

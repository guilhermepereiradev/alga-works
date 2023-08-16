package com.algaworks.algafood.api.v2.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputV2 {

    @Schema(example = "Uberlândia")
    @NotBlank
    private String nomeCidade;

    @Schema(example = "1")
    @NotNull
    private Long idEstado;
}

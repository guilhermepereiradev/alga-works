package com.algaworks.algafood.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

    @Schema(example = "38400-800")
    @NotBlank
    private String cep;

    @Schema(example = "Rua Fortaleza")
    @NotBlank
    private String logradouro;

    @Schema(example = "900")
    @NotBlank
    private String numero;

    @Schema(example = "Apto 504")
    private String complemento;

    @Schema(example = "Centro")
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;
}

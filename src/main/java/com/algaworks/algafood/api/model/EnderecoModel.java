package com.algaworks.algafood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

    @Schema(example = "38400-800")
    private String cep;
    @Schema(example = "Rua Fortaleza")
    private String logradouro;
    @Schema(example = "900")
    private String numero;
    @Schema(example = "Apto 504")
    private String complemento;
    @Schema(example = "Centro")
    private String bairro;
    private CidadeResumoModel cidade;
}

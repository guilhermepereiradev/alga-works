package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {

    @Schema(name = "742f0b56-363e-11ee-be56-0242ac120002")
    private String nomeArquivo;

    @Schema(name = "Descrição da foto")
    private String descricao;

    @Schema(name = "image/png")
    private String contentType;

    @Schema(name = "438653", description = "Tamanho em bytes")
    private Long tamanho;
}

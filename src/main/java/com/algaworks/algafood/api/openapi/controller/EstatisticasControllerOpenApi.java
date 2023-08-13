package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.controller.EstatisticasController;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.model.filter.VendaDiariaFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    @Operation(summary = "Lista os endpoints de estatísticas")
    ResponseEntity<EstatisticasController.EstatisticasModel> listarLinksEstatisticas();

    @Operation(
            summary = "Consulta as estatísticas de vendas diárias",
            parameters = {
                    @Parameter(name = "restauranteId", description = "ID de um restaurante", example = "1"),
                    @Parameter(name = "dataCriacaoInicio", description = "Data/hora inicial da criação do pedido", example = "2023-01-01T23:00:00Z"),
                    @Parameter(name = "dataCriacaoFim", description = "Data/hora final da criação do pedido", example = "2023-12-23T23:23:59Z"),
                    @Parameter(name = "timeOffset", description = "Deslocamento de horário a ser considerado na consulta em relação ao UTC"),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = VendaDiaria.class)),
                                    @Content(mediaType ="application/json"),
                                    @Content(mediaType = "application/pdf")
                            }
                    )
            }
    )
    List<VendaDiaria> consultarVendasDiarias(@ParameterObject VendaDiariaFilter filtro,
                                             @RequestParam(required = false, defaultValue = "+00:00") String timeOffset);

    @Operation(summary = "Consulta as estatísticas de vendas diárias", hidden = true)
    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
                                                     @RequestParam(required = false, defaultValue = "+00:00") String timeOffset);
}

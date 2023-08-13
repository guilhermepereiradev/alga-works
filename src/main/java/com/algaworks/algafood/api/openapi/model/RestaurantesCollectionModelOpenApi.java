package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.RestauranteBasicoModel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RestaurantesModel")
public class RestaurantesCollectionModelOpenApi extends CollectionModelOpenApi<RestauranteBasicoModel>{
}

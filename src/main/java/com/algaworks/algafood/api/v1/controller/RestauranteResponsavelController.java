package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/responsaveis")
public class RestauranteResponsavelController implements RestauranteResponsavelControllerOpenApi {

    @Autowired
    private CadastroRestauranteService restauranteService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioModel>> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        CollectionModel<UsuarioModel> usuarioCollectionModel =
                usuarioModelAssembler.toCollectionModel(restaurante.getUsuariosResponsaveis())
                        .removeLinks()
                        .add(algaLinks.linkToResponsaveisRestaurante(restauranteId));

        usuarioCollectionModel.getContent().forEach(usuarioModel ->
                usuarioModel.add(algaLinks.linkToRestauranteResponsaveisDessassociar(
                        restauranteId, usuarioModel.getId(), "desassociar")));

        usuarioCollectionModel.add(algaLinks.linkToRestauranteResponsaveisAssociar(restauranteId, "associar"));

        return ResponseEntity.ok(usuarioCollectionModel);
    }

    @DeleteMapping("/{responsavelId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.desassociarUsuarioResponsavel(restauranteId, responsavelId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{responsavelId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.associarUsuarioResponsavel(restauranteId, responsavelId);
        return ResponseEntity.noContent().build();
    }
}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.openapi.controller.RestauranteResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
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

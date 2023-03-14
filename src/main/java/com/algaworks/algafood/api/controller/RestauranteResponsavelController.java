package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteResponsavelController {

    @Autowired
    private CadastroRestauranteService restauranteService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listar(@PathVariable Long restauranteId){
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return ResponseEntity.ok().body(usuarioModelAssembler.toCollectionModel(restaurante.getUsuariosResponsaveis()));
    }

    @DeleteMapping("/{responsavelId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId){
        restauranteService.desassociarUsuarioResponsavel(restauranteId, responsavelId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{responsavelId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId){
        restauranteService.associarUsuarioResponsavel(restauranteId, responsavelId);
        return ResponseEntity.noContent().build();
    }
}

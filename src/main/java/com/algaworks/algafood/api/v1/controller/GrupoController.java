package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.GrupoInputDisassempler;
import com.algaworks.algafood.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private GrupoInputDisassempler grupoInputDisassempler;

    @GetMapping
    public ResponseEntity<CollectionModel<GrupoModel>> listar() {
        List<Grupo> grupos = grupoService.listar();

        return ResponseEntity.ok(grupoModelAssembler.toCollectionModel(grupos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoModel> buscar(@PathVariable Long id) {
        Grupo grupo = grupoService.buscarOuFalhar(id);

        return ResponseEntity.ok(grupoModelAssembler.toModel(grupo));
    }

    @PostMapping
    public ResponseEntity<GrupoModel> salvar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassempler.toDomainModel(grupoInput);
        grupo = grupoService.salvar(grupo);

        URI uri = ResourceUriHelper.createUri(grupo.getId());

        return ResponseEntity.created(uri).body(grupoModelAssembler.toModel(grupo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoModel> atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoService.buscarOuFalhar(id);
        grupoInputDisassempler.copyToDomainObject(grupoInput, grupo);

        grupo = grupoService.salvar(grupo);

        return ResponseEntity.ok(grupoModelAssembler.toModel(grupo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        grupoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoInputDisassempler;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private GrupoInputDisassempler grupoInputDisassempler;

    @GetMapping
    public ResponseEntity<List<GrupoModel>> listar() {
        return ResponseEntity.ok().body(grupoModelAssembler.toCollectionModels(grupoService.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoModel> buscar(@PathVariable Long id) {
        return ResponseEntity.ok().body(grupoModelAssembler.toModel(grupoService.buscarOuFalhar(id)));
    }

    @PostMapping
    public ResponseEntity<GrupoModel> salvar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoService.salvar(grupoInputDisassempler.toDomainModel(grupoInput));
        return ResponseEntity.status(HttpStatus.CREATED).body(grupoModelAssembler.toModel(grupo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoModel> atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoService.buscarOuFalhar(id);

        grupoInputDisassempler.copyToDomainObject(grupoInput, grupo);

        return ResponseEntity.status(HttpStatus.CREATED).body(grupoModelAssembler.toModel(grupoService.salvar(grupo)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        grupoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

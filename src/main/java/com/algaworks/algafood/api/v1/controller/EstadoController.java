package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private CadastroEstadoService estadoService;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EstadoModel>> listar() {
        List<Estado> estados = estadoService.listar();
        return ResponseEntity.ok(estadoModelAssembler.toCollectionModel(estados));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoModel> buscar(@PathVariable Long id) {
        Estado estado = estadoService.buscarOuFalhar(id);
        return ResponseEntity.ok(estadoModelAssembler.toModel(estado));
    }

    @PostMapping
    public ResponseEntity<EstadoModel> salvar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
        estado = estadoService.salvar(estado);

        URI uri = ResourceUriHelper.createUri(estado.getId());

        return ResponseEntity.created(uri).body(estadoModelAssembler.toModel(estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoModel> atualizar(@RequestBody @Valid EstadoInput estadoInput, @PathVariable Long id) {
        Estado estadoAtual = estadoService.buscarOuFalhar(id);
        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        estadoAtual = estadoService.salvar(estadoAtual);

        return ResponseEntity.ok(estadoModelAssembler.toModel(estadoAtual));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        estadoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}

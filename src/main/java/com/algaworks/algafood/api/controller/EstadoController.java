package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public ResponseEntity<List<EstadoModel>> listar() {
        return ResponseEntity.ok().body(estadoModelAssembler.toCollectionModels(estadoRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoModel> buscar(@PathVariable Long id) {
        return ResponseEntity.ok().body(estadoModelAssembler.toModel(cadastroEstado.buscarOuFalhar(id)));
    }

    @PostMapping
    public ResponseEntity<EstadoModel> salvar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = cadastroEstado.salvar(estadoInputDisassembler.toDomainObject(estadoInput));
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoModelAssembler.toModel(estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoModel> atualizar(@RequestBody @Valid EstadoInput estadoInput, @PathVariable Long id) {
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);
        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        return ResponseEntity.ok().body(estadoModelAssembler.toModel(cadastroEstado.salvar(estadoAtual)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        cadastroEstado.remover(id);
        return ResponseEntity.noContent().build();
    }
}

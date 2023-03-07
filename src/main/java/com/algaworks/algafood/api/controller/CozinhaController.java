package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelAssembler cozinhaAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping
    public ResponseEntity<List<CozinhaModel>> listar() {
        return ResponseEntity.ok().body(cozinhaAssembler.toCollectionModel(cozinhaRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CozinhaModel> buscar(@PathVariable Long id){
        return ResponseEntity.ok().body(cozinhaAssembler.toModel(cadastroCozinha.buscarOuFalhar(id)));
    }

    @PostMapping
    public ResponseEntity<CozinhaModel> salvar(@RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        CozinhaModel novaCozinha = cozinhaAssembler.toModel(cadastroCozinha.salvar(cozinha));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(novaCozinha.getId()).toUri();

        return ResponseEntity.created(uri).body(novaCozinha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CozinhaModel> atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

        return ResponseEntity.ok().body(cozinhaAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual)));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        cadastroCozinha.remover(id);
    }
}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelAssembler cozinhaAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;


    @GetMapping
    public ResponseEntity<Page<CozinhaModel>> listar(@PageableDefault(size = 2) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cadastroCozinha.listar(pageable);
        List<CozinhaModel> cozinhasModel = cozinhaAssembler.toCollectionModel(cozinhasPage.getContent());
        Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());
        return ResponseEntity.ok().body(cozinhasModelPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CozinhaModel> buscar(@PathVariable Long id) {
        return ResponseEntity.ok().body(cozinhaAssembler.toModel(cadastroCozinha.buscarOuFalhar(id)));
    }

    @PostMapping
    public ResponseEntity<CozinhaModel> salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        CozinhaModel novaCozinha = cozinhaAssembler.toModel(cadastroCozinha.salvar(cozinha));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(novaCozinha.getId()).toUri();

        return ResponseEntity.created(uri).body(novaCozinha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CozinhaModel> atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

        return ResponseEntity.ok().body(cozinhaAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cadastroCozinha.remover(id);
        return ResponseEntity.noContent().build();
    }
}

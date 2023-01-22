package com.algaworks.algafood.api.controller;

import java.net.URI;
import java.util.List;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @GetMapping
    public ResponseEntity<List<Cozinha>> listar() {
        List<Cozinha> cozinhas = cozinhaRepository.listar();
        return ResponseEntity.ok().body(cozinhas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long id){
        Cozinha cozinha = cozinhaRepository.buscar(id);

        if (cozinha != null) {
            return ResponseEntity.ok().body(cozinha);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha){
        Cozinha novaCozinha = cadastroCozinha.salvar(cozinha);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(novaCozinha.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha){
        Cozinha cozinhaAtual = cozinhaRepository.buscar(id);

        if (cozinhaAtual != null) {
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

            cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);

            return ResponseEntity.ok().body(cozinhaAtual);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        try {
            cadastroCozinha.remover(id);

            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}

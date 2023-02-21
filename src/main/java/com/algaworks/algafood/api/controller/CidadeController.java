package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @GetMapping
    public ResponseEntity<List<Cidade>> listar(){
        List<Cidade> cidades = cidadeRepository.findAll();
        return ResponseEntity.ok().body(cidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long id){
        Cidade cidade = cadastroCidade.buscarOuFalhar(id);

        return ResponseEntity.ok().body(cidade);
    }

    @PostMapping()
    public ResponseEntity<Cidade> salvar(@RequestBody @Valid Cidade cidade){
        try {
            cidade = cadastroCidade.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e.getCause());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizar(@RequestBody @Valid Cidade cidade, @PathVariable Long id){
        try{
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);

            BeanUtils.copyProperties(cidade, cidadeAtual, "id");

            Cidade cidadeSalva = cadastroCidade.salvar(cidadeAtual);
            return ResponseEntity.ok().body(cidadeSalva);
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e.getCause());
        }
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id){
        cadastroCidade.remover(id);
    }
}

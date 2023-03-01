package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
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

    @Autowired
    private CidadeModelAssembler cidadeAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeDisassembler;

    @GetMapping
    public ResponseEntity<List<CidadeModel>> listar(){
        return ResponseEntity.ok().body(cidadeAssembler.toCollectionModel(cidadeRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeModel> buscar(@PathVariable Long id){
        return ResponseEntity.ok().body(cidadeAssembler.toModel(cadastroCidade.buscarOuFalhar(id)));
    }

    @PostMapping()
    public ResponseEntity<CidadeModel> salvar(@RequestBody @Valid CidadeInput cidadeInput){
        try {
            Cidade cidade = cadastroCidade.salvar(cidadeDisassembler.toDomainObject(cidadeInput));
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeAssembler.toModel(cidade));
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e.getCause());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeModel> atualizar(@RequestBody @Valid CidadeInput cidadeInput, @PathVariable Long id){
        try{
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
            cidadeDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            return ResponseEntity.ok().body(cidadeAssembler.toModel(cadastroCidade.salvar(cidadeAtual)));
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e.getCause());
        }
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id){
        cadastroCidade.remover(id);
    }
}

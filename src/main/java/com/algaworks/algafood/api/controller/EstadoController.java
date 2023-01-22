package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @GetMapping
    public ResponseEntity<List<Estado>> listar(){
        return ResponseEntity.ok().body(estadoRepository.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscar(@PathVariable Long id){
        Estado estado = estadoRepository.buscar(id);

        if (estado != null) {
            return ResponseEntity.ok().body(estado);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Estado> salvar(@RequestBody Estado estado){
        estado = cadastroEstado.salvar(estado);

        return ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> atualizar(@RequestBody Estado estado, @PathVariable Long id){
        Estado estadoAtual = estadoRepository.buscar(id);

        if(estadoAtual == null){
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(estado, estadoAtual, "id");
        estadoAtual = cadastroEstado.salvar(estadoAtual);

        return ResponseEntity.ok().body(estadoAtual);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id){
        try {
            cadastroEstado.remover(id);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (EntidadeEmUsoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

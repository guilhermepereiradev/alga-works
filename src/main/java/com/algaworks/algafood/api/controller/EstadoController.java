package com.algaworks.algafood.api.controller;

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
        return ResponseEntity.ok().body(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscar(@PathVariable Long id){
        Estado estado = cadastroEstado.buscarOuFalhar(id);

        return ResponseEntity.ok().body(estado);
    }

    @PostMapping
    public ResponseEntity<Estado> salvar(@RequestBody Estado estado){
        estado = cadastroEstado.salvar(estado);

        return ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> atualizar(@RequestBody Estado estado, @PathVariable Long id){
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);

        BeanUtils.copyProperties(estado, estadoAtual, "id");
        Estado estadoSalva = cadastroEstado.salvar(estadoAtual);

        return ResponseEntity.ok().body(estadoSalva);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id){
        cadastroEstado.remover(id);
    }
}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listar(){
        return ResponseEntity.ok().body(usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscar(@PathVariable Long id){
        return ResponseEntity.ok().body(usuarioModelAssembler.toModel(usuarioService.buscarOuFalhar(id)));
    }

    @PostMapping
    public ResponseEntity<UsuarioModel> salvar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput){
        Usuario usuario = usuarioService.salvar(usuarioInputDisassembler.toDomainModel(usuarioInput));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModelAssembler.toModel(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> atualizar(@RequestBody @Valid UsuarioInput usuarioInput, @PathVariable Long id){
        Usuario usuario = usuarioService.buscarOuFalhar(id);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuario);

        return ResponseEntity.ok().body(usuarioModelAssembler.toModel(usuarioService.salvar(usuario)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> alterarSenha(@RequestBody @Valid SenhaInput senhaInput, @PathVariable Long id){
        usuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
        return ResponseEntity.noContent().build();
    }
}

package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioModel>> listar() {
        List<Usuario> usuarios = usuarioService.listar();
        return ResponseEntity.ok(usuarioModelAssembler.toCollectionModel(usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarOuFalhar(id);
        return ResponseEntity.ok(usuarioModelAssembler.toModel(usuario));
    }

    @PostMapping
    public ResponseEntity<UsuarioModel> salvar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainModel(usuarioInput);
        usuario = usuarioService.salvar(usuario);

        URI uri = ResourceUriHelper.createUri(usuario.getId());

        return ResponseEntity.created(uri)
                .body(usuarioModelAssembler.toModel(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> atualizar(@RequestBody @Valid UsuarioInput usuarioInput, @PathVariable Long id) {
        Usuario usuario = usuarioService.buscarOuFalhar(id);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuario);

        usuario = usuarioService.salvar(usuario);

        return ResponseEntity.ok(usuarioModelAssembler.toModel(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> alterarSenha(@RequestBody @Valid SenhaInput senhaInput, @PathVariable Long id) {
        usuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
        return ResponseEntity.noContent().build();
    }
}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {


    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public ResponseEntity<CollectionModel<GrupoModel>> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        CollectionModel<GrupoModel> grupoCollectionModel = grupoModelAssembler.toCollectionModel(usuario.getGrupos());

        grupoCollectionModel.getContent().forEach(grupo ->
                grupo.add(algaLinks.linkToUsuariosGruposDesassociar(usuarioId, grupo.getId(), "desassociar"))
        );

        grupoCollectionModel.add(algaLinks.linkToUsuariosGruposAssociar(usuarioId, "associar"));

        return ResponseEntity.ok(grupoCollectionModel);
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }
}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private PermissaoModelAssembler permissaoAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public ResponseEntity<CollectionModel<PermissaoModel>> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        CollectionModel<PermissaoModel> permissaoCollectionModel
                = permissaoAssembler.toCollectionModel(grupo.getPermissoes());

        permissaoCollectionModel.getContent().forEach(permissao ->
                permissao.add(algaLinks.linkToGruposPermissoesDesassociar(
                        grupoId, permissao.getId(), "desassociar"))
        );

        permissaoCollectionModel.add(algaLinks.linkToGruposPermissoesAssociar(grupoId, "associar"));

        return ResponseEntity.ok(permissaoCollectionModel);
    }

    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }
}

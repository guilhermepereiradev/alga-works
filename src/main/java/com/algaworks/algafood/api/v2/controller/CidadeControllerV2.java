package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CidadeModelControllerV2OpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeModelControllerV2OpenApi {

    @Autowired
    private CadastroCidadeService cidadeService;

    @Autowired
    private CidadeModelAssemblerV2 cidadeModelAssemblerV2;

    @Autowired
    private CidadeInputDisassemblerV2 cidadeInputDisassemblerV2;

    @GetMapping
    public ResponseEntity<CollectionModel<CidadeModelV2>> listar() {
        List<Cidade> cidades = cidadeService.listar();
        return ResponseEntity.ok(cidadeModelAssemblerV2.toCollectionModel(cidades));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeModelV2> buscar(@PathVariable Long id) {
        Cidade cidade = cidadeService.buscarOuFalhar(id);
        return ResponseEntity.ok(cidadeModelAssemblerV2.toModel(cidade));
    }

    @PostMapping()
    public ResponseEntity<CidadeModelV2> salvar(@RequestBody @Valid CidadeInputV2 cidadeInputV2) {
        try {
            Cidade cidade = cidadeInputDisassemblerV2.toDomainObject(cidadeInputV2);
            cidade = cidadeService.salvar(cidade);

            URI uri = ResourceUriHelper.createUri(cidade.getId());

            return ResponseEntity.created(uri)
                    .body(cidadeModelAssemblerV2.toModel(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e.getCause());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeModelV2> atualizar(@RequestBody @Valid CidadeInputV2 cidadeInputV2, @PathVariable Long id) {
        try {
            Cidade cidade = cidadeService.buscarOuFalhar(id);
            cidadeInputDisassemblerV2.copyToDomainObject(cidadeInputV2, cidade);
            cidade = cidadeService.salvar(cidade);

            return ResponseEntity.ok(cidadeModelAssemblerV2.toModel(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e.getCause());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        cidadeService.remover(id);
        return ResponseEntity.noContent().build();
    }
}

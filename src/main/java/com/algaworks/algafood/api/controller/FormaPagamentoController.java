package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";
        OffsetDateTime ultimaAtualizacao = formaPagamentoService.getUltimaDataAtualizacao();

        if (ultimaAtualizacao != null) {
            eTag = String.valueOf(ultimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamento> formasPagamento = formaPagamentoService.listar();

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamentoModelAssembler.toCollectionModel(formasPagamento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long id, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";
        OffsetDateTime dataAtualizacao = formaPagamentoService.getDataAtulizacao(id);

        if (dataAtualizacao != null) {
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(id);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamentoModelAssembler.toModel(formaPagamento));
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoModel> salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainModel(formaPagamentoInput);
        formaPagamento = formaPagamentoService.salvar(formaPagamento);

        URI uri = ResourceUriHelper.createUri(formaPagamento.getId());

        return ResponseEntity.created(uri).body(formaPagamentoModelAssembler.toModel(formaPagamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> atualizar(@PathVariable Long id, @Valid @RequestBody FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(id);
        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

        formaPagamentoAtual = formaPagamentoService.salvar(formaPagamentoAtual);

        return ResponseEntity.ok(formaPagamentoModelAssembler.toModel(formaPagamentoAtual));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        formaPagamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

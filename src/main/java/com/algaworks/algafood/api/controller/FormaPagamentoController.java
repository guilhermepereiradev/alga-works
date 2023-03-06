package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoModel>> listar(){
        return ResponseEntity.ok().body(
                formaPagamentoModelAssembler.toCollectionModels(formaPagamentoRepository.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long id){
        return ResponseEntity.ok().body(formaPagamentoModelAssembler.toModel(formaPagamentoService.buscarOuFalhar(id)));
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoModel> salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamento = formaPagamentoService.salvar(formaPagamentoInputDisassembler.toDomainModel(formaPagamentoInput));
        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoModelAssembler.toModel(formaPagamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> atualizaar(@PathVariable Long id, @Valid @RequestBody FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(id);
        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

        return ResponseEntity.ok().body(formaPagamentoModelAssembler.toModel(formaPagamentoService.salvar(formaPagamentoAtual)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        formaPagamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

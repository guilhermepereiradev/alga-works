package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private CadastroProdutoService produtoService;

    @Autowired
    private CadastroRestauranteService restauranteService;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @GetMapping
    public ResponseEntity<List<ProdutoModel>> listar(@PathVariable Long restauranteId, @RequestParam(required = false) Boolean incluirInativo) {
        List<ProdutoModel> produtoModelList;

        if (incluirInativo != null && incluirInativo) {
            produtoModelList = produtoModelAssembler.toCollectionModel(produtoService.buscarPeloRestauranteId(restauranteId));
        } else {
            produtoModelList = produtoModelAssembler.toCollectionModel(produtoService.buscarAtivosPeloRestaurante(restauranteId));
        }

        return ResponseEntity.ok().body(produtoModelList);
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoModel> buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return ResponseEntity.ok().body(produtoModelAssembler.toModel(produtoService.buscarOuFalhar(restauranteId, produtoId)));
    }

    @PostMapping()
    public ResponseEntity<ProdutoModel> salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainModel(produtoInput);
        produto.setRestaurante(restaurante);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoModelAssembler.toModel(produtoService.salvar(produto)));
    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<ProdutoModel> atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoModelAssembler.toModel(produtoService.salvar(produtoAtual)));
    }
}

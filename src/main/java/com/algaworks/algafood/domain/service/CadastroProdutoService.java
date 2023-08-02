package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CadastroRestauranteService restauranteService;


    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return repository.findById(restauranteId, produtoId).orElseThrow(
                () -> new ProdutoNaoEncontradoException(produtoId, restauranteId)
        );
    }

    public List<Produto> buscarPeloRestauranteId(Long restauranteId) {
        return repository.findByRestaurante(restauranteService.buscarOuFalhar(restauranteId));
    }

    public List<Produto> buscarAtivosPeloRestaurante(Long restauranteId) {
        return repository.findAtivosByRestaurante(restauranteService.buscarOuFalhar(restauranteId));
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }
}

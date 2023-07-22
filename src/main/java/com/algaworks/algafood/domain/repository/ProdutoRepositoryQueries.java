package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto fotoProduto);

    void delete(FotoProduto fotoProduto);
}

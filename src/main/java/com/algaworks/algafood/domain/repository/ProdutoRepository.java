package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    @Query("from Produto where restaurante.id = :restauranteId and id = :produtoId")
    Optional<Produto> findById(Long restauranteId, Long produtoId);

    List<Produto> findByRestaurante(Restaurante restaurante);

    @Query("from Produto p where p.restaurante = :restaurante and p.ativo = true")
    List<Produto> findAtivosByRestaurante(Restaurante restaurante);
}

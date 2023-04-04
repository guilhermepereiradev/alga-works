package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    public void calcularPrecoTotal(){
        BigDecimal precoUnitario = getPrecoUnitario();
        Integer quantidade = getQuantidade();

        if(precoUnitario == null){
            precoUnitario = BigDecimal.ZERO;
        }

        if(quantidade == null){
            quantidade = 0;
        }

        setPrecoTotal(precoUnitario.multiply(BigDecimal.valueOf(quantidade)));
    }
}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhaPorNome(@RequestParam("nome") String nome){
        return cozinhaRepository.findAllByNomeContains(nome);
    }

//    @GetMapping("/restaurantes/por-nome-e-frete")
//    public List<Restaurante> restaurantePorNoe(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
//        return restauranteRepository.findComFreteGratis(nome);
//    }

//    @GetMapping("/restaurantes/primeiro")
//    public Optional<Restaurante> primeiroRestaurante(){
//        return restauranteRepository.buscarPrimeiro();
//    }

    @GetMapping("/restaurantes/primeiro")
    public List<Restaurante> buscarPorNome(@RequestParam String nome, @RequestParam Long id){
        return restauranteRepository.consultarPorNome(nome, id);
    }

    @GetMapping("/cozinhas/primeiro")
    public Optional<Cozinha> primeiraCozinha(){
        return cozinhaRepository.buscarPrimeiro();
    }
}

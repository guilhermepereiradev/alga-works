package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class InclusaoCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);

        Cozinha c1 = new Cozinha();
        c1.setNome("Brasileira");

        Cozinha c2 = new Cozinha();
        c2.setNome("Italiana");

        c1 = cadastroCozinha.salvar(c1);
        c2 = cadastroCozinha.salvar(c2);

        System.out.printf("%d - %s\n", c1.getId(), c1.getNome());
        System.out.printf("%d - %s\n", c2.getId(), c2.getNome());
    }

}

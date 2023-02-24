package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private int numeroCozinhas;

    private Cozinha cozinha;

    private int cozinhaIdInexistente;

    private String jsonCorretoCozinhaChinesa;


    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        preparaDados();

        jsonCorretoCozinhaChinesa = ResourceUtils.converteJsonParaString("/json/cozinha-chinesa.json");
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas(){
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConterQuantidadeTotalCozinhas_QuandoConsultarCozinhas(){
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(numeroCozinhas));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastraCozinha(){
        given()
                .body(jsonCorretoCozinhaChinesa)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRotarnarResposataEStatusCorretas_QuandoConsultarCozinhasExistentes(){
        given()
            .pathParams("id", cozinha.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(cozinha.getNome()));
    }

    @Test
    public void deveRotarnarStatus404_QuandoConsultarCozinhasInexistente(){
        given()
            .pathParams("id", cozinhaIdInexistente)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void preparaDados(){
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");

        cozinha = new Cozinha();
        cozinha.setNome("Brasileira");

        cozinhaRepository.saveAll(Arrays.asList(cozinha1, cozinha));

        numeroCozinhas = (int) cozinhaRepository.count();
        cozinhaIdInexistente = numeroCozinhas+1;
    }
}

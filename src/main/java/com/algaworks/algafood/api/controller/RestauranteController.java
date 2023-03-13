package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.CozinhaIdInput;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;


    @GetMapping
    public ResponseEntity<List<RestauranteModel>> listar(){
        return ResponseEntity.ok().body(restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteModel> buscar(@PathVariable Long id){
        return ResponseEntity.ok().body(restauranteModelAssembler.toModel(cadastroRestaurante.buscarOuFalhar(id)));
    }

    @PostMapping
    public ResponseEntity<RestauranteModel> adicionar(@RequestBody @Valid RestauranteInput restauranteInput){
        try{
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante)));
        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput){
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

        restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

        try{
            return ResponseEntity.ok().body(restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual)));
        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}/ativo")
    public ResponseEntity<Void>  ativar(@PathVariable Long id){
        cadastroRestaurante.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/ativo")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        cadastroRestaurante.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/abertura")
    public ResponseEntity<Void> abrir(@PathVariable Long id){
        cadastroRestaurante.abrir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/fechamento")
    public ResponseEntity<Void> fechar(@PathVariable Long id){
        cadastroRestaurante.fechar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> dadosOrigem, HttpServletRequest request){
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

        merge(dadosOrigem, restauranteAtual, request);

        validate(restauranteAtual, "restaurante");

        CozinhaIdInput cozinhaIdInput = new CozinhaIdInput();
        cozinhaIdInput.setId(restauranteAtual.getCozinha().getId());
        
        RestauranteInput restauranteInput = new RestauranteInput();
        restauranteInput.setNome(restauranteAtual.getNome());
        restauranteInput.setTaxaFrete(restauranteAtual.getTaxaFrete());
        restauranteInput.setCozinha(cozinhaIdInput);
        return atualizar(id, restauranteInput);
    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        }catch (IllegalArgumentException e){
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }
}

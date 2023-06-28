package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private CadastroPedidoService pedidoService;

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @GetMapping
    public ResponseEntity<MappingJacksonValue> listar(@RequestParam(required = false) String... campos){
        List<PedidoResumoModel> pedidoResumoModelList = pedidoResumoModelAssembler.toCollectionModel(pedidoService.listar());
        MappingJacksonValue pedidoWrapper = new MappingJacksonValue(pedidoResumoModelList);

        var simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

        if(campos != null){
            simpleFilterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos));
        }

        pedidoWrapper.setFilters(simpleFilterProvider);

        return ResponseEntity.ok().body(pedidoWrapper);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PedidoModel> buscar(@PathVariable String codigo){
        return ResponseEntity.ok().body(pedidoModelAssembler.toModel(pedidoService.buscarOuFalhar(codigo)));
    }

    @PostMapping
    public ResponseEntity<PedidoModel> emitir(@RequestBody @Valid PedidoInput pedidoInput){
        Pedido pedido = pedidoInputDisassembler.toDomainModel(pedidoInput);
        pedido.setCliente(cadastroUsuarioService.buscarOuFalhar(1L)); // usuário fixo até implementar autenticação

        try{
            PedidoModel pedidoModel = pedidoModelAssembler.toModel(emissaoPedidoService.emitirPedido(pedido));
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoModel);
        } catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage(), e);
        }

    }
}

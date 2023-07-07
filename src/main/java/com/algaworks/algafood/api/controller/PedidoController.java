package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoFilterInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoFilterInput;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private PedidoFilterInputDisassembler pedidoFilterInputDisassembler;

//    @GetMapping
//    public ResponseEntity<MappingJacksonValue> listar(@RequestParam(required = false) String... campos){
//        List<PedidoResumoModel> pedidoResumoModelList = pedidoResumoModelAssembler.toCollectionModel(pedidoService.listar());
//        MappingJacksonValue pedidoWrapper = new MappingJacksonValue(pedidoResumoModelList);
//
//        var simpleFilterProvider = new SimpleFilterProvider();
//        simpleFilterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(campos != null){
//            simpleFilterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos));
//        }
//
//        pedidoWrapper.setFilters(simpleFilterProvider);
//
//        return ResponseEntity.ok().body(pedidoWrapper);
//    }

    @GetMapping
    public ResponseEntity<Page<PedidoResumoModel>> listar(Pageable pageable, PedidoFilterInput pedidoFilterInput) {
        Specification<Pedido> pedidoSpec = PedidoSpecs.usandoFiltro(pedidoFilterInputDisassembler.toDomainObject(pedidoFilterInput));

        Page<Pedido> pedidosPage = pedidoService.listar(pedidoSpec, pageable);

        List<PedidoResumoModel> pedidoResumoModelList = pedidoResumoModelAssembler.toCollectionModel(pedidosPage.getContent());

        Page<PedidoResumoModel> pedidoResumoModelPage = new PageImpl<>(pedidoResumoModelList, pageable, pedidosPage.getTotalElements());

        return ResponseEntity.ok(pedidoResumoModelPage);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PedidoModel> buscar(@PathVariable String codigo){
        return ResponseEntity.ok(pedidoModelAssembler.toModel(pedidoService.buscarOuFalhar(codigo)));
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

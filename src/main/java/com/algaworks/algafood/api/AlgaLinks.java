package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM));

    public Link linkToPedidos() {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM));

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), "pedidos");
    }

    public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class)
                .confirmar(codigoPedido))
                .withRel(rel);
    }

    public Link linkToEntregarPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class)
                .entregar(codigoPedido))
                .withRel(rel);
    }

    public Link linkToCacelarPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class)
                .cancelar(codigoPedido))
                .withRel(rel);
    }
    public Link linkToRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteId)).withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteId) {
        return linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteId)).withRel(IanaLinkRelations.SELF);
    }

    public Link linkToUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .buscar(usuarioId)).withRel(rel);
    }

    public Link linkToUsuario(Long usuarioId) {
        return linkTo(methodOn(UsuarioController.class)
                .buscar(usuarioId)).withRel(IanaLinkRelations.SELF);
    }

    public Link linkToUsuarios(String rel) {
        return linkTo(UsuarioController.class)
                .withRel(rel);
    }

    public Link linkToCidade(Long cidadeId, String rel) {
        return linkTo(methodOn(CidadeController.class)
                .buscar(cidadeId)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkTo(methodOn(CidadeController.class)
                .buscar(cidadeId))
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToFomaPagamento(Long formaPagamentoId, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoId, null))
                .withRel(rel);
    }

    public Link linkToFomaPagamento(Long formaPagamentoId) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoId, null))
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(rel);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToCozinhas() {
        String cozinhaUrl = linkTo(CozinhaController.class).toUri().toString();

        return Link.of(UriTemplate.of(cozinhaUrl, PAGINACAO_VARIABLES), "cozinhas");
    }

    public Link linkToCidades(String rel) {
        return linkTo(CidadeController.class)
                .withRel(rel);
    }

    public Link linkToEstado(Long estadoId, String rel) {
        return linkTo(methodOn(EstadoController.class)
                .buscar(estadoId))
                .withRel(rel);
    }

    public Link linkToEstado(Long estadoId) {
        return linkTo(methodOn(EstadoController.class)
                .buscar(estadoId))
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToEstados(String rel) {
        return linkTo(EstadoController.class)
                .withRel(rel);
    }

    public Link linkToGrupoUsuarios(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuarioId))
                .withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteResponsavelController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId) {
        return linkTo(methodOn(RestauranteResponsavelController.class)
                .listar(restauranteId)).withSelfRel();
    }
}

package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.TemplateVariable.VariableType;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM));

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
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

    public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoId, null))
                .withRel(rel);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoId, null))
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToFormasPagamento(String rel) {
        return linkTo(FormaPagamentoController.class)
                .withRel(rel);
    }

    public Link linkToFormasPagamento() {
        return linkTo(FormaPagamentoController.class)
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToRestaurantesFormasPagamento(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .listar(restauranteId))
                .withRel(rel);
    }

    public Link linkToRestaurantesFormasPagamento(Long restauranteId) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .listar(restauranteId))
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

    public Link linkToCozinhas(String rel) {
        String cozinhaUrl = linkTo(CozinhaController.class).toUri().toString();

        return Link.of(UriTemplate.of(cozinhaUrl, PAGINACAO_VARIABLES), rel);
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

    public Link linkToRestaurantes(String rel) {
        String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();
        return Link.of(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);
    }

    public Link linkToRestaurantes() {
        String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();
        return Link.of(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), IanaLinkRelations.SELF);
    }

    public Link linkToCozinha(Long cozinhaId, String rel) {
        return linkTo(methodOn(CozinhaController.class)
                .buscar(cozinhaId))
                .withRel(rel);
    }

    public Link linkToCozinha(Long cozinhaId) {
        return linkTo(methodOn(CozinhaController.class)
                .buscar(cozinhaId))
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteResponsavelController.class)
                .listar(restauranteId))
                .withRel(rel);
    }

    public Link linkToAtivarRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .ativar(restauranteId))
                .withRel(rel);
    }

    public Link linkToInativarRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .inativar(restauranteId))
                .withRel(rel);
    }

    public Link linkToAbrirRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .abrir(restauranteId))
                .withRel(rel);
    }

    public  Link linkToFecharRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .fechar(restauranteId))
                .withRel(rel);
    }

    public Link linkToRestauranteFormasPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .desassociarFormaPagamento(restauranteId, formaPagamentoId))
                .withRel(rel);
    }

    public Link linkToRestauranteFormasPagamentoAssociacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .associarFormaPagamento(restauranteId, null))
                .withRel(rel);
    }

    public Link linkToRestauranteResponsaveisDessassociar(Long restauranteId, Long usuarioId, String rel) {
        return linkTo(methodOn(RestauranteResponsavelController.class)
                .desassociar(restauranteId, usuarioId)).withRel(rel);
    }

    public Link linkToRestauranteResponsaveisAssociar(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteResponsavelController.class)
                .associar(restauranteId, null)).withRel(rel);
    }

    public Link linkToRestauranteProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(rel);
    }

    public Link linkToRestauranteProduto(Long restauranteId, Long produtoId) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToRestauranteProdutos(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .listar(restauranteId, null))
                .withRel(rel);
    }

    public Link linkToRestauranteProdutos(Long restauranteId) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .listar(restauranteId, null))
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToRestauranteProdutoFoto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoFotoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(rel);
    }

    public Link linkToRestauranteProdutoFoto(Long restauranteId, Long produtoId) {
        return linkTo(methodOn(RestauranteProdutoFotoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToGrupo(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoController.class)
                .buscar(grupoId))
                .withRel(rel);
    }

    public Link linkToGrupo(Long grupoId) {
        return linkTo(methodOn(GrupoController.class)
                .buscar(grupoId))
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToGrupos(String rel) {
        return linkTo(GrupoController.class)
                .withRel(rel);
    }

    public Link linkToGrupos() {
        return linkTo(GrupoController.class)
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToGruposPermissoes(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .listar(grupoId))
                .withRel(rel);
    }

    public Link linkToGruposPermissoes(Long grupoId) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .listar(grupoId))
                .withRel(IanaLinkRelations.SELF);
    }

    public Link linkToPermissoes() {
        return linkTo(PermissaoController.class).withRel(IanaLinkRelations.SELF);
    }

    public Link linkToPermissoes(String rel) {
        return linkTo(PermissaoController.class).withRel(rel);
    }

    public Link linkToGruposPermissoesDesassociar(Long grupoId, Long permissaoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .desassociar(grupoId, permissaoId))
                .withRel(rel);
    }

    public Link linkToGruposPermissoesAssociar(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .associar(grupoId, null))
                .withRel(rel);
    }

    public Link linkToUsuariosGruposDesassociar(Long usuarioId, Long grupoId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .desassociar(usuarioId, grupoId))
                .withRel(rel);
    }

    public Link linkToUsuariosGruposAssociar(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .associar(usuarioId, null))
                .withRel(rel);
    }
}

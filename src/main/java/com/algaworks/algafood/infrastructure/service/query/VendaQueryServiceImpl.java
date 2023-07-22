package com.algaworks.algafood.infrastructure.service.query;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.model.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var predicates = new ArrayList<>();

        var functionConvertTzDataCriacao = builder.function("convert_tz", Date.class, root.get("dataCriacao"),
                builder.literal("+00:00"), builder.literal(timeOffset));

        var functionDateDataCriacao = builder.function("date", Date.class, functionConvertTzDataCriacao);

        var selection = builder.construct(VendaDiaria.class, //
                functionDateDataCriacao, //
                builder.count(root.get("id")), //
                builder.sum(root.get("valorTotal"))
        );

        if (filtro.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
        }

        if (filtro.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
        }

        if (filtro.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
        }

        List<StatusPedido> statusPedidos = Arrays.asList(StatusPedido.ENTREGUE, StatusPedido.CONFIRMADO);
        predicates.add(builder.in(root.get("status")).value(statusPedidos));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);

        return entityManager.createQuery(query).getResultList();
    }
}

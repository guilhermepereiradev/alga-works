package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.filter.VendaDiariaFilter;
import org.springframework.stereotype.Service;

@Service
public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}

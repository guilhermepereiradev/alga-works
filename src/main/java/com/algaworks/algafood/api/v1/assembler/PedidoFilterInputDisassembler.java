package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.input.PedidoFilterInput;
import com.algaworks.algafood.domain.model.filter.PedidoFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoFilterInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoFilter toDomainObject(PedidoFilterInput pedidoFilterInput) {
        return modelMapper.map(pedidoFilterInput, PedidoFilter.class);
    }

    public void copyToDomainObject(PedidoFilterInput pedidoFilterInput, PedidoFilter pedidoFilter) {
        modelMapper.map(pedidoFilterInput, pedidoFilter);
    }
}

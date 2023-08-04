package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CadastroFormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository repository;

    public List<FormaPagamento> listar() {
        return repository.findAll();
    }

    public FormaPagamento buscarOuFalhar(Long id) {
        return repository.findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return repository.save(formaPagamento);
    }

    @Transactional
    public void deletar(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new NegocioException(String.format("Forma de pagamento de código %d não pode ser removida por estar em uso", id), e);
        }
    }

    public OffsetDateTime getUltimaDataAtualizacao() {
        return repository.getDataUltimaAtualizacao();
    }

    public OffsetDateTime getDataAtulizacao(Long id) {
        return repository.getdDataAtualizacao(id);
    }
}

package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

import static com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoExistente = repository.findFotoById(restauranteId, produtoId);

        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            repository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(nomeNovoArquivo);
        foto = repository.save(foto);
        repository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(nomeNovoArquivo)
                .inputStream(dadosArquivo)
                .build();

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return repository.findFotoById(restauranteId, produtoId).orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public void remover(Long restauranteId, Long produtoId) {
        FotoProduto foto = buscarOuFalhar(restauranteId, produtoId);

        repository.delete(foto);
        fotoStorageService.remover(foto.getNomeArquivo());
    }
}

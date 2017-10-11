package br.jus.trf1.bot;

import java.util.List;

public interface AtualizacaoService {
    List<Atualizacao> obterAtualizacoes(final String processo);
}

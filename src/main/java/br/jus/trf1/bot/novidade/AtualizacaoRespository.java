package br.jus.trf1.bot.novidade;

import java.util.List;

public interface AtualizacaoRespository {
    List<Atualizacao> obterAtualizacoes(final String processo);
}

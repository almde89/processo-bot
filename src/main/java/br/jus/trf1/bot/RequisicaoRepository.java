package br.jus.trf1.bot;

import br.jus.trf1.Processo;

import java.util.List;

public interface RequisicaoRepository {
    List<Requisicao> obter(final Long chatId);

    Requisicao registrar(Requisicao requisicao);

    Requisicao obter(final String identificador, final Long chatId);

    List<Processo> obterProcessos(Long chatId);
}

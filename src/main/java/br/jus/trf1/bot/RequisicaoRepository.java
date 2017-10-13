package br.jus.trf1.bot;

import java.util.List;

public interface RequisicaoRepository {
    List<Requisicao> obter(final Long chatId);

    Requisicao registrar(Requisicao requisicao);

    Requisicao obter(final String identificador, final Long chatId);
}

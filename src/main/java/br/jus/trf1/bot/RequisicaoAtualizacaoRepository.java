package br.jus.trf1.bot;

import java.util.List;

public interface RequisicaoAtualizacaoRepository {
    List<RequisicaoAtualizacao> obter(final RequisicaoAtualizacaoSpecification specification);

    RequisicaoAtualizacao registrar(final String idUsuario, final String chatId, final String messageId, final String identificador);

    RequisicaoAtualizacao obter(final String identificador);
}

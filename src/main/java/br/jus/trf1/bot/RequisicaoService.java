package br.jus.trf1.bot;

import br.jus.trf1.Processo;
import br.jus.trf1.bot.novidade.Atualizacao;

import java.util.List;

public interface RequisicaoService {
    void responder(Requisicao requisicao, List<Processo> processos);
    void responder(Requisicao requisicao, Atualizacao atualizacao);
    void responder(Requisicao requisicao, String mensagem);
}

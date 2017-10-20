package br.jus.trf1.bot;

import br.jus.trf1.Processo;
import br.jus.trf1.bot.novidade.Atualizacao;

import java.util.List;

public interface RespostaService<T>  {
    T responder(Requisicao requisicao, List<Processo> processos);
    T responder(Requisicao requisicao, Atualizacao atualizacao);
    T responder(Requisicao requisicao, String mensagem);
}

package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;

public interface RespostaStrategy {

    public abstract <T> T execute(Requisicao requisicao);
}

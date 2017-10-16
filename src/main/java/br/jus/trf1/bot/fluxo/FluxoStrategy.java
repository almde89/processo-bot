package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;

public interface FluxoStrategy {

    public abstract void execute(Requisicao requisicao);
}

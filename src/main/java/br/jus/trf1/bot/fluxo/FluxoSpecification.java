package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;

public interface FluxoSpecification {

    Boolean satisfaz(Requisicao requisicao);
    FluxoStrategy getFluxoStrategy();
}

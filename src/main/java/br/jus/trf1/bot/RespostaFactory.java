package br.jus.trf1.bot;

import br.jus.trf1.bot.fluxo.RespostaSpecification;

public interface RespostaFactory {
    Resposta instance(final Requisicao requisicao, final RespostaSpecification respostaSpecification);
}

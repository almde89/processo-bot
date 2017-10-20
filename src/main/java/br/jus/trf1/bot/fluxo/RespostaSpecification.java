package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;

public interface RespostaSpecification {

    Boolean satisfesteiPor(Requisicao requisicao);
    RespostaStrategy getRespostaStrategy();
}

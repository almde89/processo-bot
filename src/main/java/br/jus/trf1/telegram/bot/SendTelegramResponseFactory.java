package br.jus.trf1.telegram.bot;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.Resposta;
import br.jus.trf1.bot.RespostaFactory;
import br.jus.trf1.bot.fluxo.RespostaSpecification;

public class SendTelegramResponseFactory implements RespostaFactory {

    @Override
    public Resposta instance(Requisicao requisicao, RespostaSpecification respostaSpecification) {
        return new Resposta.Builder().respostaStrategy(respostaSpecification.getRespostaStrategy())
                .requisicao(requisicao)
                .build();
    }
}

package br.jus.trf1.bot;

import br.jus.trf1.bot.fluxo.RespostaStrategy;

public class Resposta {
    private Requisicao mRequisicao;
    private RespostaStrategy mRespostaStrategy;

    Resposta() {
    }

    public Resposta(Requisicao requisicao) {
        mRequisicao = requisicao;
    }

    public <T> T enviar() {
        return (T) mRespostaStrategy.execute(mRequisicao);
    }

    public static class Builder {

        private Requisicao requisicao;
        private RespostaStrategy respostaStrategy;


        public Builder requisicao(final Requisicao requisicaoOriginaria) {
            requisicao = requisicaoOriginaria;
            return this;
        }

        public Builder respostaStrategy(final RespostaStrategy strategy) {
            respostaStrategy = strategy;
            return this;
        }

        public Resposta build() {
            final Resposta instance = new Resposta();
            instance.mRequisicao = requisicao;
            instance.mRespostaStrategy = respostaStrategy;
            return instance;
        }
    }
}

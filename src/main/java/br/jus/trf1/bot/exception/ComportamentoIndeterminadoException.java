package br.jus.trf1.bot.exception;

import br.jus.trf1.bot.Requisicao;

public class ComportamentoIndeterminadoException extends RuntimeException {
    private Requisicao mRequisicao;

    public ComportamentoIndeterminadoException(String message) {
        super(message);
    }

    public ComportamentoIndeterminadoException requisicao(final Requisicao requisicao) {
        mRequisicao = requisicao;

        return this;
    }

    public Requisicao getRequisica() {
        return mRequisicao;
    }

}

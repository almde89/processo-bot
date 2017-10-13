package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;
import br.jus.trf1.telegram.bot.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;

public abstract class Fluxo {

    public Fluxo (final Requisicao requisicao) {
        mRequisicao = requisicao;
    }

    public abstract void execute(AtualizacaoRespository atualizacaoRespository, TelegramBot telegramBot
            , RequisicaoRepository requisicaoRepository
            , SendMessageFactory sendMessageFactory);

    private Requisicao mRequisicao;

    public Requisicao getNovaRequisicao() {
        return mRequisicao;
    }
}

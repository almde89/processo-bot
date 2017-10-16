package br.jus.trf1.telegram.bot;

import br.jus.trf1.Processo;
import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoService;
import br.jus.trf1.bot.novidade.Atualizacao;
import com.pengrad.telegrambot.TelegramBot;

import java.util.List;

public class SendTelegramService implements RequisicaoService {
    @Override
    public void responder(Requisicao requisicao, List<Processo> processos) {
        mTelegramBot.execute(mSendMessageFactory.instance(requisicao.getChatId(), processos));
    }

    @Override
    public void responder(Requisicao requisicao, Atualizacao atualizacao) {
        mTelegramBot.execute(mSendMessageFactory.instance(requisicao.getChatId()
                , requisicao.getMessageId(), atualizacao.getMensagemHTML()));
    }

    @Override
    public void responder(Requisicao requisicao, String mensagem) {
        mTelegramBot.execute(mSendMessageFactory.instance(requisicao.getChatId()
                , requisicao.getMessageId(), mensagem));
    }

    public SendTelegramService (final SendMessageFactory sendMessageFactory, final TelegramBot telegramBot) {
        mSendMessageFactory = sendMessageFactory;
        mTelegramBot = telegramBot;
    }

    private volatile SendMessageFactory mSendMessageFactory;
    private volatile TelegramBot mTelegramBot;
}

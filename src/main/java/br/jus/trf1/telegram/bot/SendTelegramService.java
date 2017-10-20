package br.jus.trf1.telegram.bot;

import br.jus.trf1.Processo;
import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RespostaService;
import br.jus.trf1.bot.novidade.Atualizacao;
import com.pengrad.telegrambot.TelegramBot;

import javax.lang.model.type.NullType;
import java.util.List;

public class SendTelegramService implements RespostaService<NullType> {
    @Override
    public NullType responder(Requisicao requisicao, List<Processo> processos) {
        mTelegramBot.execute(mSendMessageFactory.instance(requisicao.getChatId(), processos));
        return null;
    }

    @Override
    public NullType responder(Requisicao requisicao, Atualizacao atualizacao) {
        mTelegramBot.execute(mSendMessageFactory.instance(requisicao.getChatId()
                , requisicao.getMessageId().intValue(), atualizacao.getMensagemHTML()));
        return null;
    }

    @Override
    public NullType responder(Requisicao requisicao, String mensagem) {
        mTelegramBot.execute(mSendMessageFactory.instance(requisicao.getChatId()
                , requisicao.getMessageId().intValue(), mensagem));
        return null;
    }

    public SendTelegramService (final SendMessageFactory sendMessageFactory, final TelegramBot telegramBot) {
        mSendMessageFactory = sendMessageFactory;
        mTelegramBot = telegramBot;
    }

    private volatile SendMessageFactory mSendMessageFactory;
    private volatile TelegramBot mTelegramBot;
}

package br.jus.trf1.telegram.bot;

import br.jus.trf1.Processo;
import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RespostaService;
import br.jus.trf1.bot.novidade.Atualizacao;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public class AsyncTelegramRespostaService implements RespostaService<SendMessage> {
    private final SendMessageFactory mSendMessageFactory;

    @Override
    public SendMessage responder(Requisicao requisicao, List<Processo> processos) {
        return mSendMessageFactory.instance(requisicao.getChatId(), processos);
    }

    @Override
    public SendMessage responder(Requisicao requisicao, Atualizacao atualizacao) {
        return mSendMessageFactory.instance(requisicao.getChatId()
                , requisicao.getMessageId().intValue(), atualizacao.getMensagemHTML());
    }

    @Override
    public SendMessage responder(Requisicao requisicao, String mensagem) {
        return mSendMessageFactory.instance(requisicao.getChatId()
                , requisicao.getMessageId().intValue(), mensagem);
    }

    public AsyncTelegramRespostaService (final SendMessageFactory sendMessageFactory) {
        mSendMessageFactory = sendMessageFactory;
    }
}

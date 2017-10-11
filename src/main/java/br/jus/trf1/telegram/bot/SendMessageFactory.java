package br.jus.trf1.telegram.bot;

import br.jus.trf1.bot.RequisicaoAtualizacao;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public interface SendMessageFactory {
    SendMessage instance(final Long chatId, final Integer messageId, final String mensagem);

    SendMessage instance(final Long chatId, final List<RequisicaoAtualizacao> requisicoes);
}

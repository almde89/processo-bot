package br.jus.trf1.telegram.bot;

import br.jus.trf1.Processo;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public class SendMessageFactoryImpl implements SendMessageFactory {
    @Override
    public SendMessage instance(Long chatId, Integer messageId, String mensagem) {
        final SendMessage request = new SendMessage(chatId, mensagem)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true)
                .replyToMessageId(messageId)
                .replyMarkup(new ForceReply());
        return request;
    }

    @Override
    public SendMessage instance(Long chatId, List<Processo> processos) {
        final String[][] botoes = new String[processos.size()][1];
        int indeXMatrizBotoes = 0;

        for (final Processo requisicao : processos){
            botoes[indeXMatrizBotoes++][0] = requisicao.getProcessoNumero();
        }

        return new SendMessage(chatId, "Selecione um processo para ver sobre as novidades:")
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true)
                .replyMarkup(new ReplyKeyboardMarkup(botoes).oneTimeKeyboard(true)
                .resizeKeyboard(true).selective(true));
    }

    @Override
    public SendMessage instance(Long chatId, Integer messageId) {
        return instance(chatId, messageId, "Você tem nenhum processo em sua lista.");
    }
}

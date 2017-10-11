package br.jus.trf1.telegram.bot;

import br.jus.trf1.bot.RequisicaoAtualizacao;
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
    public SendMessage instance(Long chatId, List<RequisicaoAtualizacao> requisicoes) {
        final String[][] botoes = new String[requisicoes.size()][1];
        int indeXMatrizBotoes = 0;

        for (final RequisicaoAtualizacao requisicaoAtualizacao :requisicoes){
            botoes[indeXMatrizBotoes++][0] = requisicaoAtualizacao.getProcesso();
        }

        return new SendMessage(chatId, "Processos:")
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true)
                .replyMarkup(new ReplyKeyboardMarkup(botoes).oneTimeKeyboard(true)
                .resizeKeyboard(true).selective(true));
    }
}

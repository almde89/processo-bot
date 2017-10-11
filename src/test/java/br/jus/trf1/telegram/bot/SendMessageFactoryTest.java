package br.jus.trf1.telegram.bot;

import br.jus.trf1.bot.RequisicaoAtualizacao;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class SendMessageFactoryTest {

    @Test
    public void deveConfigurarSendMessageComInfoDeMessageIdChatIdEMensagemQuandoCriarInstancia() {
        final SendMessageFactory factory = new SendMessageFactoryImpl();
        final SendMessage request;

        request = factory.instance(new Long(21), new Integer(123), "Mensagem");

        assertNotNull("Chat id deve ser configurado", request.getParameters().get("chat_id"));
        assertEquals("Chat id deve ter valor igual ao passado por parâmetro"
                , new Long(21), request.getParameters().get("chat_id"));

        assertNotNull("Message id deve ser configurado", request.getParameters().get("reply_to_message_id"));
        assertEquals("Message id deve ter valor igual ao passado por parâmetro"
                , new Integer(123), request.getParameters().get("reply_to_message_id"));

        assertNotNull("Mensagem não deve ser nula", request.getParameters().get("text"));
        assertEquals("Mensagem deve ter valor igual ao passado por parâmetro"
                , "Mensagem", request.getParameters().get("text"));
    }

    @Test
    public void deveConfigurarMensagemComoHTMLQuandoCriarInstancia() {
        final SendMessageFactory factory = new SendMessageFactoryImpl();
        final SendMessage request;

        request = factory.instance(new Long(21), new Integer(123), "Mensagem");

        assertNotNull("A formatação deve estar configurada", request.getParameters().get("parse_mode"));
        assertEquals("A formatação deve estar configurada como html", ParseMode.HTML.toString()
                , request.getParameters().get("parse_mode"));
    }

    @Test
    public void deveCriarKeyboardsParaProcessosQuandoCriarInstanciaComRequisicaoAtualizacao() {
        final SendMessageFactoryImpl factory = new SendMessageFactoryImpl();
        final SendMessage request;
        final ArrayList<RequisicaoAtualizacao> requisicoes = new ArrayList<>();
        requisicoes.add(new RequisicaoAtualizacao(""));
        requisicoes.add(new RequisicaoAtualizacao(""));
        requisicoes.add(new RequisicaoAtualizacao(""));

        request = factory.instance(new Long(21), requisicoes);

        assertNotNull("Request deve ser diferente de null", request);
        assertEquals("Requisições devem ser respondidas com ReplyKeyboardMarkup"
                , ReplyKeyboardMarkup.class, request.getParameters().get("reply_markup").getClass());
        assertNotNull(request.getParameters().get("text"));
        assertFalse(request.getParameters().get("text").toString().isEmpty());
    }

}

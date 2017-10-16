package br.jus.trf1.telegram.bot;

import br.jus.trf1.Processo;
import br.jus.trf1.bot.Requisicao;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

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
        final ArrayList<Processo> processos = new ArrayList<>();
        final Message messageMock = mock(Message.class);
        final Chat chatMock = mock(Chat.class);
        doReturn(chatMock).when(messageMock).chat();
        doReturn(new Long(1)).when(chatMock).id();
        doReturn("1000438-56.2017.4.01.3400").when(messageMock).text();
        processos.add(new Processo("1000438-56.2017.4.01.3400"));
        processos.add(new Processo("1000438-56.2017.4.01.3400"));
        processos.add(new Processo("1000438-56.2017.4.01.3400"));

        request = factory.instance(new Long(21), processos);

        assertNotNull("Request deve ser diferente de null", request);
        assertEquals("Requisições devem ser respondidas com ReplyKeyboardMarkup"
                , ReplyKeyboardMarkup.class, request.getParameters().get("reply_markup").getClass());
        assertNotNull(request.getParameters().get("text"));
        assertFalse(request.getParameters().get("text").toString().isEmpty());
    }

}

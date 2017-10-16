package br.jus.trf1.bot;

import br.jus.trf1.bot.fluxo.FluxoSpecification;
import br.jus.trf1.bot.fluxo.ListarProcessosStrategy;
import br.jus.trf1.bot.fluxo.ProcessoNovoStrategy;
import br.jus.trf1.bot.fluxo.ProcessoSelecionadoStrategy;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RequisicaoTest {

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExcecaoParaNumeroProcessoInvalido() {
        final Message messageMock = mock(Message.class);
        doReturn("/novidade asd").when(messageMock).text();
        final Chat chatMock = mock(Chat.class);
        doReturn(chatMock).when(messageMock).chat();
        doReturn(new Long(1)).when(chatMock).id();

        new Requisicao(messageMock);
    }

    private ArrayList<FluxoSpecification> specsDeFluxo = new ArrayList<FluxoSpecification>();

}
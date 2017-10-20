package br.jus.trf1.bot;

import br.jus.trf1.bot.fluxo.RespostaSpecification;
import br.jus.trf1.bot.fluxo.RespostaStrategy;
import br.jus.trf1.bot.novidade.Atualizacao;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

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

    @Test
    public void deveCriarRespostarQuandoResponder() {
        final RespostaSpecification specification = mock(RespostaSpecification.class);
        final RespostaFactory respostaFactory = mock(RespostaFactory.class);
        final Requisicao requisicao = new Requisicao.Builder()
                .respostaFactory(respostaFactory)
                .build();
        final Resposta respostaFromFactory = new Resposta(requisicao);
        final Resposta resposta;

        doReturn(respostaFromFactory).when(respostaFactory).instance(requisicao, specification);

        resposta = requisicao.responder(specification);

        assertNotNull("Toda atualizacao deve ter uma repsosta", resposta);
    }

}
package br.jus.trf1.bot;

import br.jus.trf1.bot.fluxo.FluxoSpecification;
import br.jus.trf1.bot.fluxo.MostrarTecladoFluxo;
import br.jus.trf1.bot.fluxo.ProcessoNovoFluxo;
import br.jus.trf1.bot.fluxo.ProcessoEscolhidoTecladoFluxo;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RequisicaoTest {

    @Test
    public void deveFluxoQuandoCriarRequisicao() {
        final Requisicao requisicaoProcessoNovoEspecifico;
        final Requisicao requisicaoProcessoEspecificoJaRegistrado;
        final Requisicao requisicoListaProcessosRegistrados;
        final Message messageProcessoNovoMock = mock(Message.class);
        final Message messageProcessoJaResgistradoMock = mock(Message.class);
        final Message messageListaMock = mock(Message.class);
        final Chat chatMock = mock(Chat.class);
        doReturn("/novidade 1000438-56.2017.4.01.3400").when(messageProcessoNovoMock).text();
        doReturn("1000438-56.2017.4.01.3400").when(messageProcessoJaResgistradoMock).text();
        doReturn("/novidade").when(messageListaMock).text();
        doReturn(new Long(1)).when(chatMock).id();
        doReturn(chatMock).when(messageListaMock).chat();
        doReturn(chatMock).when(messageProcessoJaResgistradoMock).chat();
        doReturn(chatMock).when(messageProcessoNovoMock).chat();

        requisicaoProcessoNovoEspecifico = new Requisicao(messageProcessoNovoMock);
        requisicaoProcessoEspecificoJaRegistrado = new Requisicao(messageProcessoJaResgistradoMock);
        requisicoListaProcessosRegistrados = new Requisicao(messageListaMock);

        assertEquals("O fluxo deve ser do tipo ProcessoNovoFluxo", ProcessoNovoFluxo.class
                , requisicaoProcessoNovoEspecifico.getFluxo().getClass());
        assertEquals("O fluxo deve ser do tipo ProcessoEscolhidoTecladoFluxo", ProcessoEscolhidoTecladoFluxo.class
                , requisicaoProcessoEspecificoJaRegistrado.getFluxo().getClass());
        assertEquals("O fluxo deve ser do tipo MostrarTecladoFluxo", MostrarTecladoFluxo.class
                , requisicoListaProcessosRegistrados.getFluxo().getClass());
    }

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
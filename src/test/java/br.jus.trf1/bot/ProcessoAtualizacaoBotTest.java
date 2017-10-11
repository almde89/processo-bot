package br.jus.trf1.bot;

import br.jus.trf1.telegram.bot.SendMessageFactory;
import br.jus.trf1.telegram.bot.SendMessageFactoryImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ProcessoAtualizacaoBotTest {

    @Test
    public void deveRegistrarRequisicaoAtualizacaoQuandoBarraInfoProcesso() {
        final RequisicaoAtualizacaoRepository requisicaoAtualizacaoRepoMock =
                mock(RequisicaoAtualizacaoRepository.class);
        final AtualizacaoService atualizacaoServiceMock = mock(AtualizacaoService.class);
        final ProcessoAtualizacaoBot bot = new ProcessoAtualizacaoBot();
        final Update updateMock = mock(Update.class);
        final SendMessageFactory sendMessageFactoryMock = mock(SendMessageFactory.class);
        final Message messageMock = mock(Message.class);
        final User userMock = mock(User.class);
        final Chat chatMock = mock(Chat.class);
        bot.setAtualizacaoService(atualizacaoServiceMock);
        bot.setSendMessageFactory(sendMessageFactoryMock);
        bot.setRequisicaoAtualizacaoRepository(requisicaoAtualizacaoRepoMock);
        doReturn(messageMock).when(updateMock).message();
        doReturn("/info processo").when(messageMock).text();
        doReturn(new Integer(123)).when(messageMock).messageId();
        doReturn(userMock).when(messageMock).from();
        doReturn(new Integer(312)).when(userMock).id();
        doReturn(chatMock).when(messageMock).chat();
        doReturn(new Long(231)).when(chatMock).id();
        doReturn(new ArrayList<Atualizacao>(0)).when(atualizacaoServiceMock)
                .obterAtualizacoes(Matchers.anyString());

        bot.responder(updateMock, new ProcessoChatUsuarioSpecification());

        verify(updateMock.message()).text();
        verify(requisicaoAtualizacaoRepoMock).registrar(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
        verify(requisicaoAtualizacaoRepoMock).registrar((String) Matchers.isNotNull(), (String) Matchers.isNotNull(), (String) Matchers.isNotNull()
                , (String) Matchers.isNotNull());
        verify(requisicaoAtualizacaoRepoMock).registrar(Matchers.eq("312")
                , Matchers.eq("231")
                , Matchers.eq("123")
                , Matchers.eq("processo"));
    }

    @Test
    public void deveRegistrarApenasQuandoNaoRegistrado() {
        final ArrayList<RequisicaoAtualizacao> retornoObter = new ArrayList<>();
        final TelegramBot telegramBotMock = mock(TelegramBot.class);
        final AtualizacaoService atualizacaoServiceMock = mock(AtualizacaoService.class);
        final RequisicaoAtualizacaoRepository requisicaoAtualizacaoRepoMock = mock(RequisicaoAtualizacaoRepository.class);
        final ProcessoAtualizacaoBot bot = new ProcessoAtualizacaoBot();
        final SendMessageFactory sendMessageFactoryMock = mock(SendMessageFactory.class);
        final Update updateMock = mock(Update.class);
        final Message messageMock = mock(Message.class);
        final User userMock = mock(User.class);
        final Chat chatMock = mock(Chat.class);
        bot.setRequisicaoAtualizacaoRepository(requisicaoAtualizacaoRepoMock);
        bot.setTelegramBot(telegramBotMock);
        bot.setAtualizacaoService(atualizacaoServiceMock);
        bot.setSendMessageFactory(sendMessageFactoryMock);
        retornoObter.add(new RequisicaoAtualizacao(""));
        doReturn(retornoObter).when(requisicaoAtualizacaoRepoMock).obter(Matchers.any(RequisicaoAtualizacaoSpecification.class));
        doReturn(messageMock).when(updateMock).message();
        doReturn("/info processo").when(messageMock).text();
        doReturn(new Integer(123)).when(messageMock).messageId();
        doReturn(userMock).when(messageMock).from();
        doReturn(new Integer(312)).when(userMock).id();
        doReturn(chatMock).when(messageMock).chat();
        doReturn(new Long(231)).when(chatMock).id();
        doReturn(new ArrayList<Atualizacao>(0)).when(atualizacaoServiceMock).obterAtualizacoes(Matchers.anyString());

        bot.responder(updateMock, new ProcessoChatUsuarioSpecification());

        verify(requisicaoAtualizacaoRepoMock, times(0)).registrar(Matchers.anyString(), Matchers.anyString()
                , Matchers.anyString(), Matchers.anyString());
    }

    @Test
    public void deveEnviarMensagemDeAtualizacaoProcessoRecemRegistradoQuandoAcabarDeRegistrar() {
        final AtualizacaoService atualizacaoService = mock(AtualizacaoService.class);
        final SendMessageFactory sendMessageFactoryMock = mock(SendMessageFactory.class);
        final SendMessage sendMessageMock = mock(SendMessage.class);
        final TelegramBot botMock = mock(TelegramBot.class);
        final Atualizacao atualizacaoMock = mock(Atualizacao.class);
        final ArrayList<Atualizacao> atualizacoesDoServico = new ArrayList<>();
        final ArrayList<String> paramObterArtualizacoes = new ArrayList<>();
        final ArrayList<RequisicaoAtualizacao> retornoObter = new ArrayList<>();
        final RequisicaoAtualizacaoRepository requisicaoAtualizacaoRepoMock = mock(RequisicaoAtualizacaoRepository.class);
        final ProcessoAtualizacaoBot bot = new ProcessoAtualizacaoBot();
        final Update updateMock = mock(Update.class);
        final Message messageMock = mock(Message.class);
        final User userMock = mock(User.class);
        final Chat chatMock = mock(Chat.class);
        final RequisicaoAtualizacaoSpecification specificationMock = mock(RequisicaoAtualizacaoSpecification.class);
        atualizacoesDoServico.add(atualizacaoMock);
        paramObterArtualizacoes.add("processo");
        bot.setRequisicaoAtualizacaoRepository(requisicaoAtualizacaoRepoMock);
        bot.setSendMessageFactory(sendMessageFactoryMock);
        bot.setTelegramBot(botMock);
        bot.setAtualizacaoService(atualizacaoService);
        doReturn(retornoObter).when(requisicaoAtualizacaoRepoMock).obter(Matchers.any(RequisicaoAtualizacaoSpecification.class));
        doReturn(messageMock).when(updateMock).message();
        doReturn("/info processo").when(messageMock).text();
        doReturn(new Integer(123)).when(messageMock).messageId();
        doReturn(userMock).when(messageMock).from();
        doReturn(new Integer(312)).when(userMock).id();
        doReturn(chatMock).when(messageMock).chat();
        doReturn(new Long(231)).when(chatMock).id();
        doReturn(atualizacoesDoServico).when(atualizacaoService).obterAtualizacoes(Matchers.eq("processo"));
        doReturn("Atualização em <b>html</b>").when(atualizacaoMock).getMensagemHTML();
        doReturn(sendMessageMock).when(sendMessageFactoryMock)
                .instance(eq(new Long(231)), eq(new Integer(123)), eq("Atualização em <b>html</b>"));

        bot.responder(updateMock, specificationMock);

        verify(atualizacaoService).obterAtualizacoes(Matchers.eq("processo"));
        verify(sendMessageFactoryMock)
                .instance(eq(new Long(231)), eq(new Integer(123)), eq("Atualização em <b>html</b>"));
        verify(botMock).execute(sendMessageMock);
    }

    @Test
    public void deveEnviarTodasAtualizacoesQuandoResponderBarraInfo() {
        final AtualizacaoService atualizacaoServiceMock = mock(AtualizacaoService.class);
        final SendMessageFactory sendMessageFactoryMock = mock(SendMessageFactory.class);
        final SendMessage sendMessageMock = mock(SendMessage.class);
        final ProcessoAtualizacaoBot bot = new ProcessoAtualizacaoBot();
        final TelegramBot botMock = mock(TelegramBot.class);
        final Update updateMock = mock(Update.class);
        final RequisicaoAtualizacaoSpecification specMock = mock(RequisicaoAtualizacaoSpecification.class);
        final RequisicaoAtualizacaoRepository requisicaoAtualizacaoRepoMock = mock(RequisicaoAtualizacaoRepository.class);
        final ArrayList<Atualizacao> atualizacoesDoServico = new ArrayList<>();
        final Atualizacao atualizacaoMock1 = mock(Atualizacao.class);
        final Atualizacao atualizacaoMock2 = mock(Atualizacao.class);
        final Message messageMock = mock(Message.class);
        final User userMock = mock(User.class);
        final Chat chatMock = mock(Chat.class);
        final ArrayList<RequisicaoAtualizacao> retornoObter = new ArrayList<>();
        atualizacoesDoServico.add(atualizacaoMock1);
        atualizacoesDoServico.add(atualizacaoMock2);
        bot.setRequisicaoAtualizacaoRepository(requisicaoAtualizacaoRepoMock);
        bot.setAtualizacaoService(atualizacaoServiceMock);
        bot.setSendMessageFactory(sendMessageFactoryMock);
        bot.setTelegramBot(botMock);
        doReturn("/info processo").when(messageMock).text();
        doReturn(retornoObter).when(requisicaoAtualizacaoRepoMock).obter(Matchers.any(RequisicaoAtualizacaoSpecification.class));
        doReturn(atualizacoesDoServico).when(atualizacaoServiceMock).obterAtualizacoes(Matchers.eq("processo"));
        doReturn(messageMock).when(updateMock).message();
        doReturn(new Integer(123)).when(messageMock).messageId();
        doReturn(userMock).when(messageMock).from();
        doReturn(new Integer(312)).when(userMock).id();
        doReturn(chatMock).when(messageMock).chat();
        doReturn(new Long(231)).when(chatMock).id();
        doReturn(sendMessageMock).when(sendMessageFactoryMock)
                .instance(eq(new Long(231)), eq(new Integer(123)), Matchers.eq("Atualização em <b>html</b>"));
        doReturn("Atualização em <b>html</b>").when(atualizacaoMock1).getMensagemHTML();
        doReturn("Atualização em <b>html</b>").when(atualizacaoMock2).getMensagemHTML();

        bot.responder(updateMock, specMock);

        verify(botMock, times(2)).execute(sendMessageMock);
    }

    @Test
    public void deveEnviarAtualizacaoAoChatEMensagemQuandoResponderBarraInfo() {
        final AtualizacaoService atualizacaoServiceMock = mock(AtualizacaoService.class);
        final SendMessageFactory sendMessageFactoryMock = mock(SendMessageFactory.class);
        final SendMessage sendMessageMock = mock(SendMessage.class);
        final ProcessoAtualizacaoBot bot = new ProcessoAtualizacaoBot();
        final TelegramBot botMock = mock(TelegramBot.class);
        final Update updateMock = mock(Update.class);
        final RequisicaoAtualizacaoSpecification specMock = mock(RequisicaoAtualizacaoSpecification.class);
        final RequisicaoAtualizacaoRepository requisicaoAtualizacaoRepoMock = mock(RequisicaoAtualizacaoRepository.class);
        final ArrayList<Atualizacao> atualizacoesDoServico = new ArrayList<>();
        final Atualizacao atualizacaoMock = mock(Atualizacao.class);
        final Message messageMock = mock(Message.class);
        final User userMock = mock(User.class);
        final Chat chatMock = mock(Chat.class);
        final ArrayList<RequisicaoAtualizacao> retornoObter = new ArrayList<>();
        atualizacoesDoServico.add(atualizacaoMock);
        bot.setRequisicaoAtualizacaoRepository(requisicaoAtualizacaoRepoMock);
        bot.setAtualizacaoService(atualizacaoServiceMock);
        bot.setSendMessageFactory(sendMessageFactoryMock);
        bot.setTelegramBot(botMock);
        doReturn("/info processo").when(messageMock).text();
        doReturn(retornoObter).when(requisicaoAtualizacaoRepoMock).obter(Matchers.any(RequisicaoAtualizacaoSpecification.class));
        doReturn(atualizacoesDoServico).when(atualizacaoServiceMock).obterAtualizacoes(Matchers.eq("processo"));
        doReturn(messageMock).when(updateMock).message();
        doReturn(new Integer(123)).when(messageMock).messageId();
        doReturn(userMock).when(messageMock).from();
        doReturn(new Integer(312)).when(userMock).id();
        doReturn(chatMock).when(messageMock).chat();
        doReturn(new Long(231)).when(chatMock).id();
        doReturn(sendMessageMock).when(sendMessageFactoryMock).instance(eq(new Long(231)), eq(new Integer(123))
                , eq("Atualização em <b>html</b>"));
        doReturn("Atualização em <b>html</b>").when(atualizacaoMock).getMensagemHTML();

        bot.responder(updateMock, specMock);

        verify(sendMessageFactoryMock)
                .instance(eq(new Long(231)), eq(new Integer(123)), eq("Atualização em <b>html</b>"));
    }

    @Test
    public void deveConstruirRespostaAPartirDasRequisicoesCadastradasQuandoBarraProcessoSemParametro() {
        final TelegramBot telegramBotMock = mock(TelegramBot.class);
        final SendMessageFactory sendMessageFactoryMock = mock(SendMessageFactory.class);
        final RequisicaoAtualizacaoRepository requisicaoAtualizacaoRepositoryMock =
                mock(RequisicaoAtualizacaoRepository.class);
        final AtualizacaoService atualizacaoServiceMock = mock(AtualizacaoService.class);
        final Update updateMock = mock(Update.class);
        final User userMock = mock(User.class);
        final Chat chatMock = mock(Chat.class);
        final RequisicaoAtualizacaoSpecification specificationMock = mock(RequisicaoAtualizacaoSpecification.class);
        final ArrayList<RequisicaoAtualizacao> requisicoesAtualizacao = new ArrayList<>(3);
        final Message messageMock = mock(Message.class);
        final ProcessoAtualizacaoBot bot = new ProcessoAtualizacaoBot.Builder()
                .telegramBot(telegramBotMock)
                .sendMessageFactory(sendMessageFactoryMock)
                .requisicaoAtualizacaoRepository(requisicaoAtualizacaoRepositoryMock)
                .atualizacaoService(atualizacaoServiceMock)
                .build();
        requisicoesAtualizacao.add(new RequisicaoAtualizacao("numeroprocesso1"));
        requisicoesAtualizacao.add(new RequisicaoAtualizacao("numeroprocesso2"));
        requisicoesAtualizacao.add(new RequisicaoAtualizacao("numeroprocesso3"));
        doReturn("/novidade").when(messageMock).text();
        doReturn(messageMock).when(updateMock).message();
        doReturn(requisicoesAtualizacao).when(requisicaoAtualizacaoRepositoryMock).obter(specificationMock);
        doReturn(new Integer(2)).when(userMock).id();
        doReturn(new Long(1)).when(chatMock).id();
        doReturn(chatMock).when(messageMock).chat();

        bot.responder(updateMock, specificationMock);

        verify(sendMessageFactoryMock).instance(new Long(1)
                , requisicoesAtualizacao);
        verify(sendMessageFactoryMock).instance(new Long(1)
                , requisicoesAtualizacao);
        verify(sendMessageFactoryMock).instance(new Long(1)
                , requisicoesAtualizacao);
    }

    @Test
    public void deveEnviarRespostaComTodasAsRequisicoesQuandoBarraProcessoSemParametros() {
        final TelegramBot telegramBotMock = mock(TelegramBot.class);
        final SendMessageFactory sendMessageFactory = new SendMessageFactoryImpl();
        final RequisicaoAtualizacaoRepository requisicaoAtualizacaoRepositoryMock =
                mock(RequisicaoAtualizacaoRepository.class);
        final AtualizacaoService atualizacaoServiceMock = mock(AtualizacaoService.class);
        final ArrayList<Atualizacao> atualizacoes = new ArrayList<>();
        final Update updateMock = mock(Update.class);
        final User userMock = mock(User.class);
        final Chat chatMock = mock(Chat.class);
        final Atualizacao atualizacaoMock = mock(Atualizacao.class);
        final RequisicaoAtualizacaoSpecification specificationMock = mock(RequisicaoAtualizacaoSpecification.class);
        final ArrayList<RequisicaoAtualizacao> requisicoesAtualizacao = spy(new ArrayList<>(3));
        final Message messageMock = mock(Message.class);
        final ProcessoAtualizacaoBot bot = new ProcessoAtualizacaoBot.Builder()
                .telegramBot(telegramBotMock)
                .sendMessageFactory(sendMessageFactory)
                .requisicaoAtualizacaoRepository(requisicaoAtualizacaoRepositoryMock)
                .atualizacaoService(atualizacaoServiceMock)
                .build();
        atualizacoes.add(atualizacaoMock);
        requisicoesAtualizacao.add(new RequisicaoAtualizacao("numeroprocesso1"));
        requisicoesAtualizacao.add(new RequisicaoAtualizacao("numeroprocesso2"));
        requisicoesAtualizacao.add(new RequisicaoAtualizacao("numeroprocesso3"));
        doReturn("/novidade").when(messageMock).text();
        doReturn(messageMock).when(updateMock).message();
        doReturn(requisicoesAtualizacao).when(requisicaoAtualizacaoRepositoryMock).obter(specificationMock);
        doReturn(new Integer(2)).when(userMock).id();
        doReturn(new Long(1)).when(chatMock).id();
        doReturn(chatMock).when(messageMock).chat();
        doReturn(atualizacoes).when(atualizacaoServiceMock).obterAtualizacoes(anyString());
        doReturn("Mensagem").when(atualizacaoMock).getMensagemHTML();

        bot.responder(updateMock, specificationMock);

        verify(telegramBotMock).execute((SendMessage) notNull());
        verify(requisicoesAtualizacao).iterator();
    }

    @Test
    public void deveRetornarAtualizacoesQuandoResponderAoNumeroDoProcessoJaRegistrado() {
        final TelegramBot telegramBotMock = mock(TelegramBot.class);
        final SendMessage sendMessageMock = mock(SendMessage.class);
        final SendMessageFactory sendMessageFactoryMock = mock(SendMessageFactory.class);
        final RequisicaoAtualizacaoRepository requisicaoAtualizacaoRepositoryMock =
                mock(RequisicaoAtualizacaoRepository.class);
        final AtualizacaoService atualizacaoServiceMock = mock(AtualizacaoService.class);
        final Update updateMock = mock(Update.class);
        final User userMock = mock(User.class);
        final Chat chatMock = mock(Chat.class);
        final RequisicaoAtualizacaoSpecification specificationMock = mock(RequisicaoAtualizacaoSpecification.class);
        final RequisicaoAtualizacao requisicaoAtualizacao = new RequisicaoAtualizacao("1000438-56.2017.4.01.3400");
        final Message messageMock = mock(Message.class);
        final ProcessoAtualizacaoBot bot = new ProcessoAtualizacaoBot.Builder()
                .telegramBot(telegramBotMock)
                .sendMessageFactory(sendMessageFactoryMock)
                .requisicaoAtualizacaoRepository(requisicaoAtualizacaoRepositoryMock)
                .atualizacaoService(atualizacaoServiceMock)
                .build();
        final Atualizacao atualizacaoMock = mock(Atualizacao.class);
        final Atualizacao atualizacaoMock2 = mock(Atualizacao.class);
        final ArrayList<Atualizacao> atualizacoes = new ArrayList<>();
        atualizacoes.add(atualizacaoMock);
        atualizacoes.add(atualizacaoMock2);
        doReturn("1000438-56.2017.4.01.3400").when(messageMock).text();
        doReturn(messageMock).when(updateMock).message();
        doReturn(requisicaoAtualizacao).when(requisicaoAtualizacaoRepositoryMock).obter("1000438-56.2017.4.01.3400");
        doReturn(new Integer(2)).when(userMock).id();
        doReturn(new Long(1)).when(chatMock).id();
        doReturn(chatMock).when(messageMock).chat();
        doReturn(new Integer(2)).when(messageMock).messageId();
        doReturn("Mensagem Atualizacao").when(atualizacaoMock).getMensagemHTML();
        doReturn("Mensagem Atualizacao 2").when(atualizacaoMock2).getMensagemHTML();
        doReturn(atualizacoes).when(atualizacaoServiceMock).obterAtualizacoes("1000438-56.2017.4.01.3400");
        doReturn(sendMessageMock).when(sendMessageFactoryMock).instance(eq(new Long(1)), eq(new Integer(2)), (String)notNull());

        bot.responder(updateMock, specificationMock);

        verify(telegramBotMock, times(atualizacoes.size())).execute(sendMessageMock);
    }

}

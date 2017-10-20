package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RespostaService;
import br.jus.trf1.bot.novidade.Atualizacao;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ProcessoSelecionadoStrategyTest {

    @Test
    public void deveEnviarNovidadeDoProcessoRequisitadoQuandoResponderRequisicao() {
        final RespostaService respostaService = mock(RespostaService.class);
        final AtualizacaoRespository atualizacaoRespository = mock(AtualizacaoRespository.class);
        final ProcessoSelecionadoStrategy strategy = new ProcessoSelecionadoStrategy.Builder()
                .requisicaoService(respostaService)
                .atualizacaoRespository(atualizacaoRespository)
                .build();
        final Atualizacao atualizacao = mock(Atualizacao.class);
        final Requisicao requisicao = mock(Requisicao.class);

        doReturn(true).when(atualizacao).haNovidades();
        doReturn("processo").when(requisicao).getProcesso();
        doReturn(new Long(1)).when(requisicao).getChatId();
        doReturn(atualizacao).when(atualizacaoRespository).obterNovidades(eq("processo"));

        strategy.execute(requisicao);

        verify(respostaService).responder(requisicao, atualizacao);
    }

}
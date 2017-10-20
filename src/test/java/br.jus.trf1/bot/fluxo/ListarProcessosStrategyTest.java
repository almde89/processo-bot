package br.jus.trf1.bot.fluxo;

import br.jus.trf1.Processo;
import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.RespostaService;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ListarProcessosStrategyTest {
    @Test
    public void deveResponderRequisicaoComProcessosDoRepositoryQuandoResponderRequisicao() throws Exception {
        final RequisicaoRepository requisicaoRepository = mock(RequisicaoRepository.class);
        final RespostaService respostaService = mock(RespostaService.class);
        final ListarProcessosStrategy strategy = new ListarProcessosStrategy.Builder()
                .requisicaoRepository(requisicaoRepository)
                .requisicaoService(respostaService)
                .build();
        final ArrayList<Processo> processos = new ArrayList<>();
        final Requisicao requisicao = mock(Requisicao.class);

        doReturn(new Long(1)).when(requisicao).getChatId();
        doReturn(processos).when(requisicaoRepository).obterProcessos(eq(new Long(1)));

        strategy.execute(requisicao);

        verify(respostaService).responder(requisicao, processos);
    }

}
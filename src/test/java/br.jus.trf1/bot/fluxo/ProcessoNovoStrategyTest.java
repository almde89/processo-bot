package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.RespostaService;
import br.jus.trf1.bot.novidade.Atualizacao;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;
import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class ProcessoNovoStrategyTest {
    @Test
    public void deveEnviarNovidadeDoProcessoRequisitadoQuandoResponderRequisicao() throws Exception {
        final RequisicaoRepository requisicaoRepository = mock(RequisicaoRepository.class);
        final RespostaService respostaService = mock(RespostaService.class);
        final AtualizacaoRespository atualizacaoRespository = mock(AtualizacaoRespository.class);
        final ProcessoNovoStrategy strategy = new ProcessoNovoStrategy.Builder()
                .requisicaoRepository(requisicaoRepository)
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

    @Test
    public void deveRegistrarRequisicaoQuandoRequisicaoNaoCadastrada() throws Exception {
        final RequisicaoRepository requisicaoRepository = mock(RequisicaoRepository.class);
        final RespostaService respostaService = mock(RespostaService.class);
        final AtualizacaoRespository atualizacaoRespository = mock(AtualizacaoRespository.class);
        final ProcessoNovoStrategy strategy = new ProcessoNovoStrategy.Builder()
                .requisicaoRepository(requisicaoRepository)
                .requisicaoService(respostaService)
                .atualizacaoRespository(atualizacaoRespository)
                .build();
        final Atualizacao atualizacao = mock(Atualizacao.class);
        final Requisicao requisicao = mock(Requisicao.class);

        doReturn("processo").when(requisicao).getProcesso();
        doReturn(new Long(1)).when(requisicao).getChatId();
        doReturn(atualizacao).when(atualizacaoRespository).obterNovidades(eq("processo"));

        strategy.execute(requisicao);

        verify(requisicaoRepository).registrar(requisicao);
    }

    @Test
    public void naoDeveRegistrarRequisicaoQuandoRequisicaoJaCadastrada() throws Exception {
        final RequisicaoRepository requisicaoRepository = mock(RequisicaoRepository.class);
        final RespostaService respostaService = mock(RespostaService.class);
        final AtualizacaoRespository atualizacaoRespository = mock(AtualizacaoRespository.class);
        final ProcessoNovoStrategy strategy = new ProcessoNovoStrategy.Builder()
                .requisicaoRepository(requisicaoRepository)
                .requisicaoService(respostaService)
                .atualizacaoRespository(atualizacaoRespository)
                .build();
        final Atualizacao atualizacao = mock(Atualizacao.class);
        final Requisicao requisicao = mock(Requisicao.class);

        doReturn(true).when(requisicao).isCadastrada();
        doReturn("processo").when(requisicao).getProcesso();
        doReturn(new Long(1)).when(requisicao).getChatId();
        doReturn(atualizacao).when(atualizacaoRespository).obterNovidades(eq("processo"));

        strategy.execute(requisicao);

        verify(requisicaoRepository, times(0)).registrar(requisicao);
    }

}
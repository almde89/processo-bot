package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.RespostaService;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ProcessoNovoSpecificationTest {

    @Test
    public void deveSatisfazerSpecificationQuandoBarraNovidade() {
        final ProcessoNovoSpecification specification = new ProcessoNovoSpecification(mock(AtualizacaoRespository.class)
                , mock(RequisicaoRepository.class), mock(RespostaService.class));
        final Requisicao requisicao = mock(Requisicao.class);
        final Boolean satisfaz;

        doReturn("/novidade 1000432-54.2017.4.01.3400").when(requisicao).getTexto();

        satisfaz = specification.satisfesteiPor(requisicao);

        assertTrue("/novidade deve satisfazer à especificação", satisfaz);
    }

    @Test
    public void naoDeveSatisfazerSpecificationQuandoDiferenteBarraNovidade() {
        final ProcessoNovoSpecification specification = new ProcessoNovoSpecification(mock(AtualizacaoRespository.class)
                , mock(RequisicaoRepository.class), mock(RespostaService.class));
        final Requisicao requisicao = mock(Requisicao.class);
        final Boolean satisfaz;

        doReturn("qualquercoisa").when(requisicao).getTexto();

        satisfaz = specification.satisfesteiPor(requisicao);

        assertFalse("Qualquer coisa diferente de /novidade não pode satisfazer a especificação", satisfaz);
    }

    @Test
    public void deveRetornarFluxoListarProcessosQuandoSolicitado() {
        final RespostaStrategy respostaStrategy;
        final ProcessoNovoSpecification specification = new ProcessoNovoSpecification(mock(AtualizacaoRespository.class)
                , mock(RequisicaoRepository.class), mock(RespostaService.class));

        respostaStrategy = specification.getRespostaStrategy();

        assertEquals("Deve ser do tipo ListarProcessosStrategy", ProcessoNovoStrategy.class, respostaStrategy.getClass());
    }
}

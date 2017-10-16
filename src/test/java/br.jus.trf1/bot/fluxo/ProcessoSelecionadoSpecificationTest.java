package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoService;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ProcessoSelecionadoSpecificationTest {
    @Test
    public void deveSatisfazerSpecificationQuandoNumeroProcesso() {
        final ProcessoSelecionadoSpecification specification = new ProcessoSelecionadoSpecification(mock(AtualizacaoRespository.class)
                , mock(RequisicaoService.class));
        final Requisicao requisicao = mock(Requisicao.class);
        final Boolean satisfaz;

        doReturn("1000432-54.2017.4.01.3400").when(requisicao).getTexto();

        satisfaz = specification.satisfaz(requisicao);

        assertTrue("1000432-54.2017.4.01.3400 deve satisfazer à especificação", satisfaz);
    }

    @Test
    public void naoDeveSatisfazerSpecificationQuandoDiferenteDoNumeroProcesso() {
        final ProcessoSelecionadoSpecification specification =
                new ProcessoSelecionadoSpecification(mock(AtualizacaoRespository.class)
                , mock(RequisicaoService.class));
        final Requisicao requisicao = mock(Requisicao.class);
        final Boolean satisfaz;

        doReturn("qualquercoisa").when(requisicao).getTexto();

        satisfaz = specification.satisfaz(requisicao);

        assertFalse("Qualquer coisa diferente de /novidade não pode satisfazer a especificação", satisfaz);
    }

    @Test
    public void deveRetornarFluxoProcessoSelecionadoQuandSolicitado() {
        final FluxoStrategy fluxoStrategy;
        final ProcessoSelecionadoSpecification specification = new ProcessoSelecionadoSpecification(mock(AtualizacaoRespository.class)
                , mock(RequisicaoService.class));

        fluxoStrategy = specification.getFluxoStrategy();

        assertEquals("Deve ser do tipo ListarProcessosStrategy", ProcessoSelecionadoStrategy.class, fluxoStrategy.getClass());
    }
}

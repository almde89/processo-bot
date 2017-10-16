package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.RequisicaoService;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ListarProcessoSpecificationTest {

    @Test
    public void deveSatisfazerSpecificationQuandoBarraNovidade() {
        final ListarProcessoSpecification specification = new ListarProcessoSpecification(mock(RequisicaoRepository.class)
                , mock(RequisicaoService.class));
        final Requisicao requisicao = mock(Requisicao.class);
        final Boolean satisfaz;

        doReturn("/novidade").when(requisicao).getTexto();

        satisfaz = specification.satisfaz(requisicao);

        assertTrue("/novidade deve satisfazer à especificação", satisfaz);
    }

    @Test
    public void naoDeveSatisfazerSpecificationQuandoDiferenteBarraNovidade() {
        final ListarProcessoSpecification specification = new ListarProcessoSpecification(mock(RequisicaoRepository.class)
                , mock(RequisicaoService.class));
        final Requisicao requisicao = mock(Requisicao.class);
        final Boolean satisfaz;

        doReturn("qualquercoisa").when(requisicao).getTexto();

        satisfaz = specification.satisfaz(requisicao);

        assertFalse("Qualquer coisa diferente de /novidade não pode satisfazer a especificação", satisfaz);
    }

    @Test
    public void deveRetornarFluxoListarProcessosQuandoSolicitado() {
        final FluxoStrategy fluxoStrategy;
        final ListarProcessoSpecification specification = new ListarProcessoSpecification(mock(RequisicaoRepository.class)
                , mock(RequisicaoService.class));

        fluxoStrategy = specification.getFluxoStrategy();

        assertEquals("Deve ser do tipo ListarProcessosStrategy", ListarProcessosStrategy.class, fluxoStrategy.getClass());
    }
}

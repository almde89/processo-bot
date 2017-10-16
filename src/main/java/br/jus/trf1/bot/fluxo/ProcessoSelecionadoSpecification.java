package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoService;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;

import java.util.regex.Pattern;

public class ProcessoSelecionadoSpecification implements FluxoSpecification {

    public ProcessoSelecionadoSpecification(final AtualizacaoRespository respository, final RequisicaoService service) {
        mFluxo = new ProcessoSelecionadoStrategy.Builder()
                .atualizacaoRespository(respository)
                .requisicaoService(service)
                .build();
    }

    @Override
    public Boolean satisfaz(Requisicao requisicao) {
        return mPattern.matcher(requisicao.getTexto()).matches();
    }

    @Override
    public FluxoStrategy getFluxoStrategy() {
        return mFluxo;
    }

    private Pattern mPattern = Pattern.compile("(\\d{7})\\-(\\d{2})\\.(\\d{4})\\.(\\d)\\.(\\d{2})\\.(\\d{4})");
    private ProcessoSelecionadoStrategy mFluxo;
}

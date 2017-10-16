package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.RequisicaoService;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;

import java.util.regex.Pattern;

public class ProcessoNovoSpecification implements FluxoSpecification {

    public ProcessoNovoSpecification(final AtualizacaoRespository atualizacaoRespository
            , final RequisicaoRepository requisicaoRepository, final RequisicaoService service) {
        mFluxoStrategy = new ProcessoNovoStrategy.Builder().atualizacaoRespository(atualizacaoRespository)
                .requisicaoRepository(requisicaoRepository)
                .requisicaoService(service).build();
    }

    @Override
    public Boolean satisfaz(Requisicao requisicao) {
        return mPattern.matcher(requisicao.getTexto()).matches();
    }

    @Override
    public FluxoStrategy getFluxoStrategy() {
        return mFluxoStrategy;
    }

    private Pattern mPattern = Pattern.compile("(/novidade) (\\d{7})\\-(\\d{2})\\.(\\d{4})\\.(\\d)\\.(\\d{2})\\.(\\d{4})");
    private ProcessoNovoStrategy mFluxoStrategy;
}

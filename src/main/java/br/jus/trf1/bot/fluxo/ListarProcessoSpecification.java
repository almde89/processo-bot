package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.RequisicaoService;

import java.util.regex.Pattern;

public class ListarProcessoSpecification implements FluxoSpecification {
    private final ListarProcessosStrategy mFluxoStrategy;

    public ListarProcessoSpecification(final RequisicaoRepository repository, final RequisicaoService service) {
        mFluxoStrategy = new ListarProcessosStrategy.Builder()
                .requisicaoRepository(repository)
                .requisicaoService(service).build();
    }

    @Override
    public Boolean satisfaz(Requisicao requisicao) {
        return mPattern.matcher(requisicao.getTexto()).matches();
    }

    private Pattern mPattern = Pattern.compile("(/novidade)");

    public FluxoStrategy getFluxoStrategy() {
        return mFluxoStrategy;
    }
}

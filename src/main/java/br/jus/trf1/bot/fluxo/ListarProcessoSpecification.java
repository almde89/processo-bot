package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.RespostaService;

import java.util.regex.Pattern;

public class ListarProcessoSpecification implements RespostaSpecification {
    private final ListarProcessosStrategy mFluxoStrategy;

    public ListarProcessoSpecification(final RequisicaoRepository repository, final RespostaService service) {
        mFluxoStrategy = new ListarProcessosStrategy.Builder()
                .requisicaoRepository(repository)
                .requisicaoService(service).build();
    }

    @Override
    public Boolean satisfesteiPor(Requisicao requisicao) {
        return mPattern.matcher(requisicao.getTexto()).matches();
    }

    private Pattern mPattern = Pattern.compile("(/novidade)");

    public RespostaStrategy getRespostaStrategy() {
        return mFluxoStrategy;
    }
}

package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.RespostaService;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;

import java.util.regex.Pattern;

public class ProcessoNovoSpecification implements RespostaSpecification {

    public ProcessoNovoSpecification(final AtualizacaoRespository atualizacaoRespository
            , final RequisicaoRepository requisicaoRepository, final RespostaService service) {
        mFluxoStrategy = new ProcessoNovoStrategy.Builder().atualizacaoRespository(atualizacaoRespository)
                .requisicaoRepository(requisicaoRepository)
                .requisicaoService(service).build();
    }

    @Override
    public Boolean satisfesteiPor(Requisicao requisicao) {
        return mPattern.matcher(requisicao.getTexto()).matches();
    }

    @Override
    public RespostaStrategy getRespostaStrategy() {
        return mFluxoStrategy;
    }

    private Pattern mPattern = Pattern.compile("(/novidade) (\\d{7})\\-(\\d{2})\\.(\\d{4})\\.(\\d)\\.(\\d{2})\\.(\\d{4})");
    private ProcessoNovoStrategy mFluxoStrategy;
}

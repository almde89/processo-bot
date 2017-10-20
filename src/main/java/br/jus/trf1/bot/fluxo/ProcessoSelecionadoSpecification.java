package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RespostaService;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;

import java.util.regex.Pattern;

public class ProcessoSelecionadoSpecification implements RespostaSpecification {

    public ProcessoSelecionadoSpecification(final AtualizacaoRespository respository, final RespostaService service) {
        mFluxo = new ProcessoSelecionadoStrategy.Builder()
                .atualizacaoRespository(respository)
                .requisicaoService(service)
                .build();
    }

    @Override
    public Boolean satisfesteiPor(Requisicao requisicao) {
        return mPattern.matcher(requisicao.getTexto()).matches();
    }

    @Override
    public RespostaStrategy getRespostaStrategy() {
        return mFluxo;
    }

    private Pattern mPattern = Pattern.compile("(\\d{7})\\-(\\d{2})\\.(\\d{4})\\.(\\d)\\.(\\d{2})\\.(\\d{4})");
    private ProcessoSelecionadoStrategy mFluxo;
}

package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RespostaService;
import br.jus.trf1.bot.novidade.Atualizacao;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;

public class ProcessoSelecionadoStrategy implements RespostaStrategy {
    private RespostaService mRespostaService;
    private AtualizacaoRespository mAtualizacaoRespository;

    @Override
    public <T> T execute(Requisicao requisicao) {
        final Atualizacao atualizacao = mAtualizacaoRespository.obterNovidades(requisicao.getProcesso());

        if (atualizacao.haNovidades()) {
            return (T) mRespostaService.responder(requisicao, mAtualizacaoRespository.obterNovidades(requisicao.getProcesso()));
        } else {
            return (T) mRespostaService.responder(requisicao, "Não há atualizações para o processo");
        }
    }

    public static class Builder {
        private RespostaService respostaService;
        private AtualizacaoRespository atualizacaoRespository;

        public Builder requisicaoService(RespostaService respostaService) {
            this.respostaService = respostaService;
            return this;
        }

        public ProcessoSelecionadoStrategy build() {
            final ProcessoSelecionadoStrategy instance = new ProcessoSelecionadoStrategy();
            instance.mRespostaService = respostaService;
            instance.mAtualizacaoRespository = atualizacaoRespository;
            return instance;
        }

        public Builder atualizacaoRespository(AtualizacaoRespository atualizacaoRespository) {
            this.atualizacaoRespository = atualizacaoRespository;
            return this;
        }
    }
}

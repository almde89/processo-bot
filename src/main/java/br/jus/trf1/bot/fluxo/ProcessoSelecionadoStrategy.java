package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoService;
import br.jus.trf1.bot.novidade.Atualizacao;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;

public class ProcessoSelecionadoStrategy implements FluxoStrategy {
    private RequisicaoService mRequisicaoService;
    private AtualizacaoRespository mAtualizacaoRespository;

    @Override
    public void execute(Requisicao requisicao) {
        final Atualizacao atualizacao = mAtualizacaoRespository.obterNovidades(requisicao.getProcesso());

        if (atualizacao.haNovidades()) {
            mRequisicaoService.responder(requisicao, mAtualizacaoRespository.obterNovidades(requisicao.getProcesso()));
        } else {
            mRequisicaoService.responder(requisicao, "Não há atualizações para o processo");
        }
    }

    public static class Builder {
        private RequisicaoService requisicaoService;
        private AtualizacaoRespository atualizacaoRespository;

        public Builder requisicaoService(RequisicaoService requisicaoService) {
            this.requisicaoService = requisicaoService;
            return this;
        }

        public ProcessoSelecionadoStrategy build() {
            final ProcessoSelecionadoStrategy instance = new ProcessoSelecionadoStrategy();
            instance.mRequisicaoService = requisicaoService;
            instance.mAtualizacaoRespository = atualizacaoRespository;
            return instance;
        }

        public Builder atualizacaoRespository(AtualizacaoRespository atualizacaoRespository) {
            this.atualizacaoRespository = atualizacaoRespository;
            return this;
        }
    }
}

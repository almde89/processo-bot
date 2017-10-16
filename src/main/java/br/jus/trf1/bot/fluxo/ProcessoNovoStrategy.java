package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.RequisicaoService;
import br.jus.trf1.bot.novidade.Atualizacao;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;

public class ProcessoNovoStrategy implements FluxoStrategy {

    private RequisicaoRepository mRequisicaoRepository;
    private RequisicaoService mRequisicaoService;
    private AtualizacaoRespository mAtualizacaoRespository;

    @Override
    public void execute(Requisicao requisicao) {
        final Atualizacao atualizacao;
        if (!requisicao.isCadastrada()) {
            mRequisicaoRepository.registrar(requisicao);
        }
        atualizacao = mAtualizacaoRespository.obterNovidades(requisicao.getProcesso());

        if (atualizacao.haNovidades()) {
            mRequisicaoService.responder(requisicao, atualizacao);
        } else {
            mRequisicaoService.responder(requisicao, "Não ná atualizações para o processo escolhido.");
        }
    }

    public static class Builder {
        private RequisicaoRepository requisicaoRepository;
        private RequisicaoService requisicaoService;
        private AtualizacaoRespository atualizacaoRespository;

        public Builder requisicaoRepository(RequisicaoRepository requisicaoRepository) {
            this.requisicaoRepository = requisicaoRepository;
            return this;
        }

        public Builder requisicaoService(RequisicaoService requisicaoService) {
            this.requisicaoService = requisicaoService;
            return this;
        }

        public ProcessoNovoStrategy build() {
            final ProcessoNovoStrategy instance = new ProcessoNovoStrategy();
            instance.mRequisicaoRepository = requisicaoRepository;
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

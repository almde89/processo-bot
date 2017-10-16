package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.RequisicaoService;

public class ListarProcessosStrategy implements FluxoStrategy {

    private RequisicaoRepository mRequisicaoRepository;
    private RequisicaoService mRequisicaoService;

    @Override
    public void execute(Requisicao requisicao) {
        mRequisicaoService.responder(requisicao, mRequisicaoRepository.obterProcessos(requisicao.getChatId()));
    }

    public static class Builder {
        private RequisicaoRepository requisicaoRepository;
        private RequisicaoService requisicaoService;

        public Builder requisicaoRepository(RequisicaoRepository requisicaoRepository) {
            this.requisicaoRepository = requisicaoRepository;
            return this;
        }

        public ListarProcessosStrategy build() {
            final ListarProcessosStrategy instance = new ListarProcessosStrategy();
            instance.mRequisicaoRepository = requisicaoRepository;
            instance.mRequisicaoService = requisicaoService;
            return instance;
        }

        public Builder requisicaoService(RequisicaoService requisicaoService) {
            this.requisicaoService = requisicaoService;
            return this;
        }
    }
}

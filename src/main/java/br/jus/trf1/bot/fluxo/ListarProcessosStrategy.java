package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.RespostaService;

public class ListarProcessosStrategy implements RespostaStrategy {

    private RequisicaoRepository mRequisicaoRepository;
    private RespostaService mRespostaService;

    @Override
    public <T> T execute(Requisicao requisicao) {
        return  (T) mRespostaService.responder(requisicao, mRequisicaoRepository.obterProcessos(requisicao.getChatId()));
    }

    public static class Builder {
        private RequisicaoRepository requisicaoRepository;
        private RespostaService respostaService;

        public Builder requisicaoRepository(RequisicaoRepository requisicaoRepository) {
            this.requisicaoRepository = requisicaoRepository;
            return this;
        }

        public ListarProcessosStrategy build() {
            final ListarProcessosStrategy instance = new ListarProcessosStrategy();
            instance.mRequisicaoRepository = requisicaoRepository;
            instance.mRespostaService = respostaService;
            return instance;
        }

        public Builder requisicaoService(RespostaService respostaService) {
            this.respostaService = respostaService;
            return this;
        }
    }
}

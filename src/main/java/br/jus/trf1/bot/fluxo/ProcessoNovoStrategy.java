package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.Resposta;
import br.jus.trf1.bot.RespostaService;
import br.jus.trf1.bot.novidade.Atualizacao;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;

public class ProcessoNovoStrategy implements RespostaStrategy {

    private RequisicaoRepository mRequisicaoRepository;
    private RespostaService mRespostaService;
    private AtualizacaoRespository mAtualizacaoRespository;

    @Override
    public <T> T execute(Requisicao requisicao) {
        final Atualizacao atualizacao;
        if (!requisicao.isCadastrada()) {
            mRequisicaoRepository.registrar(requisicao);
        }
        atualizacao = mAtualizacaoRespository.obterNovidades(requisicao.getProcesso());

        if (atualizacao.haNovidades()) {
            return (T) mRespostaService.responder(requisicao, atualizacao);
        } else {
            return (T) mRespostaService.responder(requisicao, "Não ná atualizações para o processo escolhido.");
        }
    }

    public static class Builder {
        private RequisicaoRepository requisicaoRepository;
        private RespostaService respostaService;
        private AtualizacaoRespository atualizacaoRespository;

        public Builder requisicaoRepository(RequisicaoRepository requisicaoRepository) {
            this.requisicaoRepository = requisicaoRepository;
            return this;
        }

        public Builder requisicaoService(RespostaService respostaService) {
            this.respostaService = respostaService;
            return this;
        }

        public ProcessoNovoStrategy build() {
            final ProcessoNovoStrategy instance = new ProcessoNovoStrategy();
            instance.mRequisicaoRepository = requisicaoRepository;
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

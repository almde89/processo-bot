package br.jus.trf1.bot;

import br.jus.trf1.bot.exception.ComportamentoIndeterminadoException;
import br.jus.trf1.bot.fluxo.ListarProcessoSpecification;
import br.jus.trf1.bot.fluxo.ProcessoNovoSpecification;
import br.jus.trf1.bot.fluxo.ProcessoSelecionadoSpecification;
import br.jus.trf1.bot.fluxo.RespostaSpecification;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class NovidadeProcessoBot {

    public NovidadeProcessoBot() {
    }

    public static class Builder {
        private AtualizacaoRespository atualizacaoRespository;
        private RequisicaoRepository requisicaoRepository;
        private RespostaService respostaService;

        public Builder() {}

        public Builder atualizacaoRepository(final AtualizacaoRespository respository) {
            atualizacaoRespository = respository;
            return this;
        }

        public Builder respostaService(final RespostaService service) {
            respostaService = service;
            return this;
        }

        public Builder requisicaoRepository(final RequisicaoRepository repository) {
            requisicaoRepository = repository;
            return this;
        }

        public NovidadeProcessoBot build() {
            final NovidadeProcessoBot instance = new NovidadeProcessoBot();
            instance.setRequisicaoAtualizacaoRepository(requisicaoRepository);
            instance.mAtualizacaoRespository = atualizacaoRespository;
            instance.mRespostaService = respostaService;
            instance.mRequisicaoRepository = requisicaoRepository;
            instance.addFluxoSpecification(new ListarProcessoSpecification(requisicaoRepository, respostaService));
            instance.addFluxoSpecification(new ProcessoNovoSpecification(atualizacaoRespository, requisicaoRepository, respostaService));
            instance.addFluxoSpecification(new ProcessoSelecionadoSpecification(atualizacaoRespository, respostaService));
            return instance;
        }
    }

    public void setRequisicaoAtualizacaoRepository(final RequisicaoRepository requisicaoRepository) {
        mRequisicaoRepository = requisicaoRepository;
    }

    public Resposta responder(final Requisicao novaRequisicao) {
        final Stream<RespostaSpecification> respostaSpecificationStream = mRespostaSpecifications
                .stream()
                .filter(specification -> specification.satisfesteiPor(novaRequisicao));
        final Optional<RespostaSpecification> optionalSpecification = respostaSpecificationStream.findFirst();

        if (optionalSpecification.isPresent())  return novaRequisicao.responder(optionalSpecification.get());

        throw new ComportamentoIndeterminadoException("Não deveríamos estar aqui").requisicao(novaRequisicao);

    }

    public void addFluxoSpecification(final RespostaSpecification specification) {
        mRespostaSpecifications.add(specification);
    }

    private List<RespostaSpecification> mRespostaSpecifications = new ArrayList<>();

    private AtualizacaoRespository mAtualizacaoRespository;

    private RespostaService mRespostaService;

    private RequisicaoRepository mRequisicaoRepository;

}

package br.jus.trf1.bot;

import br.jus.trf1.bot.fluxo.FluxoSpecification;
import br.jus.trf1.bot.fluxo.ListarProcessoSpecification;
import br.jus.trf1.bot.fluxo.ProcessoNovoSpecification;
import br.jus.trf1.bot.fluxo.ProcessoSelecionadoSpecification;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;
import br.jus.trf1.telegram.bot.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;

import java.util.ArrayList;
import java.util.List;

public class NovidadeProcessoBot {

    public NovidadeProcessoBot() {
        addFluxoSpecification(new ListarProcessoSpecification(mRequisicaoRepository, mRequisicaoService));
        addFluxoSpecification(new ProcessoNovoSpecification(mAtualizacaoRespository, mRequisicaoRepository, mRequisicaoService));
        addFluxoSpecification(new ProcessoSelecionadoSpecification(mAtualizacaoRespository, mRequisicaoService));
    }

    public static class Builder {
        private AtualizacaoRespository atualizacaoRespository;
        private RequisicaoRepository requisicaoRepository;
        private RequisicaoService requisicaoService;

        public Builder() {}

        public Builder atualizacaoRepository(final AtualizacaoRespository respository) {
            atualizacaoRespository = respository;
            return this;
        }

        public Builder requisicaoService(final RequisicaoService service) {
            requisicaoService = service;
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
            instance.mRequisicaoService = requisicaoService;
            instance.mRequisicaoRepository = requisicaoRepository;
            return instance;
        }
    }

    public void setRequisicaoAtualizacaoRepository(final RequisicaoRepository requisicaoRepository) {
        mRequisicaoRepository = requisicaoRepository;
    }

    public void responder(final Requisicao novaRequisicao) {
        mFluxoSpecifications.forEach(specification -> {
            if (specification.satisfaz(novaRequisicao)) specification.getFluxoStrategy().execute(novaRequisicao);
        });
    }

    public void addFluxoSpecification(final FluxoSpecification specification) {
        mFluxoSpecifications.add(specification);
    }

    private List<FluxoSpecification> mFluxoSpecifications = new ArrayList<>();

    private AtualizacaoRespository mAtualizacaoRespository;

    private RequisicaoService mRequisicaoService;

    private RequisicaoRepository mRequisicaoRepository;

}

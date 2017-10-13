package br.jus.trf1.bot;

import br.jus.trf1.bot.novidade.Atualizacao;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;
import br.jus.trf1.telegram.bot.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;

import java.util.List;

public class NovidadeProcessoBot {

    public static class Builder {
        private SendMessageFactory sendMessageFactory;
        private AtualizacaoRespository atualizacaoRespository;
        private TelegramBot telegramBot;
        private RequisicaoRepository requisicaoRepository;

        public Builder() {}

        public Builder sendMessageFactory(final SendMessageFactory factory) {
            sendMessageFactory = factory;
            return this;
        }

        public Builder atualizacaoService(final AtualizacaoRespository service) {
            atualizacaoRespository = service;
            return this;
        }

        public Builder telegramBot(final TelegramBot bot) {
            telegramBot = bot;
            return this;
        }

        public Builder requisicaoAtualizacaoRepository(final RequisicaoRepository repository) {
            requisicaoRepository = repository;
            return this;
        }

        public NovidadeProcessoBot build() {
            final NovidadeProcessoBot instance = new NovidadeProcessoBot();
            instance.setRequisicaoAtualizacaoRepository(requisicaoRepository);
            instance.setTelegramBot(telegramBot);
            instance.setSendMessageFactory(sendMessageFactory);
            instance.setAtualizacaoService(atualizacaoRespository);
            return instance;
        }
    }

    public void setRequisicaoAtualizacaoRepository(final RequisicaoRepository requisicaoRepository) {
        mRequisicaoRepository = requisicaoRepository;
    }

    public void responder(final Requisicao novaRequisicao) {
        novaRequisicao.getFluxo().execute(mAtualizacaoRespository, mTelegramBot, mRequisicaoRepository, mSendMessageFactory);
    }

    public void setTelegramBot(final TelegramBot telegramBot) {
        mTelegramBot = telegramBot;
    }

    public void setAtualizacaoService(final AtualizacaoRespository atualizacaoRespository) {
        mAtualizacaoRespository = atualizacaoRespository;
    }

    public void setSendMessageFactory(final SendMessageFactory sendMessageFactory) {
        mSendMessageFactory = sendMessageFactory;
    }

    private AtualizacaoRespository mAtualizacaoRespository;

    private TelegramBot mTelegramBot;

    private RequisicaoRepository mRequisicaoRepository;

    private SendMessageFactory mSendMessageFactory;
}

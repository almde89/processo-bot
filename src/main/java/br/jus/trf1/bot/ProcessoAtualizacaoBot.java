package br.jus.trf1.bot;

import br.jus.trf1.telegram.bot.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

import java.util.List;

public class ProcessoAtualizacaoBot {

    public static class Builder {
        private SendMessageFactory sendMessageFactory;
        private AtualizacaoService atualizacaoService;
        private TelegramBot telegramBot;
        private RequisicaoAtualizacaoRepository requisicaoAtualizacaoRepository;

        public Builder() {}

        public Builder sendMessageFactory(final SendMessageFactory factory) {
            sendMessageFactory = factory;
            return this;
        }

        public Builder atualizacaoService(final AtualizacaoService service) {
            atualizacaoService = service;
            return this;
        }

        public Builder telegramBot(final TelegramBot bot) {
            telegramBot = bot;
            return this;
        }

        public Builder requisicaoAtualizacaoRepository(final RequisicaoAtualizacaoRepository repository) {
            requisicaoAtualizacaoRepository = repository;
            return this;
        }

        public ProcessoAtualizacaoBot build() {
            final ProcessoAtualizacaoBot instance = new ProcessoAtualizacaoBot();
            instance.setRequisicaoAtualizacaoRepository(requisicaoAtualizacaoRepository);
            instance.setTelegramBot(telegramBot);
            instance.setSendMessageFactory(sendMessageFactory);
            instance.setAtualizacaoService(atualizacaoService);
            return instance;
        }
    }

    public void setRequisicaoAtualizacaoRepository(final RequisicaoAtualizacaoRepository requisicaoAtualizacaoRepository) {
        mRequisicaoAtualizacaoRepository = requisicaoAtualizacaoRepository;
    }

    public void responder(final Update update, RequisicaoAtualizacaoSpecification specification) {
        final String mensagemString = update.message().text();
        final String[] fragmentosMensagem = mensagemString.split(" ");
        final List<Atualizacao> atualizacoes;
        final String numeroProcesso = fragmentosMensagem.length > 1? fragmentosMensagem[1] : fragmentosMensagem[0];
        final RequisicaoAtualizacao requisicaoCadastrada = mRequisicaoAtualizacaoRepository.obter(numeroProcesso);
        final List<RequisicaoAtualizacao> requisicoesCadastradas;

        if (numeroProcesso.matches("(\\d{7})\\-(\\d{2})\\.(\\d{4})\\.(\\d)\\.(\\d{2})\\.(\\d{4})")) {
            if (requisicaoCadastrada.isCadastrada()) {
                atualizacoes = mAtualizacaoService.obterAtualizacoes(requisicaoCadastrada.getProcesso());
                atualizacoes.forEach(atualizacao ->
                        mTelegramBot.execute(mSendMessageFactory.instance(update.message().chat().id()
                                , update.message().messageId(), atualizacao.getMensagemHTML()))
                );
            }
        } else {
            requisicoesCadastradas = mRequisicaoAtualizacaoRepository.obter(specification);
            if (isNenhuma(requisicoesCadastradas)) {
                mRequisicaoAtualizacaoRepository.registrar(update.message().from().id().toString(),  update.message().chat().id().toString()
                    , update.message().messageId().toString(), numeroProcesso);

                atualizacoes = mAtualizacaoService.obterAtualizacoes(numeroProcesso);

                if (isHa(atualizacoes)) {
                    atualizacoes.forEach(atualizacao ->
                        mTelegramBot.execute(mSendMessageFactory.instance(update.message().chat().id()
                                , update.message().messageId(), atualizacao.getMensagemHTML()))
                    );
                }
            } else {
                mTelegramBot.execute(mSendMessageFactory
                        .instance(update.message().chat().id(), requisicoesCadastradas));
            }
        }
    }

    private boolean isHa(List<Atualizacao> atualizacaos) {
        return atualizacaos.size() > 0;
    }

    private boolean isNenhuma(List<RequisicaoAtualizacao> requisicoesCadastrada) {
        return requisicoesCadastrada.size() <= 0;
    }

    public void setTelegramBot(final TelegramBot telegramBot) {
        mTelegramBot = telegramBot;
    }

    public void setAtualizacaoService(final AtualizacaoService atualizacaoService) {
        mAtualizacaoService = atualizacaoService;
    }

    public void setSendMessageFactory(final SendMessageFactory sendMessageFactory) {
        mSendMessageFactory = sendMessageFactory;
    }

    private AtualizacaoService mAtualizacaoService;

    private TelegramBot mTelegramBot;

    private RequisicaoAtualizacaoRepository mRequisicaoAtualizacaoRepository;

    private SendMessageFactory mSendMessageFactory;
}

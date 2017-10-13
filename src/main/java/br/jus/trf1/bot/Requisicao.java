package br.jus.trf1.bot;

import br.jus.trf1.bot.fluxo.Fluxo;
import br.jus.trf1.bot.fluxo.MostrarTecladoFluxo;
import br.jus.trf1.bot.fluxo.ProcessoEscolhidoTecladoFluxo;
import br.jus.trf1.bot.fluxo.ProcessoNovoFluxo;
import br.jus.trf1.bot.novidade.Atualizacao;
import br.jus.trf1.telegram.bot.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.response.SendResponse;

import java.util.List;
import java.util.regex.Pattern;

public class Requisicao {
    private String mNumeroProcesso;
    private Fluxo mFluxo = new ProcessoNovoFluxo(this);
    private Long mChatId;
    private Long mMessageId;
    private volatile SendMessageFactory mSendMessageFactory;
    private volatile TelegramBot mTelegramBot;

    public static class Builder {

        private Message message;
        private SendMessageFactory sendMessageFactory;
        private TelegramBot telegramBot;
        private Long messageId;
        private Long chatId;
        private String processo;

        public Builder() {}

        public Builder sendMessageFactory(final SendMessageFactory factory) {
            sendMessageFactory = factory;
            return this;
        }

        public Builder telegramBot(final TelegramBot bot) {
            telegramBot = bot;
            return this;
        }

        public Builder messageId(final Long identificaoMensagem) {
            messageId = identificaoMensagem;
            return this;
        }

        public Builder chatId(final Long identificacaoChat) {
            chatId = identificacaoChat;
            return this;
        }

        public Builder processo (final String numeroProcesso) {
            processo = numeroProcesso;
            return this;
        }

        public Builder message(final Message mensagemUsuario) {
            message = mensagemUsuario;
            return this;
        }

        public Requisicao build() {
            final Requisicao instance = new Requisicao();
            if (message != null) instance.setMessage(message);
            else {
                instance.mNumeroProcesso = processo;
                instance.mChatId = chatId;
                instance.mMessageId = messageId;
            }
            instance.mTelegramBot = telegramBot;
            instance.mSendMessageFactory =sendMessageFactory;
            return instance;
        }
    }

    Requisicao () {}

    public Requisicao(final Message message) {
        setMessage(message);
    }

    public String getProcesso() {
        return mNumeroProcesso;
    }

    public boolean isNumeroProcessoValido() {
        return isNumeroProcesso(mNumeroProcesso);
    }

    private void setNumeroProcesso(String numeroProcesso) {
        if (!isNumeroProcesso(numeroProcesso))
            throw new IllegalArgumentException("O processo deve obedecer ao NPU.");
        mNumeroProcesso = numeroProcesso;
    }

    private boolean isNumeroProcesso(String numeroProcesso) {
        final Pattern matcher = Pattern.compile("(\\d{7})\\-(\\d{2})\\.(\\d{4})\\.(\\d)\\.(\\d{2})\\.(\\d{4})");
        return matcher.matcher(numeroProcesso).matches();
    }

    public Fluxo getFluxo() {
        return mFluxo;
    }

    public Long getChatId() {
        return mChatId;
    }

    public Integer getMessageId() {
        return mMessageId.intValue();
    }

    public SendResponse responder(final Atualizacao atualizacao) {
        return mTelegramBot.execute(mSendMessageFactory.instance(getChatId()
                , getMessageId(), atualizacao.getMensagemHTML()));
    }

    public SendResponse responder() {
        return mTelegramBot.execute(mSendMessageFactory.instance(getChatId()
                , getMessageId()));
    }

    public SendResponse responder(final List<Requisicao> requisicoesCadastradas) {
        return mTelegramBot.execute(mSendMessageFactory.instance(getChatId(), requisicoesCadastradas));
    }

    private void setMessage(final Message message) {
        final String mensagem = message.text();
        final String[] fragmentosMensagem = mensagem.split(" ");

        if (fragmentosMensagem.length > 1) {
            setNumeroProcesso(fragmentosMensagem[1]);
        }
        else if (!mensagem.startsWith("/")) {
            setNumeroProcesso(mensagem);
            mFluxo = new ProcessoEscolhidoTecladoFluxo(this);
        } else {
            mFluxo = new MostrarTecladoFluxo(this);
        }
        mChatId = message.chat().id();
        mMessageId = message.messageId().longValue();
    }
}

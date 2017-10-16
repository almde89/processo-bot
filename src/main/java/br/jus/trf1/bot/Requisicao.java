package br.jus.trf1.bot;

import br.jus.trf1.telegram.bot.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;

import java.util.regex.Pattern;

public class Requisicao {
    private String mNumeroProcesso;
    private Long mChatId;
    private Long mMessageId;
    private String mTexto;
    private RequisicaoRepository mRequisicaoRepository;

    public String getTexto() {
        return mTexto;
    }

    public Boolean isCadastrada() {
        return mRequisicaoRepository.obter(mNumeroProcesso, mChatId) != null;
    }

    public static class Builder {

        private Message message;
        private SendMessageFactory sendMessageFactory;
        private TelegramBot telegramBot;
        private Long messageId;
        private Long chatId;
        private String processo;
        private RequisicaoRepository requisicaoRepository;

        public Builder() {}


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

        public Builder requisicaoRepository(final RequisicaoRepository repository) {
            requisicaoRepository = repository;
            return this;
        }

        public Requisicao build() {
            final Requisicao instance = new Requisicao();
            if (message != null) instance.setMessage(message);
            else if (processo != null) {
                instance.setNumeroProcesso(processo);
                instance.mChatId = chatId;
                instance.mMessageId = messageId;
            }
            instance.mRequisicaoRepository = requisicaoRepository;
            return instance;
        }
    }

    Requisicao () {
        mNumeroProcesso = "";
    }

    public Requisicao(final Message message) {
        mTexto = message.text();
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

    public Long getChatId() {
        return mChatId;
    }

    public Integer getMessageId() {
        return mMessageId.intValue();
    }

//    public SendResponse responder(final List<Requisicao> requisicoesCadastradas) {
//        return mTelegramBot.execute(mSendMessageFactory.instance(getChatId(), requisicoesCadastradas));
//    }

    private void setMessage(final Message message) {
        final String mensagem = message.text();
        final String[] fragmentosMensagem = mensagem.split(" ");

        if (fragmentosMensagem.length > 1) {
            setNumeroProcesso(fragmentosMensagem[1]);
        }
        else if (!mensagem.startsWith("/")) {
            setNumeroProcesso(mensagem);
        }
        mChatId = message.chat().id();
        mMessageId = message.messageId().longValue();
    }
}

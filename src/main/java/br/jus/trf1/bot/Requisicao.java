package br.jus.trf1.bot;

import br.jus.trf1.Processo;
import br.jus.trf1.bot.fluxo.RespostaSpecification;
import br.jus.trf1.bot.fluxo.RespostaStrategy;
import br.jus.trf1.bot.novidade.Atualizacao;
import com.pengrad.telegrambot.model.Message;

public class Requisicao {
    private Processo mProcesso;
    private Long mChatId;
    private Long mMessageId;
    private String mTexto;
    private RequisicaoRepository mRequisicaoRepository;
    private RespostaFactory mRespostaFactory;

    public String getTexto() {
        return mTexto;
    }

    public Boolean isCadastrada() {
        return mRequisicaoRepository.obter(mProcesso.getProcessoNumero(), mChatId) != null;
    }

    public Resposta responder(RespostaSpecification respostaSpecification) {
        return mRespostaFactory.instance(this, respostaSpecification);
    }

    public static class Builder {

        private Message message;
        private Long messageId;
        private Long chatId;
        private Processo processo;
        private RequisicaoRepository requisicaoRepository;
        private String textoMensagem;
        private RespostaFactory respostaFactory;

        public Builder() {}


        public Builder messageId(final Long identificaoMensagem) {
            messageId = identificaoMensagem;
            return this;
        }

        public Builder chatId(final Long identificacaoChat) {
            chatId = identificacaoChat;
            return this;
        }

        public Builder textoMensagem(final String texto) {
            textoMensagem = texto;
            return this;
        }

        public Builder processo (final String numeroProcesso) {
            processo = new Processo(numeroProcesso);
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
                instance.mTexto = textoMensagem;
                instance.setProcesso(processo);
                instance.mChatId = chatId;
                instance.mMessageId = messageId;
            }
            instance.mRespostaFactory = respostaFactory;
            instance.mRequisicaoRepository = requisicaoRepository;
            return instance;
        }


        public Builder respostaFactory(RespostaFactory factory) {
            respostaFactory = factory;
            return this;
        }
    }

    Requisicao () {
        mProcesso = new Processo();
    }

    Requisicao(final Message message) {
        mTexto = message.text();
        setMessage(message);
    }

    public String getProcesso() {
        return mProcesso.getProcessoNumero();
    }

    public boolean isNumeroProcessoValido() {
        return mProcesso.isValido();
    }

    private void setProcesso(Processo processo) {
        mProcesso = processo;
    }

    public Long getChatId() {
        return mChatId;
    }

    public Long getMessageId() {
        return mMessageId;
    }

    private void setMessage(final Message message) {
        final String mensagem = message.text();
        final String[] fragmentosMensagem = mensagem.split(" ");

        if (fragmentosMensagem.length > 1) {
            setProcesso(new Processo(fragmentosMensagem[1]));
        }
        else if (!mensagem.startsWith("/")) {
            setProcesso(new Processo(mensagem));
        }
        mTexto = mensagem;
        mChatId = message.chat().id();
        mMessageId = message.messageId().longValue();
    }
}

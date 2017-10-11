package br.jus.trf1.bot;

import java.util.ArrayList;
import java.util.List;

import static j2html.TagCreator.br;

public class Atualizacao {

    public String getMensagemHTML() {
        return mMensagemHTML;
    }

    public static class Builder {

        public Builder() {
            novidadesAtualizacao = new ArrayList<>();
        }

        public Atualizacao build() {
            final Atualizacao instance = new Atualizacao();
            conteudo = new StringBuilder();
            if (cabecalhoAtualizacao != null) conteudo.append(cabecalhoAtualizacao.toString());
            novidadesAtualizacao.stream().forEachOrdered(novidade -> conteudo.append(novidade.toString()).append("\n"));
            instance.mMensagemHTML = conteudo.toString();
            return instance;
        }

        public Builder adicionar(final Cabecalho cabecalho) {
            cabecalhoAtualizacao = cabecalho;
            return this;
        }

        public Cabecalho.Builder adicionarCabecalho(final String cabecalhoString) {
            return new Cabecalho.Builder(this).adicionarLinha(cabecalhoString);
        }

        public Builder adicionar(final Novidade novidade) {
            novidadesAtualizacao.add(novidade);
            return this;
        }

        private StringBuilder conteudo;
        private List<Novidade> novidadesAtualizacao;
        private Cabecalho cabecalhoAtualizacao;
    }

    private String mMensagemHTML;
}

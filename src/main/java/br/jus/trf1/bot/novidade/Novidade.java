package br.jus.trf1.bot.novidade;

public class Novidade {

    public Novidade(final String textoNovidade) {
        mTextoNovidade = textoNovidade;
    }

    public static class Builder {

        public Builder() {
            conteudoAtualizacao = new StringBuilder();
        }

        public Novidade build() {
            return new Novidade(conteudoAtualizacao.toString());
        }

        public Builder atualizacao(final String atualizacao) {
            conteudoAtualizacao.append(atualizacao).append("\n");
            return this;
        }

        private StringBuilder conteudoAtualizacao;
    }

    @Override
    public String toString() {
        return mTextoNovidade;
    }

    private final String mTextoNovidade;
}

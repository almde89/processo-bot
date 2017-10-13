package br.jus.trf1.bot.novidade;

public class Cabecalho {

    public static class Builder {

        public Builder() {
            cabecalhoAtualizacao = new StringBuilder();
        }

        public Builder(Atualizacao.Builder builder) {
            this();
            atualizacaoBuilder = builder;
        }

        public Cabecalho build() {
            final Cabecalho cabecalho = new Cabecalho();
            cabecalho.conteudoCabecalho = cabecalhoAtualizacao.toString();
            return cabecalho;
        }

        public Atualizacao.Builder finalizar() {
            return atualizacaoBuilder.adicionar(build());
        }

        public Builder adicionarLinha(final String cabecalho) {
            cabecalhoAtualizacao.append(cabecalho).append("\n");
            return this;
        }

        private StringBuilder cabecalhoAtualizacao;
        private Atualizacao.Builder atualizacaoBuilder;
    }

    @Override
    public String toString() {
        return conteudoCabecalho;
    }

    private String conteudoCabecalho;
}

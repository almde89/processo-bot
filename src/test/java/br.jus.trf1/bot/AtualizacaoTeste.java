package br.jus.trf1.bot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AtualizacaoTeste {

    @Test
    public void deveAdicionarTextoAtualizacaoQuandoAdicionarCabecalho() {
        final StringBuilder expectCabecalho = new StringBuilder();
        final String cabecalhoPrimeiraLinha = "Este é um cabecalho  <b>html</b>";
        final String cabecalhoSegundaLinha = "Este é a segunda linha";
        final String cabecalhoTerceiraLinha = "Esta é a terceira linha.";
        final Atualizacao.Builder atualizacaoBuilder = new Atualizacao.Builder();
        expectCabecalho.append(cabecalhoPrimeiraLinha).append("\n")
                .append(cabecalhoSegundaLinha).append("\n")
                .append(cabecalhoTerceiraLinha).append("\n");

        atualizacaoBuilder
                .adicionarCabecalho(cabecalhoPrimeiraLinha)
                .adicionarLinha(cabecalhoSegundaLinha)
                .adicionarLinha(cabecalhoTerceiraLinha)
                .finalizar();

        assertEquals("O conteúdo da atualização deve ser igual ao cabeçalho adicionado", expectCabecalho.toString()
                , atualizacaoBuilder.build().getMensagemHTML());
    }

    @Test
    public void deveAdicionarTextoAtualizacaoQuandoAdicionarNovidade() {
        final String primeiraLinhaNovidade = "texto atualização.";
        final String segundaLinhaNovidade = "segunda linha atualizacao";
        final String terceiraLinhaNovidade = "terceira linha atualizacao";
        final String quartaLinhaNovidade = "quarta linha atualizacao";
        final StringBuilder expectTextoNovidade = new StringBuilder();
        final Atualizacao.Builder atualizacaoBuilder = new Atualizacao.Builder();
        expectTextoNovidade.append(primeiraLinhaNovidade).append("\n")
                .append("\n")
                .append(segundaLinhaNovidade).append("\n")
                .append(quartaLinhaNovidade).append("\n")
                .append(terceiraLinhaNovidade).append("\n")
                .append("\n");

        atualizacaoBuilder.adicionar(new Novidade.Builder().atualizacao(primeiraLinhaNovidade)
                .build())
            .adicionar(new Novidade.Builder().atualizacao(segundaLinhaNovidade)
                    .atualizacao(quartaLinhaNovidade)
                    .atualizacao(terceiraLinhaNovidade).build());

        assertEquals("O conteúdo da atualização deve ser igual ao conteúdo das novidades adicionadas."
                , expectTextoNovidade.toString(), atualizacaoBuilder.build().getMensagemHTML());
    }

    @Test
    public void deveAdicionarTextoAtualizacaoQuandoAdicionarCabecalhoENovidade() {
        final String primeiraLinhaNovidade = "texto atualização.";
        final String segundaLinhaNovidade = "segunda linha atualizacao";
        final String terceiraLinhaNovidade = "terceira linha atualizacao";
        final String quartaLinhaNovidade = "quarta linha atualizacao";
        final String cabecalhoPrimeiraLinha = "Este é um cabecalho  <b>html</b>";
        final String cabecalhoSegundaLinha = "Este é a segunda linha";
        final String cabecalhoTerceiraLinha = "Esta é a terceira linha.";
        final StringBuilder expectTextoNovidade = new StringBuilder();
        final Atualizacao.Builder atualizacaoBuilder = new Atualizacao.Builder();
        expectTextoNovidade.append(cabecalhoPrimeiraLinha).append("\n")
                .append(cabecalhoSegundaLinha).append("\n")
                .append(cabecalhoTerceiraLinha).append("\n")
                .append(primeiraLinhaNovidade).append("\n")
                .append("\n")
                .append(segundaLinhaNovidade).append("\n")
                .append(quartaLinhaNovidade).append("\n")
                .append(terceiraLinhaNovidade).append("\n")
                .append("\n");

        atualizacaoBuilder.adicionar(new Novidade.Builder().atualizacao(primeiraLinhaNovidade)
                .build())
                .adicionar(new Cabecalho.Builder().adicionarLinha(cabecalhoPrimeiraLinha)
                        .adicionarLinha(cabecalhoSegundaLinha).adicionarLinha(cabecalhoTerceiraLinha).build())
                .adicionar(new Novidade.Builder().atualizacao(segundaLinhaNovidade)
                        .atualizacao(quartaLinhaNovidade)
                        .atualizacao(terceiraLinhaNovidade).build());

        assertEquals("O conteúdo da atualização deve ser igual ao conteúdo das novidades adicionadas e de seu cabeçalho."
                , expectTextoNovidade.toString(), atualizacaoBuilder.build().getMensagemHTML());
    }

}

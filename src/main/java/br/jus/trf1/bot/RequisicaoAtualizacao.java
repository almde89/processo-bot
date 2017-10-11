package br.jus.trf1.bot;

public class RequisicaoAtualizacao {
    private String mNumeroProcesso;
    private boolean mCadastrada;

    public RequisicaoAtualizacao (final String numeroProcesso) {
        mNumeroProcesso = numeroProcesso;
        mCadastrada = true;
    }

    public RequisicaoAtualizacao() {
        mCadastrada = false;
    }

    public String getProcesso() {
        return mNumeroProcesso;
    }

    public boolean isCadastrada() {
        return mCadastrada;
    }
}

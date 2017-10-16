package br.jus.trf1;

public class Processo {
    private String mNumeroProcesso;

    public Processo(final String numeroProcesso) {
        mNumeroProcesso = numeroProcesso;
    }

    public String getProcessoNumero() {
        return mNumeroProcesso;
    }
}

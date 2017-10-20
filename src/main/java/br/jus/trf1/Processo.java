package br.jus.trf1;

import java.util.regex.Pattern;

public class Processo {
    private String mNumeroProcesso;
    private Boolean mValido;

    public Processo() {
        mValido = false;
    }

    public Processo(final String numeroProcesso) {
        if (!isNumeroProcesso(numeroProcesso))
            throw new IllegalArgumentException("O processo deve obedecer ao NPU.");
        mValido = true;
        mNumeroProcesso = numeroProcesso;
    }

    public String getProcessoNumero() {
        return mNumeroProcesso;
    }

    public Boolean isValido() {
        return mValido;
    }

    private boolean isNumeroProcesso(String numeroProcesso) {
        final Pattern matcher = Pattern.compile("(\\d{7})\\-(\\d{2})\\.(\\d{4})\\.(\\d)\\.(\\d{2})\\.(\\d{4})");
        return matcher.matcher(numeroProcesso).matches();
    }

}

package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.novidade.Atualizacao;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;
import br.jus.trf1.telegram.bot.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;

import java.util.List;

public class ProcessoEscolhidoTecladoFluxo extends Fluxo {
    public ProcessoEscolhidoTecladoFluxo(Requisicao requisicao) {
        super(requisicao);
    }

    @Override
    public void execute(AtualizacaoRespository atualizacaoRespository, TelegramBot telegramBot, RequisicaoRepository requisicaoRepository, SendMessageFactory sendMessageFactory) {
        final List<Atualizacao> atualizacoes;
        atualizacoes = atualizacaoRespository.obterAtualizacoes(getNovaRequisicao().getProcesso());
        enviarAtualizacoes(atualizacoes);
    }

    private void enviarAtualizacoes(List<Atualizacao> atualizacoes) {
        atualizacoes.forEach(atualizacao ->
                getNovaRequisicao().responder(atualizacao)
        );
    }
}

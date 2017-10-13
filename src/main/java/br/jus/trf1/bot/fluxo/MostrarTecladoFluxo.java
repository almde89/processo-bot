package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;
import br.jus.trf1.telegram.bot.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;

import java.util.List;

public class MostrarTecladoFluxo extends Fluxo {

    public MostrarTecladoFluxo(Requisicao requisicao) {
        super(requisicao);
    }

    @Override
    public void execute(AtualizacaoRespository atualizacaoRespository, TelegramBot telegramBot
            , RequisicaoRepository requisicaoRepository, SendMessageFactory sendMessageFactory) {
        final List<Requisicao> requisicoesCadastradas;
        requisicoesCadastradas = requisicaoRepository.obter(getNovaRequisicao().getChatId());
        if (isNenhuma(requisicoesCadastradas)) {
            getNovaRequisicao().responder();
        } else {
            getNovaRequisicao().responder(requisicoesCadastradas);
        }
    }

    private boolean isNenhuma(List<Requisicao> requisicoesCadastrada) {
        return requisicoesCadastrada.size() <= 0;
    }
}

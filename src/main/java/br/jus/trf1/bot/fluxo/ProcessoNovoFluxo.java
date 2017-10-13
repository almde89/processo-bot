package br.jus.trf1.bot.fluxo;

import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.novidade.Atualizacao;
import br.jus.trf1.bot.novidade.AtualizacaoRespository;
import br.jus.trf1.telegram.bot.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;

import java.util.List;

public class ProcessoNovoFluxo extends Fluxo {

    public ProcessoNovoFluxo(Requisicao requisicao) {
        super(requisicao);
    }

    @Override
    public void execute(AtualizacaoRespository atualizacaoRespository, TelegramBot telegramBot
            , RequisicaoRepository requisicaoRepository
            , SendMessageFactory sendMessageFactory) {
        final Requisicao requisicaoCadastrada = requisicaoRepository
                .obter(getNovaRequisicao().getProcesso(), getNovaRequisicao().getChatId());
        final List<Atualizacao> atualizacoes;
        if (!requisicaoCadastrada.isNumeroProcessoValido()) {
            requisicaoRepository.registrar(getNovaRequisicao());
        }
        atualizacoes = atualizacaoRespository.obterAtualizacoes(getNovaRequisicao().getProcesso());
        enviarAtualizacoes(atualizacoes);
    }

    private void enviarAtualizacoes(List<Atualizacao> atualizacoes) {
        atualizacoes.forEach(atualizacao ->
                getNovaRequisicao().responder(atualizacao)
        );
    }
}

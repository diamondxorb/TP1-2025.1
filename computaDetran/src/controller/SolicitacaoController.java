package controller;

import model.SolicitacaoAgendamento;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoController {
    private final List<SolicitacaoAgendamento> solicitacoes;

    public SolicitacaoController() {
        this.solicitacoes = new ArrayList<>();
    }

    public boolean solicitarAgendamento(SolicitacaoAgendamento solicitacao) {
        try {
            solicitacao.setStatus("Pendente");
            solicitacoes.add(solicitacao);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<SolicitacaoAgendamento> listarSolicitacoes(String cpfProprietario) {
        List<SolicitacaoAgendamento> result = new ArrayList<>();
        for (SolicitacaoAgendamento s : solicitacoes) {
            if (s.getVeiculo().getProprietario().getCpf().equals(cpfProprietario)) {
                result.add(s);
            }
        }
        return result;
    }

    public List<SolicitacaoAgendamento> listarSolicitacoesPendentes() {
        List<SolicitacaoAgendamento> result = new ArrayList<>();
        for (SolicitacaoAgendamento s : solicitacoes) {
            if ("Pendente".equals(s.getStatus())) {
                result.add(s);
            }
        }
        return result;
    }

    public boolean aprovarSolicitacao(int idSolicitacao) {
        for (SolicitacaoAgendamento s : solicitacoes) {
            if (s.getId() == idSolicitacao) {
                s.setStatus("Aprovado");
                return true;
            }
        }
        return false;
    }

    public boolean negarSolicitacao(int idSolicitacao, String motivo) {
        for (SolicitacaoAgendamento s : solicitacoes) {
            if (s.getId() == idSolicitacao) {
                s.setStatus("Negado");
                s.setMotivoNegacao(motivo);
                return true;
            }
        }
        return false;
    }
}
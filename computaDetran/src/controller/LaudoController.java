package controller;

import model.Laudo;
import java.util.ArrayList;
import java.util.List;

public class LaudoController {
    private static final List<Laudo> laudos = new ArrayList<>();

    public LaudoController() {}

    //Mantido igual ao anterior
    public static List<Laudo> listarPorProprietario(String cpfProprietario) {
        List<Laudo> result = new ArrayList<>();
        for (Laudo laudo : laudos) {
            if (laudo.getVeiculo().getProprietario().getCpf().equals(cpfProprietario)) {
                result.add(laudo);
            }
        }
        return result;
    }

    //Nova função para integração com agendamentos
    public boolean emitirLaudo(Laudo laudo) {
        try {
            laudos.add(laudo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
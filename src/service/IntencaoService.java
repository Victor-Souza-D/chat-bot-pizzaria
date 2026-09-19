package service;

import model.Intencao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IntencaoService {
    private final Map<Intencao, List<String>> regras;

    public IntencaoService() {
        regras = new LinkedHashMap<>();

        regras.put(
                Intencao.CANCELAR_RESERVA,
                List.of("cancelar", "cancelamento")
        );

        regras.put(
                Intencao.CONSULTAR_RESERVA,
                List.of("olhar", "ver", "verificar")
        );

        regras.put(
                Intencao.RESERVAR,
                List.of("reservar", "marcar", "reserva")
        );

        regras.put(
                Intencao.PRECO,
                List.of("custa", "preço", "valor")
        );

        regras.put(
                Intencao.HORARIO,
                List.of("horário", "horas", "funcionamento", "aberto")
        );

        regras.put(
                Intencao.ENDERECO,
                List.of("endereço", "lugar", "local")
        );
    }

    public Intencao identificadorIntencao(String mensagem) {
        for (Map.Entry<Intencao, List<String>> regra : regras.entrySet()) {
            for (String palavra : regra.getValue()) {
                if (mensagem.contains(palavra)) {
                    return regra.getKey();
                }
            }
        }
        return Intencao.DESCONHECIDO;
    }
}

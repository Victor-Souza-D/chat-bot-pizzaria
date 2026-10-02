package service;

import model.Cliente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ChatService {
    private final List<Cliente> clientes = new ArrayList<>();

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Cliente> buscarPorNome(String nomeCliente) {
        return clientes.stream()
                .filter(cliente -> cliente.getNome().equalsIgnoreCase(nomeCliente))
                .toList();
    }

    private boolean remover;
    public boolean removerReserva(String nomeCliente) {
        return remover =  clientes.removeIf(cliente -> cliente.getNome().equalsIgnoreCase(nomeCliente));
    }

    public boolean existeAgendamento(LocalDate dia, LocalTime horario) {
        for (Cliente agendamento : clientes) {
            if (agendamento.getDia().equals(dia) &&  agendamento.getHorario().equals(horario)) {
                return true;
            }
        }
        return false;
    }
}

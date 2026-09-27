package service;

import model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ChatService {
    private final List<Cliente> clientes = new ArrayList<>();

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Cliente> buscarPorNome(String nomeCliente) {
        return clientes.stream()
                .filter(cliente -> cliente.getNome().equals(nomeCliente))
                .toList();
    }

    public boolean removerReserva(String nomeCliente) {
        boolean removido = clientes.removeIf(cliente -> cliente.getNome().equals(nomeCliente));
        return removido;
    }
}

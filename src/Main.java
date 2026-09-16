import model.Cliente;
import model.Intencao;
import service.ChatService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChatService chatService = new ChatService();

        boolean continuar = true;
        while (continuar) {
            System.out.print("\nMensagem: ");
            String mensagem = sc.nextLine().toLowerCase();

            if (mensagem.equals("sair") || mensagem.contains("tchau") || mensagem.contains("flw")) {
                continuar =  false;
                break;
            }

            boolean encontrou = false;

            if (mensagem.contains("preço") || mensagem.contains("preco") || mensagem.contains("valor") || mensagem.contains("custa")) {
                System.out.println(responderPreco());
                encontrou = true;
            }
            if (mensagem.contains("horário") || mensagem.contains("horario")) {
                System.out.println(responderHorario());
                encontrou = true;
            }
            if (mensagem.contains("endereço") || mensagem.contains("localização") || mensagem.contains("local")) {
                System.out.println(responderEndereco());
                encontrou = true;
            }
            if (mensagem.contains("reservar") || mensagem.contains("reserva")) {
                responderReserva(sc, chatService);
                encontrou = true;
            }
            if (mensagem.contains("cancelar") || mensagem.contains("cancelamento")) {
                responderCancelamento(sc, chatService);
                encontrou = true;
            }
            if (mensagem.contains("ver") || mensagem.contains("consultar")) {
                verReserva(sc, chatService);
                encontrou = true;
            }
            if (!encontrou) {
                System.out.println("Não entendi....");
            }
        }
        sc.close();
    }

    public static String responderPreco() {
        return "Pizza G R$36.00\n" +
                "Pizza M R$26.00\n" +
                "Pizza Extra Grande R$40.00";
    }

    public static String responderHorario() {
        return "Segundas a Sexta das 18h as 00h\n" +
                "Sabados e Domingos 18h as 1h";
    }

    public static String responderEndereco() {
        return "Rua Apóstolo Matheus, Santa Etelvina, 245";
    }

    public static void responderReserva(Scanner sc, ChatService service) {
        System.out.print("Seu Nome: ");
        String nomeCliente = sc.nextLine();

        System.out.print("Qual o Horário: ");
        String horarioCliente = sc.nextLine();

        System.out.print("Quantas pessoas: ");
        int qtdPessoas = Integer.parseInt(sc.nextLine());

        Cliente cliente = new Cliente(nomeCliente, horarioCliente, qtdPessoas);
        service.adicionarCliente(cliente);
    }

    public static void responderCancelamento(Scanner sc, ChatService chatService) {
        System.out.print("Nome da Reserva: ");
        String nomeCliente = sc.nextLine();

        boolean removido = chatService.removerReserva(nomeCliente);
        if (removido) {
            System.out.println("Cancelamento feito com sucesso!!");
        } else {
            System.out.println("Não encontrei uma reserva com esse nome!");
        }
    }

    public static void verReserva(Scanner sc, ChatService chatService) {
        System.out.print("Nome da Reserva: ");
        String nomeCliente = sc.nextLine();

        List<Cliente> clientes = chatService.buscarPorNome(nomeCliente);
        if (clientes.isEmpty()) {
            System.out.println("Reserva nenhuma reserva encontrada nesse nome!");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    public Intencao identificarIntencao(String mensagem) {
        if (mensagem.contains("cancelar") || mensagem.contains())
            return Intencao.CANCELAR_RESERVA;

        if (mensagem.contains("preço") || mensagem.contains("valor") || mensagem.contains("custa"))
            return Intencao.PRECO;

        return Intencao.DESCONHECIDO;
    }
}

import model.Cliente;
import model.Intencao;
import service.ChatService;
import service.IntencaoService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChatService chatService = new ChatService();
        IntencaoService intencaoService = new IntencaoService();

        boolean continuar = true;
        while (continuar) {
            System.out.print("\nMensagem: ");
            String mensagem = sc.nextLine().toLowerCase();

            Intencao intencao = intencaoService.identificadorIntencao(mensagem);

            switch (intencao) {
                case CANCELAR_RESERVA -> responderCancelamento(sc, chatService);
                case CONSULTAR_RESERVA -> verReserva(sc, chatService);
                case RESERVAR -> responderReserva(sc, chatService);
                case PRECO -> responderPreco();
                case HORARIO -> responderHorario();
                case ENDERECO -> responderEndereco();
                case DESCONHECIDO -> System.out.println("Não entendi, por favor repita sua pergunta!!");
            }
        }
        sc.close();
    }

    public static void responderPreco() {
        System.out.println("Pizza G R$36.00\n" +
                "Pizza M R$26.00\n" +
                "Pizza Extra Grande R$40.00");
    }

    public static void responderHorario() {
        System.out.println("Segundas a Sexta das 18h as 00h\n" +
                "Sabados e Domingos 18h as 1h");
    }

    public static void responderEndereco() {
        System.out.println("Rua Apóstolo Matheus, Santa Etelvina, 245");
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
           for (Cliente cliente : clientes) {
               System.out.println(cliente.toString());
           }
        }
    }
}

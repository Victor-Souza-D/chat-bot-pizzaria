import model.Cliente;
import model.Intencao;
import service.ChatService;
import service.IntencaoService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

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

        LocalDate dia;
        while (true) {
            System.out.print("Dia da Reserva: ");
            try {
                dia = LocalDate.parse(sc.nextLine());
                if (dia.getDayOfWeek() == SATURDAY || dia.getDayOfWeek() == SUNDAY) {
                    System.out.println("Fim de Semana");
                } else {
                    System.out.println("Dia da Semana");
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Dia incorreto!");
            }
        }

        LocalTime horarioCliente;
        while (true) {
            System.out.print("Qual o Horário: ");
            try {
                horarioCliente = LocalTime.parse(sc.nextLine());
                break;
            } catch (DateTimeParseException ex) {
                System.out.println("Formato de horario incorreto!");
            }
        }

        int qtdPessoas = 0;
        while (true) {
            System.out.print("Quantas Pessoas: ");
            try {
                qtdPessoas = Integer.parseInt(sc.nextLine());
                if (qtdPessoas > 0) {
                    System.out.println("Dados validos!!");
                    break;
                } else {
                    System.out.println("Numero invalido \n");
                }
            } catch(NumberFormatException e){
                System.out.println("Digite apenas numero");
            }
        }
        Cliente cliente = new Cliente(nomeCliente, dia,  horarioCliente, qtdPessoas);
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
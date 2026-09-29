import model.Cliente;
import model.Intencao;
import service.ChatService;
import service.IntencaoService;

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

        while (true) {
            IO.print("\nMensagem: ");
            String mensagem = sc.nextLine().toLowerCase();

            Intencao intencao = intencaoService.identificadorIntencao(mensagem);

            switch (intencao) {
                case CANCELAR_RESERVA -> responderCancelamento(sc, chatService);
                case CONSULTAR_RESERVA -> verReserva(sc, chatService);
                case RESERVAR -> responderReserva(sc, chatService);
                case PRECO -> responderPreco();
                case HORARIO -> responderHorario();
                case ENDERECO -> responderEndereco();
                case DESCONHECIDO -> IO.println("Não entendi, por favor repita sua pergunta!!");
            }
            sc.close();
        }
    }

    public static void responderPreco() {
        IO.println("""
                Pizza G R$36.00
                Pizza M R$26.00
                Pizza Extra Grande R$40.00""");
    }

    public static void responderHorario() {
        IO.println("Segundas a Sexta das 18h as 00h\n" +
                "Sabados e Domingos 18h as 1h");
    }

    public static void responderEndereco() {
        IO.println("Rua Apóstolo Matheus, Santa Etelvina, 245");
    }

    public static void responderReserva(Scanner sc, ChatService service) {
        IO.print("Seu Nome: ");
        String nomeCliente = sc.nextLine();

        LocalDate dia;
        while (true) {
            IO.print("Dia da Reserva: ");
            try {
                dia = LocalDate.parse(sc.nextLine());
                if (dia.getDayOfWeek() == SATURDAY || dia.getDayOfWeek() == SUNDAY) {
                    IO.println("Fim de Semana");
                } else {
                    IO.println("Dia da Semana");
                }
                break;
            } catch (DateTimeParseException e) {
                IO.println("Dia incorreto!");
            }
        }

        LocalTime horarioCliente;
        while (true) {
            IO.print("Qual o Horário: ");
            try {
                horarioCliente = LocalTime.parse(sc.nextLine());

                LocalTime horarioAbertura = LocalTime.of(18, 0);
                LocalTime horarioFechamento = LocalTime.of(1, 0);

                if (dia.getDayOfWeek() == SATURDAY || dia.getDayOfWeek() == SUNDAY) {
                    if (horarioCliente.equals(horarioAbertura) || horarioCliente.isAfter(horarioAbertura)
                            || horarioCliente.isBefore(horarioFechamento)) {
                        System.out.print("valido");
                        break;
                    } else {
                        System.out.println("invalido"); }
                } else {
                    if (horarioCliente.isAfter(horarioAbertura) || horarioCliente.equals(horarioAbertura)){
                        System.out.print("valido");
                        break;
                    } else {
                        System.out.println("invalido");
                    }
                }
            } catch (DateTimeParseException ex) {
                IO.println("Formato de horario incorreto!");
            }
        }

        int qtdPessoas =0 ;
        while (true) {
            IO.print("Quantas Pessoas: ");
            try {
                qtdPessoas = Integer.parseInt(sc.nextLine());
                if (qtdPessoas > 0) {
                    IO.println("Dados validos!!");
                    break;
                } else {
                    IO.println("Numero invalido \n");
                }
            } catch (NumberFormatException e) {
                IO.println("Digite apenas numero");
            }
        }
        Cliente cliente = new Cliente(nomeCliente, dia, horarioCliente, qtdPessoas);
        service.adicionarCliente(cliente);
    }

    public static void responderCancelamento(Scanner sc, ChatService chatService) {
        IO.print("Nome da Reserva: ");
        String nomeCliente = sc.nextLine();

        boolean removido = chatService.removerReserva(nomeCliente);
        if (removido) {
            IO.println("Cancelamento feito com sucesso!!");
        } else {
            IO.println("Não encontrei uma reserva com esse nome!");
        }
    }

    public static void verReserva(Scanner sc, ChatService chatService) {
        IO.print("Nome da Reserva: ");
        String nomeCliente = sc.nextLine();

        List<Cliente> clientes = chatService.buscarPorNome(nomeCliente);
        if (clientes.isEmpty()) {
            IO.println("Reserva nenhuma reserva encontrada nesse nome!");
        } else {
            for (Cliente cliente : clientes) {
                IO.println(cliente.toString());
            }
        }
    }
}
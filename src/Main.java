import model.Cliente;
import model.Intencao;
import service.ChatService;
import service.HorarioService;
import service.IntencaoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChatService chatService = new ChatService();
        IntencaoService intencaoService = new IntencaoService();
        HorarioService horarioService = new HorarioService();

        while (true) {
            IO.print("\nMensagem: ");
            try {
                String mensagem = sc.nextLine().toLowerCase();

                Intencao intencao = intencaoService.identificadorIntencao(mensagem);

                switch (intencao) {
                    case CANCELAR_RESERVA -> responderCancelamento(sc, chatService);
                    case CONSULTAR_RESERVA -> verReserva(sc, chatService);
                    case RESERVAR -> responderReserva(sc, chatService, horarioService);
                    case PRECO -> responderPreco();
                    case HORARIO -> responderHorario();
                    case ENDERECO -> responderEndereco();
                    case DESCONHECIDO -> IO.println("Não entendi, por favor repita sua pergunta!!");
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
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

    public static void responderReserva(Scanner sc, ChatService service, HorarioService horarioService) {
        String nomeCliente;
        while (true) {
            System.out.print("Nome: ");
            nomeCliente = sc.nextLine().trim();

            if (nomeCliente.trim().isEmpty()) {
                System.out.println("O seu nome deve ter pelo menos um nome.");
            } else {
                break;
            }
        }

        LocalDate dia;
        DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // tranforma o
        while (true) {
            IO.print("Dia da Reserva: ");
            try {
                dia = LocalDate.parse(sc.nextLine(), formatoDia);
                if (dia.isBefore(LocalDate.now())) {
                    IO.println("Data invalida");
                } else {
                    break;
                }
            } catch (DateTimeParseException e) {
                IO.println(e.getMessage());
            }
        }

        LocalTime horarioCliente;
        while (true) {
            IO.print("Qual o Horário: ");
            try {
                horarioCliente = LocalTime.parse(sc.nextLine());

                boolean valido = horarioService.horarioValido(dia, horarioCliente);
                if (valido) {
                } else {
                    IO.println("Horário invalido");
                    continue;
                }

                boolean existeAgendamento = service.existeAgendamento(dia, horarioCliente);
                if (existeAgendamento) {
                    System.out.println("Horário já existente... Por favor coloque outro horário!!");
                    continue;
                }

                LocalDateTime dateTime = LocalDateTime.of(dia, horarioCliente); // Verifica se o cliente tentar reservar no horario que ja passou.
                if (dateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("ERRO>>>");
                    continue;
                }
                break;

            } catch (DateTimeParseException ex) {
                IO.println("Formato de horario incorreto!");
            }
        }

        int qtdPessoas = 0;
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
        System.out.println("Cliente adicionado com sucesso!");
    }

    public static void responderCancelamento(Scanner sc, ChatService chatService) {
            System.out.print("Nome da Reserva: ");
            String nomeCliente = sc.nextLine().trim();

        LocalDate data;
        DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print("Data da Reserva: ");
            try {
                data = LocalDate.parse(sc.nextLine(), formatoDia);
                break;
            } catch (DateTimeParseException ex) {
                System.out.println("Data invalida");
            }
        }

        LocalTime horario;
        while (true) {
            System.out.print("Horario da Reserva: ");
            try {
                horario = LocalTime.parse(sc.nextLine());
                break;
            } catch (DateTimeParseException ex) {
                System.out.println("Horario incorreto!");
            }
        }

        boolean cancelar = chatService.removerReserva(nomeCliente, data, horario);
        if (cancelar) {
            IO.println("Cancelamento feito com sucesso!!");
        } else {
            IO.println("Não encontrei uma reserva com esse dados!");
        }
    }

    public static void verReserva(Scanner sc, ChatService chatService) {
        String nomeCliente;
        while (true) {
            System.out.print("Nome: ");
            nomeCliente = sc.nextLine().trim();
            if (nomeCliente.trim().isEmpty()) {
                System.out.println("O seu nome deve ter pelo menos um nome.");
            } else {
                break;
            }
        }

        List<Cliente> clientes = chatService.buscarPorNome(nomeCliente);
        if (clientes.isEmpty()) {
            IO.println("Nenhuma reserva encontrada nesse nome!");
        } else {
            System.out.print("\nForam encontrados " + clientes.size() + " reservas!");
            System.out.println("\n===+=== Reserva do Cliente ===+===");
            Comparator<Cliente> compararDia = Comparator.comparing(Cliente::getDia).thenComparing(Cliente::getHorario);
            clientes.stream().sorted(compararDia).forEach(System.out::println);
        }
    }
}

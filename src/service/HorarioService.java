package service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class HorarioService {

    public boolean horarioValido(LocalDate dataCliente, LocalTime horarioCliente) {
        LocalTime horarioAbertura = LocalTime.of(18, 0);
        LocalTime horarioFechamento = LocalTime.of(1, 0);

        LocalDate diaFuncionamento;
        if (horarioCliente.isBefore(LocalTime.of(1, 0))) {
            diaFuncionamento = dataCliente.minusDays(1);
        } else {
            diaFuncionamento = dataCliente;
        }

        if (diaFuncionamento.getDayOfWeek() == DayOfWeek.SATURDAY || diaFuncionamento.getDayOfWeek() == DayOfWeek.SUNDAY) {
            if (horarioCliente.equals(horarioAbertura) || horarioCliente.isAfter(horarioAbertura) || horarioCliente.isBefore(horarioFechamento)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (horarioCliente.equals(horarioAbertura) || horarioCliente.isAfter(horarioAbertura)) {
                return true;
            } else {
                return false;
            }
        }
    }
}

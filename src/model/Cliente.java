package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cliente {
    private String nome;
    private LocalDate dia;
    private LocalTime horario;
    private int qtdPessoas;

    public Cliente(String nomeCliente, LocalDate dia, LocalTime horarioCliente, int qtdPessoas) {
        this.nome = nomeCliente;
        this.dia = dia;
        this.horario = horarioCliente;
        this.qtdPessoas = qtdPessoas;
    }


    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdPessoas() {
        return qtdPessoas;
    }

    public void setQtdPessoas(int qtdPessoas) {
        this.qtdPessoas = qtdPessoas;
    }

    @Override
    public String toString() {
        return "==+== SUA RESERVA ==+==" +
                "\nNome: " + getNome() +
                "\nDia: " + getDia() +
                "\nHorário: " + getHorario() +
                "\nQuantidade de pessoas: " + getQtdPessoas();
    }
}



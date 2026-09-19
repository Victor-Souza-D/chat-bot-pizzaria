package model;

public class Cliente {
    private String nome;
    private String horario;
    private int qtdPessoas;

    public Cliente(String nomeCliente, String horarioCliente, int qtdPessoas) {
        this.nome = nomeCliente;
        this.horario = horarioCliente;
        this.qtdPessoas = qtdPessoas;
    }


    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
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
        return "Nome: " + getNome() +
                "\nHorário: " + getHorario() +
                "\nQuantidade de pessoas: " + getQtdPessoas();
    }
}



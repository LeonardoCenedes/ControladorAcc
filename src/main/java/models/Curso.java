package models;

public class Curso {
    private String nome;
    private int limiteHoras;

    public Curso(String nome, int limiteHoras) {
        this.nome = nome;
        this.limiteHoras = limiteHoras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getLimiteHoras() {
        return limiteHoras;
    }

    public void setLimiteHoras(int limiteHoras) {
        this.limiteHoras = limiteHoras;
    }
}

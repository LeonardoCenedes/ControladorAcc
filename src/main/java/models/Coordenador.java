package models;

public class Coordenador {
    private String nome;
    private String email;
    private String senha;  
    private String cpf;
    private String nomeCurso;
    private String statusConta;
    

    public Coordenador(String nome, String email, String senha, String cpf, String nomeCurso) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.nomeCurso = nomeCurso;
        this.statusConta = "Ativo";
    }

    public String getStatus() {
        return statusConta;
    }

    public void setStatus(String status) {
        this.statusConta = status;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Coordenador{" +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }

    public boolean aceitarAtividade(String atividadeId) {
        return true;
    }

    public boolean rejeitarAtividade(String atividadeId) {
        return true;
    }

    public boolean preencherNovoLimiteHoras(String atividadeId, float novoLimiteHoras, float taxaHoras) {
        return true;    
    }


}

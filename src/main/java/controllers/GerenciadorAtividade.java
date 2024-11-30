package controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.Atividade;
import models.TipoAtividade;

public class GerenciadorAtividade {
    private List<Atividade> atividades;
    private GerenciadorCursos gerenciadorCursos;

    public GerenciadorAtividade(GerenciadorCursos gerenciadorCursos) {
        this.atividades = new ArrayList<>();
        this.gerenciadorCursos = gerenciadorCursos;
    }

    public boolean preencherDadosAtividade(String nomeAtividade,LocalDateTime data, String descricao,  int raUsuario, String nomeCurso, TipoAtividade tipo, int totalHoras, String documento) {
        if (validarDadosAtividade(nomeAtividade, descricao, data, raUsuario, nomeCurso)) {
            Atividade novaAtividade = new Atividade(nomeAtividade, data, "Pendente", descricao, raUsuario, nomeCurso, tipo, totalHoras, documento);
            atividades.add(novaAtividade);
            return true;
        }
        return false;
    }

    public boolean validarDadosAtividade(String nomeAtividade, String descricao, LocalDateTime data, int raUsuario, String nome) {
        return validarNomeAtividade(nomeAtividade) &&
               validarDescricao(descricao) &&
               validarData(data) &&
               validarRa(raUsuario) &&
               gerenciadorCursos.validarNomeExistenteCurso(nome);
    }

    public boolean validarNomeAtividade(String nomeAtividade) {
        if (nomeAtividade == null || nomeAtividade.trim().isEmpty()) {
            return false;
        }
    
        int length = nomeAtividade.trim().length();
        return length >= 6 && length <= 32;
    }

    public boolean validarDescricao(String descricao) {
        if (descricao == null) {
            return false;
        }
    
        int length = descricao.trim().length();
        return length >= 0 && length <= 128;
    }

    public boolean validarData(LocalDateTime data) {
        if (data == null) {
            return false;
        }
    
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tenYearsAgo = today.minusYears(10);
    
        return !data.isAfter(today) && !data.isBefore(tenYearsAgo);
    }

    public boolean validarRa(int ra) {
        return ra != 0;
    }

    public boolean anexarDocumento(String nomeAtividade, LocalDateTime data, int raUsuario, String nomeCurso, String documento) {
        Atividade atividade = procurarAtividade(nomeAtividade, data, raUsuario, nomeCurso);
        if (atividade != null) {
            atividade.setDocumento(documento);
            return true;
        }
        return false;
    }

    public boolean validarDocumento(String documento) {
        if (documento == null) {
            return false;
        }
        return documento.toLowerCase().endsWith(".pdf");
    }

    public List<Atividade> buscarAtividades() {
        return atividades;
    }

    public boolean atualizarStatusAtividade(String nomeAtividade, LocalDateTime data, int raUsuario, String nomeCurso, String status) {
        Atividade atividade = procurarAtividade(nomeAtividade, data, raUsuario, nomeCurso);
        if (atividade != null) {
            atividade.setStatus(status);
            return true;
        }
        return false;
    }

    public List<Atividade> buscarAtividadesPorUsuario(int raUsuario) {
        List<Atividade> atividadesPorUsuario = new ArrayList<>();
        for (Atividade atividade : atividades) {
            if (atividade.getRaUsuario() == raUsuario) {
                atividadesPorUsuario.add(atividade);
            }
        }
        return atividadesPorUsuario;
    }

    public List<Atividade> buscarAtividadesPorStatus(String status) {
        List<Atividade> atividadesPorStatus = new ArrayList<>();
        for (Atividade atividade : atividades) {
            if (atividade.getStatus().equals(status)) {
                atividadesPorStatus.add(atividade);
            }
        }
        return atividadesPorStatus;
    }

    public boolean deletarAtividade(String nomeAtividade, LocalDateTime data, int raUsuario, String nomeCurso) {
        Atividade atividade = procurarAtividade(nomeAtividade, data, raUsuario, nomeCurso);
        if (atividade != null) {
            atividades.remove(atividade);
            return true;
        }
        return false;
    }

    public void removerAtividade(Atividade atividade) {
        this.atividades.remove(atividade);
    }
    
    public double calcularHoras(TipoAtividade tipo, int totalHoras) {
        return totalHoras * tipo.getCoeficienteHoras();
    }

    public Atividade procurarAtividade(String nomeAtividade, LocalDateTime data, int raUsuario, String nomeCurso) {
        for (Atividade atividade : atividades) {
            if (atividade.getNomeAtividade().equals(nomeAtividade) &&
                atividade.getDataRealizacao().equals(data) &&
                atividade.getRaUsuario() == raUsuario &&
                atividade.getNomeCurso().equals(nomeCurso)) {
                return atividade;
            }
        }
        return null;
    }

    public List<Atividade> buscarFiltradaPorRaeStatus(int ra, String status) {
        List<Atividade> atividadesFiltradas = new ArrayList<>();
        for (Atividade atividade : atividades) {
            if (atividade.getRaUsuario() == ra && atividade.getStatus().equals(status)) {
                atividadesFiltradas.add(atividade);
            }
        }
        return atividadesFiltradas;
    }

    public List<Atividade> buscarPorCurso(String nomeCurso) {
        List<Atividade> atividadesPorCurso = new ArrayList<>();
        for (Atividade atividade : atividades) {
            if (atividade.getNomeCurso().equals(nomeCurso)) {
                atividadesPorCurso.add(atividade);
            }
        }
        return atividadesPorCurso;
    }

    public List<Atividade> buscarPorCursoeStatus(String nomeCurso, String status) {
        List<Atividade> atividadesPorCursoEStatus = new ArrayList<>();
        for (Atividade atividade : atividades) {
            if (atividade.getNomeCurso().equals(nomeCurso) && atividade.getStatus().equals(status)) {
                atividadesPorCursoEStatus.add(atividade);
            }
        }
        return atividadesPorCursoEStatus;
    }
}

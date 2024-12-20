package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import controllers.GerenciadorCoordenador;
import controllers.GerenciadorCursos;
import models.Atividade;
import models.Curso;
import models.TipoAtividade;

public class SumarioAtividadeCoordenador extends JFrame {

    public SumarioAtividadeCoordenador(Atividade atividade, GerenciadorCursos gerenciadorCursos, GerenciadorCoordenador gerenciadorCoordenador) {
        // Set the title of the window
        setTitle("Sumário da Atividade");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fetch the course information using GerenciadorCursos
        Curso curso = gerenciadorCursos.buscarCurso(atividade.getNomeCurso());

        // Create labels for each item
        JLabel nomeAtividadeLabel = new JLabel("Nome da Atividade:");
        JLabel nomeAtividadeValue = new JLabel(atividade.getNomeAtividade(), SwingConstants.RIGHT);
        
        JLabel dataRealizacaoLabel = new JLabel("Data de Realização:");
        JLabel dataRealizacaoValue = new JLabel(atividade.getData().toString(), SwingConstants.RIGHT);
        
        JLabel statusLabel = new JLabel("Status:");
        JLabel statusValue = new JLabel(atividade.getStatus().toString(), SwingConstants.RIGHT);
        
        JLabel descricaoLabel = new JLabel("Descrição:");
        JLabel descricaoValue = new JLabel(atividade.getDescricao(), SwingConstants.RIGHT);
        
        JLabel totalHorasLabel = new JLabel("Total de Horas:");
        JLabel totalHorasValue = new JLabel(String.valueOf(atividade.getTotalHoras()), SwingConstants.RIGHT);
        
        JLabel raAlunoLabel = new JLabel("RA do Aluno:");
        JLabel raAlunoValue = new JLabel(String.valueOf(atividade.getRaAluno()), SwingConstants.RIGHT);
        
        JLabel cursoLabel = new JLabel("Curso:");
        JLabel cursoValue = new JLabel(curso != null ? curso.getNome() : "Curso não encontrado", SwingConstants.RIGHT);
        
        JLabel tipoLabel = new JLabel("Tipo:");
        JLabel tipoValue = new JLabel(atividade.getTipoAtividade().getNome(), SwingConstants.RIGHT);

        // Create label and button for Documento
        JLabel documentoLabel = new JLabel("Documento:");
        JButton abrirButton = new JButton("Abrir");

        // Add action listener to the abrirButton to open the document
        abrirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(atividade.getDocumento()));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(SumarioAtividadeCoordenador.this, "Erro ao abrir o documento", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create buttons for Aceitar and Negar
        JButton aceitarButton = new JButton("Aceitar");
        JButton negarButton = new JButton("Negar");

        // Disable buttons if the status is "Aprovado" or "Negado"
        if ("Aprovada".equals(atividade.getStatus()) || "Negada".equals(atividade.getStatus())) {
            aceitarButton.setEnabled(false);
            negarButton.setEnabled(false);
        }

        // Add action listener to the aceitarButton to accept the activity
        aceitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerenciadorCoordenador.aceitarAtividade(atividade.getNomeAtividade(), atividade.getData(), atividade.getRaAluno(), atividade.getNomeCurso());
                JOptionPane.showMessageDialog(SumarioAtividadeCoordenador.this, "Atividade aceita com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        // Add action listener to the negarButton to deny the activity
        negarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerenciadorCoordenador.negarAtividade(atividade.getNomeAtividade(), atividade.getData(), atividade.getRaAluno(), atividade.getNomeCurso());
                JOptionPane.showMessageDialog(SumarioAtividadeCoordenador.this, "Atividade negada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        // Create a panel and add components to it
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the contents
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(aceitarButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(negarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nomeAtividadeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(nomeAtividadeValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(dataRealizacaoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(dataRealizacaoValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(statusLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(statusValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(descricaoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(descricaoValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(totalHorasLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(totalHorasValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(raAlunoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(raAlunoValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cursoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(cursoValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(tipoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(tipoValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(documentoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(abrirButton, gbc);

        // Add the panel to the frame
        add(panel);

        // Pack the frame to fit the components
        pack();

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // For testing purposes
        GerenciadorCursos gerenciadorCursos = new GerenciadorCursos();
        GerenciadorCoordenador gerenciadorCoordenador = new GerenciadorCoordenador(null, gerenciadorCursos, null);
        Atividade atividade = new Atividade("Nome da Atividade", LocalDateTime.now(), "Aprovado", "Descrição da Atividade", 123456, "Curso de Exemplo", new TipoAtividade("Tipo de Exemplo", 10, 0.5), 10, "path/to/documento.pdf");
        new SumarioAtividadeCoordenador(atividade, gerenciadorCursos, gerenciadorCoordenador);
    }
}
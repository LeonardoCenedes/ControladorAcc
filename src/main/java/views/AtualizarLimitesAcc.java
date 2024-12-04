package views;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controllers.GerenciadorAtividade;
import controllers.GerenciadorCoordenador;
import controllers.GerenciadorCursos;
import controllers.GerenciadorEstudante;
import models.Atividade;
import models.Coordenador;
import models.Estudante;
import models.TipoAtividade;

public class AtualizarLimitesAcc extends JFrame {

    public AtualizarLimitesAcc(Coordenador coordenador, GerenciadorAtividade gerenciadorAtividade, GerenciadorEstudante gerenciadorEstudante, GerenciadorCoordenador gerenciadorCoordenador) {
        // Set the title of the window
        setTitle("Atualizar Limites de Atividades");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create labels and input fields
        JLabel maxHorasLabel = new JLabel("Máximo de Horas:");
        JTextField maxHorasField = new JTextField(20);

        // Fetch the Estudante object using the coordenador's course
        List<Estudante> estudantes = gerenciadorEstudante.buscarEstudantesPorCurso(coordenador.getNomeCurso());
        Estudante estudante = estudantes.isEmpty() ? null : estudantes.get(0);

        // Create dropdown for TipoAtividade
        List<TipoAtividade> tipoAtividades = estudante != null ? estudante.getTipoAtividades() : List.of();
        JComboBox<TipoAtividade> tipoDropdown = new JComboBox<>(tipoAtividades.toArray(new TipoAtividade[0]));
        tipoDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TipoAtividade) {
                    setText(((TipoAtividade) value).getNome());
                }
                return c;
            }
        });

        // Create update button
        JButton updateButton = new JButton("Atualizar");

        // Add action listener to the update button
        updateButton.addActionListener(e -> {
            String maxHorasText = maxHorasField.getText();
            if (maxHorasText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O campo de máximo de horas não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check for pending activities
            List<Atividade> atividadesPendentes = gerenciadorAtividade.buscarPorCursoeStatus(coordenador.getNomeCurso(), "Pendente");
            if (!atividadesPendentes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Existem atividades pendentes. Por favor, verifique todas as atividades pendentes antes de continuar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Update TipoAtividade
            TipoAtividade selectedTipo = (TipoAtividade) tipoDropdown.getSelectedItem();
            if (selectedTipo != null) {
                int maxHoras = Integer.parseInt(maxHorasText);
                boolean success = gerenciadorEstudante.atualizarTipoAtividadePorCurso(coordenador.getNomeCurso(), selectedTipo.getNome(), maxHoras);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Limite de horas atualizado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Close the window after successful update
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar o limite de horas.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create a panel and add components to it
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(maxHorasLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(maxHorasField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Tipo de Atividade:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(tipoDropdown, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(updateButton, gbc);

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
        GerenciadorEstudante gerenciadorEstudante = new GerenciadorEstudante(gerenciadorCursos);
        GerenciadorAtividade gerenciadorAtividade = new GerenciadorAtividade(gerenciadorCursos);
        GerenciadorCoordenador gerenciadorCoordenador = new GerenciadorCoordenador(gerenciadorAtividade, gerenciadorCursos, gerenciadorEstudante);
        Coordenador coordenador = new Coordenador("Nome Coordenador", "email@unesp.br", "senha123", "12345678901", "Curso Exemplo");
        new AtualizarLimitesAcc(coordenador, gerenciadorAtividade, gerenciadorEstudante, gerenciadorCoordenador);
    }
}
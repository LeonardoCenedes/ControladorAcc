package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import controllers.GerenciadorAtividade;
import controllers.GerenciadorCursos;
import models.Atividade;

public class ListaAtividadesEstudante extends JFrame {

    private JList<String> itemList;
    private JScrollPane listScrollPane;
    private JPanel panel;
    public ListaAtividadesEstudante(int raUsuario, GerenciadorAtividade gerenciadorAtividade, GerenciadorCursos gerenciadorCursos) {
        // Set the title of the window
        setTitle("Lista de Atividades");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a toolbar with four options
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); // Disable the option to detach the toolbar

        // Create buttons
        JButton todasButton = new JButton("Todas");
        JButton pendentesButton = new JButton("Pendentes");
        JButton aprovadasButton = new JButton("Aprovadas");
        JButton negadasButton = new JButton("Negadas");

        // Add action listeners to the buttons
        todasButton.addActionListener(e -> {
            List<Atividade> atividades = gerenciadorAtividade.buscarAtividadesPorUsuario(raUsuario);
            updateActivityList(atividades);
        });

        pendentesButton.addActionListener(e -> {
            List<Atividade> atividades = gerenciadorAtividade.buscarFiltradaPorRaeStatus(raUsuario, "Pendente");
            updateActivityList(atividades);
        });

        aprovadasButton.addActionListener(e -> {
            List<Atividade> atividades = gerenciadorAtividade.buscarFiltradaPorRaeStatus(raUsuario, "Aprovada");
            updateActivityList(atividades);
        });

        negadasButton.addActionListener(e -> {
            List<Atividade> atividades = gerenciadorAtividade.buscarFiltradaPorRaeStatus(raUsuario, "Negada");
            updateActivityList(atividades);
        });

        // Create a box to center the buttons
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalGlue());
        box.add(todasButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(pendentesButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(aprovadasButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(negadasButton);
        box.add(Box.createHorizontalGlue());

        // Add the box to the toolbar
        toolBar.add(box);

        // Fetch the list of activities for the user
        List<Atividade> atividades = gerenciadorAtividade.buscarAtividadesPorUsuario(raUsuario);

        // Create an array of activity names
        String[] activityNames = atividades.stream().map(Atividade::getNomeAtividade).toArray(String[]::new);

        // Create a list of clickable items
        itemList = new JList<>(activityNames);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setLayoutOrientation(JList.VERTICAL);
        itemList.setVisibleRowCount(-1);

        // Add a mouse listener for double-click events
        itemList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = itemList.locationToIndex(e.getPoint());
                    Atividade selectedAtividade = atividades.get(index);
                    new SumarioAtividadeEstudante(selectedAtividade, gerenciadorCursos, gerenciadorAtividade, ListaAtividadesEstudante.this);
                }
            }
        });

        // Create a scroll pane for the list
        listScrollPane = new JScrollPane(itemList);
        listScrollPane.setPreferredSize(new Dimension(300, 400)); // Set the preferred size of the scroll pane

        // Create a panel and add components to it
        panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(64, 64, 64, 64)); // Add padding around the contents

        // Add the toolbar to the top
        panel.add(toolBar, BorderLayout.NORTH);

        // Add the scroll pane with the list to the center
        panel.add(listScrollPane, BorderLayout.CENTER);

        // Add the panel to the frame
        add(panel);

        // Pack the frame to fit the components
        pack();

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Set the frame to be visible
        setVisible(true);

        // Initial load of activities
        updateActivityList(atividades);
    }

    public void updateActivityList(List<Atividade> atividades) {
        String[] activityNames = atividades.stream().map(Atividade::getNomeAtividade).toArray(String[]::new);
        itemList.setListData(activityNames);

        // Refresh the panel
        panel.revalidate();
        panel.repaint();
    }

    public static void main(String[] args) {
        // For testing purposes
        GerenciadorCursos gerenciadorCursos = new GerenciadorCursos();
        GerenciadorAtividade gerenciadorAtividade = new GerenciadorAtividade(gerenciadorCursos);
        new ListaAtividadesEstudante(1, gerenciadorAtividade, gerenciadorCursos);
    }
}
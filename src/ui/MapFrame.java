package ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.Grafo;
import service.DadosCapitais;

public class MapFrame extends JFrame {
    private MapPanel mapPanel;

    public MapFrame() {
        setTitle("Mapa de Capitais Brasileiras");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        Grafo grafo = new Grafo();
        DadosCapitais.inicializarConexoes(grafo);
        
        mapPanel = new MapPanel(grafo);
        add(mapPanel);
        
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MapFrame().setVisible(true);
        });
    }
}
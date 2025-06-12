package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import algorithm.AlgoritmoDijkstra;
import model.Capital;
import model.Grafo;

public class MapPanel extends JPanel {
    private Image mapImage;
    private Map<String, Point> capitalCoordinates;
    private Capital selectedCapital1;
    private Capital selectedCapital2;
    private List<Capital> pathCapitals;
    private Grafo grafo;
    private AlgoritmoDijkstra algoritmo;

    public MapPanel(Grafo grafo) {
        this.grafo = grafo;
        this.algoritmo = new AlgoritmoDijkstra(grafo);
        this.pathCapitals = new ArrayList<>();
        
        // Inicializar mapa e coordenadas
        initializeMapImage();
        initializeCapitalCoordinates();
        
        // Configurar painel
        setPreferredSize(new Dimension(800, 600));  // Aumentado para 700 pixels de altura
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getPoint());
            }
        });
    }

    private void initializeMapImage() {
        try {
            // Tenta carregar do arquivo local primeiro
            String[] possiblePaths = {
                "src/resources/mapa_brasil.jpg",
                "resources/mapa_brasil.jpg",
                "./src/resources/mapa_brasil.jpg",
                "./resources/mapa_brasil.jpg"
            };

            for (String path : possiblePaths) {
                File file = new File(path);
                if (file.exists()) {
                    mapImage = ImageIO.read(file);
                    if (mapImage != null) {
                        // Redimensiona a imagem para o tamanho do painel
                        mapImage = mapImage.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                        return;
                    }
                }
            }

            // Se não encontrou o arquivo, lança exceção
            throw new IOException("Não foi possível encontrar o arquivo mapa_brasil.jpg");

        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem do mapa: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeCapitalCoordinates() {
    capitalCoordinates = new HashMap<>();
    
    // Região Norte (Verde)
    capitalCoordinates.put("RR", new Point(250, 50));   // Boa Vista
    capitalCoordinates.put("AP", new Point(450, 50));  // Macapá
    capitalCoordinates.put("AM", new Point(200, 150));  // Manaus
    capitalCoordinates.put("PA", new Point(400, 180));  // Belém
    capitalCoordinates.put("AC", new Point(100, 250));  // Rio Branco
    capitalCoordinates.put("RO", new Point(200, 240)); // Porto Velho
    capitalCoordinates.put("TO", new Point(500, 250));  // Palmas

    // Região Nordeste (Vermelho)
    capitalCoordinates.put("MA", new Point(590, 180));  // São Luís
    capitalCoordinates.put("PI", new Point(640, 220));  // Teresina
    capitalCoordinates.put("CE", new Point(690, 180));  // Fortaleza
    capitalCoordinates.put("RN", new Point(750, 180));  // Natal
    capitalCoordinates.put("PB", new Point(745, 210));  // João Pessoa
    capitalCoordinates.put("PE", new Point(720, 220));  // Recife
    capitalCoordinates.put("AL", new Point(750, 240));  // Maceió
    capitalCoordinates.put("SE", new Point(725, 260));  // Aracaju
    capitalCoordinates.put("BA", new Point(600, 280));  // Salvador

    // Região Centro-Oeste (Laranja)
    capitalCoordinates.put("MT", new Point(380, 270));  // Cuiabá
    capitalCoordinates.put("GO", new Point(490, 330));  // Goiânia
    capitalCoordinates.put("DF", new Point(530, 325));  // Brasília
    capitalCoordinates.put("MS", new Point(380, 400));  // Campo Grande

    // Região Sudeste (Amarelo)
    capitalCoordinates.put("MG", new Point(600, 390));  // Belo Horizonte
    capitalCoordinates.put("ES", new Point(660, 390));  // Vitória
    capitalCoordinates.put("RJ", new Point(640, 420));  // Rio de Janeiro
    capitalCoordinates.put("SP", new Point(500, 420));  // São Paulo

    // Região Sul (Roxo)
    capitalCoordinates.put("PR", new Point(450, 450));  // Curitiba
    capitalCoordinates.put("SC", new Point(460, 490));  // Florianópolis
    capitalCoordinates.put("RS", new Point(450, 520));  // Porto Alegre
}

    private void handleMouseClick(Point clickPoint) {
        for (Map.Entry<String, Point> entry : capitalCoordinates.entrySet()) {
            Point capitalPoint = entry.getValue();
            if (clickPoint.distance(capitalPoint) < 10) {
                Capital capital = findCapitalByUF(entry.getKey());
                if (selectedCapital1 == null) {
                    selectedCapital1 = capital;
                } else if (selectedCapital2 == null && capital != selectedCapital1) {
                    selectedCapital2 = capital;
                    calculateAndDrawPath();
                } else {
                    selectedCapital1 = capital;
                    selectedCapital2 = null;
                    pathCapitals.clear();
                }
                repaint();
                break;
            }
        }
    }

    private Capital findCapitalByUF(String uf) {
        for (Capital capital : grafo.getCapitais()) {
            if (capital.getEstado().equals(uf)) {
                return capital;
            }
        }
        return null;
    }

    private void calculateAndDrawPath() {
        if (selectedCapital1 != null && selectedCapital2 != null) {
            AlgoritmoDijkstra.Resultado resultado = algoritmo.calcularMenorCaminho(selectedCapital1, selectedCapital2);
            pathCapitals = resultado.getCaminho();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Desenhar mapa
        if (mapImage != null) {
            // Calcula as dimensões para manter a proporção da imagem
            Dimension size = getSize();
            double scaleX = (double) size.width / mapImage.getWidth(null);
            double scaleY = (double) size.height / mapImage.getHeight(null);
            double scale = Math.min(scaleX, scaleY);

            int width = (int) (mapImage.getWidth(null) * scale);
            int height = (int) (mapImage.getHeight(null) * scale);
            int x = (size.width - width) / 2;
            int y = (size.height - height) / 2;

            g2d.drawImage(mapImage, x, y, width, height, this);
        } else {
            // Se não houver imagem, desenha um fundo branco
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Desenhar capitais
        g2d.setColor(Color.BLACK);
        for (Map.Entry<String, Point> entry : capitalCoordinates.entrySet()) {
            Point p = entry.getValue();
            g2d.fillOval(p.x - 4, p.y - 4, 8, 8);  // Aumentado para 8 pixels
            
            // Desenhar sigla do estado com fundo branco para melhor legibilidade
            String sigla = entry.getKey();
            g2d.setFont(new Font("Arial", Font.BOLD, 14));  // Aumentado para 14
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(sigla);
            
            g2d.setColor(Color.WHITE);
            g2d.fillRect(p.x - textWidth/2 - 3, p.y - 22, textWidth + 6, 16);

            g2d.setColor(Color.BLACK);
            g2d.drawString(sigla, p.x - textWidth/2, p.y - 10);  // Texto mais próximo do ponto
        }

        // Desenhar linhas do caminho
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));
        if (!pathCapitals.isEmpty()) {
            for (int i = 0; i < pathCapitals.size() - 1; i++) {
                Point p1 = capitalCoordinates.get(pathCapitals.get(i).getEstado());
                Point p2 = capitalCoordinates.get(pathCapitals.get(i + 1).getEstado());
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        // Destacar capitais selecionadas
        if (selectedCapital1 != null) {
            Point p = capitalCoordinates.get(selectedCapital1.getEstado());
            g2d.setColor(Color.BLUE);
            g2d.fillOval(p.x - 7, p.y - 7, 14, 14);  // Aumentado para 14 pixels
        }

        if (selectedCapital2 != null) {
            Point p = capitalCoordinates.get(selectedCapital2.getEstado());
            g2d.setColor(Color.GREEN);
            g2d.fillOval(p.x - 7, p.y - 7, 14, 14);  // Aumentado para 14 pixels
        }

        // Mostrar distância total
        if (selectedCapital1 != null && selectedCapital2 != null) {
            AlgoritmoDijkstra.Resultado resultado = algoritmo.calcularMenorCaminho(selectedCapital1, selectedCapital2);
            String info = String.format("Distância: %d km", resultado.getDistanciaTotal());
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString(info, 10, 20);
        }
    }
}
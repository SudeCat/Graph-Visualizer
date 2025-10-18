import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphVisualizer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java GraphVisualizer <filename>");
            return;
        }

        String filename = args[0];
        List<List<Integer>> adjacencyMatrix = readAdjacencyMatrix(filename);

        EventQueue.invokeLater(() -> {
            GraphFrame frame = new GraphFrame(adjacencyMatrix);
            frame.setVisible(true);
        });
    }

    private static List<List<Integer>> readAdjacencyMatrix(String filename) {
        List<List<Integer>> adjacencyMatrix = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                List<Integer> row = new ArrayList<>();
                for (int i = 1; i < values.length; i++) {
                    row.add(Integer.parseInt(values[i]));
                }
                adjacencyMatrix.add(row);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }

        return adjacencyMatrix;
    }
}

class GraphFrame extends Frame {
    private static final int NODE_SIZE = 30;
    private final List<List<Integer>> adjacencyMatrix;

    GraphFrame(List<List<Integer>> adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
        setTitle("Graph Visualizer");
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int numNodes = adjacencyMatrix.size();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - NODE_SIZE * 2;

        for (int i = 0; i < numNodes; i++) {
            int x = (int) (centerX + radius * Math.cos(2 * Math.PI * i / numNodes));
            int y = (int) (centerY + radius * Math.sin(2 * Math.PI * i / numNodes));

            g.setColor(Color.BLUE);
            g.fillOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE+30, NODE_SIZE+50);
            g.setColor(Color.WHITE);
            Font font = new Font("Arial", Font.PLAIN, 35); //
            g.setFont(font);
            g.drawString(String.valueOf(i + 1), x + 5, y + 20); //


            List<Integer> row = adjacencyMatrix.get(i);
            for (int j = 0; j < row.size(); j++) {
                int weight = row.get(j);
                if (weight != 0) {
                    int targetX = (int) (centerX + radius * Math.cos(2 * Math.PI * j / numNodes));
                    int targetY = (int) (centerY + radius * Math.sin(2 * Math.PI * j / numNodes));

                    g.setColor(Color.PINK);
                    drawArrow(g, x+20, y+20, targetX, targetY, 10, i, j);
                    g.drawString(String.valueOf(weight), (x + targetX) / 2, (y + targetY) / 2);
                }
            }
        }
    }

    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2, int size, int sourceNode, int targetNode) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke(2));

        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowSize = size / 2;
        int dx = (int) (arrowSize * Math.cos(angle));
        int dy = (int) (arrowSize * Math.sin(angle));

        if (sourceNode != targetNode) {
            g2.drawLine(x1, y1, x2, y2);
            g2.drawLine(x2, y2, x2 - dx - dy, y2 - dy + dx);
            g2.drawLine(x2, y2, x2 - dx + dy, y2 - dy - dx);
        } else {
            g2.drawLine(x1, y1, x2 - dx, y2 - dy);
            g2.drawLine(x2 - dx, y2 - dy, x2 - dx - dy, y2 - dy + dx);
            g2.drawLine(x2 - dx, y2 - dy, x2 - dx + dy, y2 - dy - dx);
        }

        g2.dispose();
    }
}
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java GraphVisualizerJUNG <filename>");
            return;
        }

        String filename = args[0];
        List<List<Integer>> adjacencyMatrix = readAdjacencyMatrix(filename);

        EventQueue.invokeLater(() -> {
            visualizeGraph(adjacencyMatrix);
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

    private static void visualizeGraph(List<List<Integer>> adjacencyMatrix) {
        int numNodes = adjacencyMatrix.size();

        DirectedSparseGraph<Integer, Integer> graph = new DirectedSparseGraph<>();
        for (int i = 0; i < numNodes; i++) {
            graph.addVertex(i + 1);
        }

        int edgeCount = 0;
        for (int i = 0; i < numNodes; i++) {
            List<Integer> row = adjacencyMatrix.get(i);
            for (int j = 0; j < row.size(); j++) {
                int weight = row.get(j);
                if (weight != 0) {
                    graph.addEdge(edgeCount++, i + 1, j + 1);
                }
            }
        }

        Layout<Integer, Integer> layout = new CircleLayout<>(graph);
        layout.setSize(new Dimension(800, 600));

        VisualizationViewer<Integer, Integer> vv = new VisualizationViewer<>(layout);
        vv.setPreferredSize(new Dimension(1000, 800));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
        vv.getRenderContext().setEdgeLabelTransformer(edge -> {
            int source = graph.getSource(edge);
            int target = graph.getDest(edge);
            int weight = adjacencyMatrix.get(source - 1).get(target - 1);
            return String.valueOf(weight);
        });
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        JFrame frame = new JFrame("Graph Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}

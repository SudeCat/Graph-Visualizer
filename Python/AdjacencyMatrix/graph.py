import csv
import sys
import networkx as nx
import matplotlib.pyplot as plt


def read_adjacency_matrix(file_path):
    adjacency_matrix = []
    with open(file_path, 'r') as file:
        reader = csv.reader(file)
        for row in reader:
            row = [int(weight) if weight != '' else 0 for weight in row[1:]]
            adjacency_matrix.append(row)
    return adjacency_matrix


def visualize_graph(adjacency_matrix):
    num_nodes = len(adjacency_matrix)

    graph = nx.DiGraph()
    for i in range(num_nodes):
        graph.add_node(i + 1)

    for i in range(num_nodes):
        row = adjacency_matrix[i]
        for j in range(len(row)):
            weight = row[j]
            if weight > 0:
                if i != j:
                    graph.add_edge(i + 1, j + 1, weight=weight)
                else:
                    graph.add_edge(i + 1, j + 1, weight=weight, self_loop=True)

    pos = nx.circular_layout(graph)

    plt.figure(figsize=(8, 6))
    nx.draw_networkx_nodes(graph, pos, node_color='lightblue', node_size=1000)
    nx.draw_networkx_labels(graph, pos, font_size=15)

    for edge in graph.edges():
        x1, y1 = pos[edge[0]]
        x2, y2 = pos[edge[1]]
        weight1 = graph[edge[0]][edge[1]]['weight']

        if graph.has_edge(edge[1], edge[0]):
            weight2 = graph[edge[1]][edge[0]]['weight']
        else:
            weight2 = None

        if edge[0] != edge[1] and weight2 is not None and weight1 != weight2:
            mid_x = (x1 + x2) / 2
            mid_y = (y1 + y2) / 2
            dx = y2 - y1
            dy = x1 - x2
            plt.text(mid_x + dx * 0.09, mid_y + dy * 0.09, str(weight1), ha='center', va='center', color='gray')
            plt.annotate("", xy=(x2, y2), xytext=(x1 + dx * 0.05, y1 + dy * 0.05),
                         arrowprops=dict(arrowstyle="->",connectionstyle="arc3,rad=0.2", color='gray'), color='gray')

            plt.text(mid_x - dx * 0.09, mid_y - dy * 0.09, str(weight2), ha='center', va='center', color='gray')
            plt.annotate("", xy=(x1, y1), xytext=(x2 - dx * 0.05, y2 - dy * 0.05),
                         arrowprops=dict(arrowstyle="->",connectionstyle="arc3,rad=0.2", color='gray'), color='gray')
        else:
            color = 'black'

            if edge[0] == edge[1]:
                nx.draw_networkx_edges(graph, pos, edgelist=[edge], arrowstyle="->", connectionstyle="arc3,rad=0.2",
                                       width=1.5, alpha=0.5, edge_color='red')
                plt.text(x1, y1 + 0.15, str(weight1), ha='center', va='center', color='red')
            else:
                if color != 'gray':
                    plt.annotate("", xy=(x2, y2), xytext=(x1, y1), arrowprops=dict(arrowstyle="->", color=color),
                                 color=color)
                    plt.text((x1 + x2) / 2, (y1 + y2) / 2, str(weight1), ha='center', va='center', color=color)

    plt.axis('off')
    plt.show()


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python program.py")
        sys.exit(1)

    file_path = sys.argv[1]
    adjacency_matrix = read_adjacency_matrix(file_path)

    if len(adjacency_matrix) < 3:
        print("Not enough nodes to draw the graph.")
        sys.exit(1)

    visualize_graph(adjacency_matrix)

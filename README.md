# Graph-Visualizer
 A cross-language project demonstrating graph visualization from adjacency matrices, implemented in both  **Java ☕** and **Python 🐍** . This repository showcases how directed and weighted graphs can be read from a CSV or text-based adjacency matrix file and visualized using different graphical frameworks.
 
## 🧩 Project Overview

GraphVision helps students, developers, and researchers **visualize graph data** easily.  
It supports both **custom Java AWT rendering** and **Python NetworkX visualization**, offering flexibility for educational or analytical use.

- 📈 Visualize **directed weighted graphs** from `.csv` or `.txt` adjacency matrices  
- 💡 Implemented in **both Java and Python** for cross-language learning  
- 🎯 Compare manual graphics (Java AWT) vs. library-based visualization (JUNG, NetworkX)  
- 🖼️ Includes example datasets and ready-to-run scripts
- 
---

## 📊 Input Format

Your input file should represent an **adjacency matrix** with edge weights.  
Example (`sample_graph.csv`):

```csv
,1,2,3,4
1,0,5,0,2
2,0,0,3,0
3,1,0,0,4
4,0,0,0,0
```
## ⚡ 1. Java Implementations

### GraphVisualizer.java – AWT-Based Renderer
Custom rendering using Java’s AWT and 2D Graphics APIs.

### 🔍 Features
- Manual circular node layout
- Directed edges with arrowheads 
- Displays edge weights
- Handles self-loops and bidirectional edges
```bash
cd java
javac GraphVisualizer.java
java GraphVisualizer sample_graph.csv
```
### GraphVisualizerJUNG.java – JUNG Framework Renderer
Uses JUNG (Java Universal Network/Graph Framework) for layout-based visualization.

### 🔍 Features
- Circular layout
- Automatic node/edge rendering
- Edge weight labeling
- Interactive zoom and drag features
```bash
cd java
javac -cp ".:jung-algorithms.jar:jung-visualization.jar" GraphVisualizerJUNG.java
java -cp ".:jung-algorithms.jar:jung-visualization.jar" GraphVisualizerJUNG sample_graph.csv
```
## 🐍 2. Python Implementations

Built with NetworkX and Matplotlib, this script visualizes graphs with automatic layouts and labeled edge weights.

### 🔍 Features
- Directed graph visualization
- Weighted edges and self-loops
- Handles bidirectional edges gracefully
- Displays node labels and colors
```bash
cd python
python graph_visualizer.py sample_graph.csv
pip install networkx matplotlib
```
## End of the README

Feel free to explore each code in detail, modify parameters as needed. If you have questions or suggestions, please open an issue or submit a pull request.
- **Email:** [catsudeebrar@gmail.com](mail)

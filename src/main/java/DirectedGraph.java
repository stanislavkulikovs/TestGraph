import java.util.List;

public class DirectedGraph extends UndirectedGraph {

    @Override
    public void addEdge(String vertexName1, String vertexName2) {
        List<Vertex> edges1 = vertexMap.get(addVertex(vertexName1));
        edges1.add(addVertex(vertexName2));
    }

    @Override
    public void removeEdge(String vertexName1, String vertexName2) {
        if (!hasEdge(vertexName1, vertexName2)) return;
        List<Vertex> edges1 = vertexMap.get(new Vertex(vertexName1));
        edges1.remove(new Vertex(vertexName2));
    }

    @Override
    public void removeVertex(String vertexName) {
        if (!hasVertex(vertexName)) return;
        Vertex vertex = new Vertex(vertexName);
        vertexMap.values().forEach(vertexEdge -> vertexEdge.remove(vertex));
        vertexMap.remove(vertex);
    }
}

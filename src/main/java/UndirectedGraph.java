import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class UndirectedGraph {
    protected HashMap<Vertex, ArrayList<Vertex>> vertexMap = new HashMap<Vertex, ArrayList<Vertex>>();

    public boolean hasVertex(String vertexName) {
        return vertexMap.containsKey(new Vertex(vertexName));
    }

    public Vertex addVertex(String vertexName) {
        Vertex newVertex = new Vertex(vertexName);
        vertexMap.putIfAbsent(newVertex, new ArrayList<>());
        return newVertex;
    }

    public boolean hasEdge(String vertexName1, String vertexName2) {
        if (!hasVertex(vertexName1)) return false;
        List<Vertex> edges = vertexMap.get(new Vertex(vertexName1));
        return edges.contains(new Vertex(vertexName2));
    }

    public void addEdge(String vertexName1, String vertexName2) {
        Vertex vertex1 = addVertex(vertexName1);
        Vertex vertex2 = addVertex(vertexName2);
        List<Vertex> edges1 = vertexMap.get(vertex1);
        List<Vertex> edges2 = vertexMap.get(vertex2);
        edges1.add(vertex2);
        edges2.add(vertex1);
    }

    public void removeVertex(String vertexName) {
        if (!hasVertex(vertexName)) return;
        Vertex vertex = new Vertex(vertexName);
        vertexMap.get(vertex).forEach(vertexEdge -> vertexMap.get(vertexEdge).remove(vertex));
        vertexMap.remove(vertex);
    }

    public void removeEdge(String vertexName1, String vertexName2) {
        if (!hasEdge(vertexName1, vertexName2)) return;
        Vertex vertex1 = new Vertex(vertexName1);
        Vertex vertex2 = new Vertex(vertexName2);
        List<Vertex> edges1 = vertexMap.get(vertex1);
        List<Vertex> edges2 = vertexMap.get(vertex2);
        edges1.remove(vertex2);
        edges2.remove(vertex1);
    }

    public List<Vertex> getPath(String vertexName1, String vertexName2) {
        if (!hasVertex(vertexName1) || !hasVertex(vertexName2)) return emptyList();
        Pair<Boolean, List<Vertex>> result = deepSearch(vertexName1, vertexName2, singletonList(new Vertex(vertexName1)));
        return result.getKey() ? result.getValue() : emptyList();
    }

    private Pair<Boolean, List<Vertex>> deepSearch(String vertexName1, String vertexName2, List<Vertex> visited) {
        Vertex vertex1 = new Vertex(vertexName1);
        Vertex vertex2 = new Vertex(vertexName2);

        return hasEdge(vertexName1, vertexName2) ? new Pair<>(true, getCollect(visited, vertex2)) : vertexMap.get(vertex1)
                .parallelStream()
                .filter(vertexEdge -> !visited.contains(vertexEdge))
                .map(vertexEdge -> deepSearch(vertexEdge.getName(), vertexName2, getCollect(visited, vertexEdge)))
                .filter(Pair::getKey)
                .findFirst()
                .orElse(new Pair<>(false, emptyList()));
    }

    private List<Vertex> getCollect(List<Vertex> visited, Vertex vertex2) {
        return Stream.concat(visited.stream(), Stream.of(vertex2))
                .collect(Collectors.toList());
    }
}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {
    private DirectedGraph graph;

    @BeforeEach
    void setUp() {
        graph = new DirectedGraph();
    }

    @Test
    void addEdge() {
        assertFalse(graph.hasEdge("first", "second"));

        graph.addEdge("first", "second");

        assertTrue(graph.hasEdge("first", "second"));
        assertFalse(graph.hasEdge("second", "first"));
    }

    @Test
    void removeVertex() {
        graph.addEdge("second", "first");
        graph.addEdge("third", "first");
        graph.addEdge("third", "second");

        assertTrue(graph.hasEdge("second", "first"));
        assertTrue(graph.hasEdge("third", "first"));
        assertTrue(graph.hasEdge("third", "second"));

        graph.removeVertex("first");

        assertTrue(graph.hasVertex("second"));
        assertTrue(graph.hasVertex("third"));
        assertFalse(graph.hasVertex("first"));
        assertFalse(graph.hasEdge("second", "first"));
        assertFalse(graph.hasEdge("third", "first"));
        assertTrue(graph.hasEdge("third", "second"));
    }

    @Test
    void removeEdge() {
        graph.addEdge("first", "second");
        graph.addEdge("first", "third");

        assertTrue(graph.hasEdge("first", "second"));
        assertTrue(graph.hasEdge("first", "third"));

        graph.removeEdge("first", "second");

        assertTrue(graph.hasVertex("first"));
        assertTrue(graph.hasVertex("second"));
        assertTrue(graph.hasVertex("third"));
        assertFalse(graph.hasEdge("first", "second"));
        assertTrue(graph.hasEdge("first", "third"));
    }

    @Test
    void getPath_simpleGraph() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        List<Vertex> expectedPath = asList(
                new Vertex("A"),
                new Vertex("B"),
                new Vertex("C"),
                new Vertex("D")
        );

        assertEquals(expectedPath, graph.getPath("A", "D"));
    }

    @Test
    void getPath_wrongDirection() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        assertEquals(emptyList(), graph.getPath("D", "A"));
    }

    @Test
    void getPath_manyBranches() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("B", "D");
        graph.addEdge("B", "E");
        graph.addEdge("D", "H");
        graph.addEdge("E", "F");
        graph.addEdge("E", "G");

        List<Vertex> expectedPath = asList(
                new Vertex("A"),
                new Vertex("B"),
                new Vertex("E"),
                new Vertex("G")
        );

        assertEquals(expectedPath, graph.getPath("A", "G"));
    }
}
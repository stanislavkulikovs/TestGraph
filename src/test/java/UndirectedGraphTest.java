import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class UndirectedGraphTest {
    private UndirectedGraph graph;

    @BeforeEach
    void setUp() {
        graph = new UndirectedGraph();
    }

    @Test
    void addVertex() {
        assertFalse(graph.hasVertex("first"));

        graph.addVertex("first");

        assertTrue(graph.hasVertex("first"));
    }

    @Test
    void addEdge() {
        assertFalse(graph.hasEdge("first", "second"));

        graph.addEdge("first", "second");

        assertTrue(graph.hasEdge("first", "second"));
        assertTrue(graph.hasEdge("second", "first"));
    }

    @Test
    void removeVertex() {
        graph.addEdge("first", "second");
        graph.addEdge("first", "third");

        assertTrue(graph.hasEdge("first", "second"));
        assertTrue(graph.hasEdge("second", "first"));
        assertTrue(graph.hasEdge("first", "third"));
        assertTrue(graph.hasEdge("third", "first"));

        graph.removeVertex("first");

        assertTrue(graph.hasVertex("second"));
        assertTrue(graph.hasVertex("third"));
        assertFalse(graph.hasVertex("first"));
        assertFalse(graph.hasEdge("first", "second"));
        assertFalse(graph.hasEdge("second", "first"));
        assertFalse(graph.hasEdge("first", "third"));
        assertFalse(graph.hasEdge("third", "first"));
    }

    @Test
    void removeNonexistentVertex() {
        assertDoesNotThrow(() -> graph.removeVertex("any"));
    }

    @Test
    void removeEdge() {
        graph.addEdge("first", "second");
        graph.addEdge("first", "third");

        assertTrue(graph.hasEdge("first", "second"));
        assertTrue(graph.hasEdge("second", "first"));
        assertTrue(graph.hasEdge("first", "third"));
        assertTrue(graph.hasEdge("third", "first"));

        graph.removeEdge("first", "second");

        assertTrue(graph.hasVertex("first"));
        assertTrue(graph.hasVertex("second"));
        assertTrue(graph.hasVertex("third"));
        assertFalse(graph.hasEdge("first", "second"));
        assertFalse(graph.hasEdge("second", "first"));
        assertTrue(graph.hasEdge("first", "third"));
        assertTrue(graph.hasEdge("third", "first"));
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
    void getPath_reverseDirection() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        List<Vertex> expectedPath = asList(
                new Vertex("D"),
                new Vertex("C"),
                new Vertex("B"),
                new Vertex("A")
        );

        assertEquals(expectedPath, graph.getPath("D", "A"));
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

    @Test
    void getPath_roundGraph() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");

        List<Vertex> expectedPath1 = asList(
                new Vertex("A"),
                new Vertex("B"),
                new Vertex("D")
        );

        List<Vertex> expectedPath2 = asList(
                new Vertex("A"),
                new Vertex("c"),
                new Vertex("D")
        );

        assertTrue(asList(expectedPath1, expectedPath2).contains(graph.getPath("A", "D")));
    }

    @Test
    void getPath_disconnectedGraph() {
        graph.addEdge("A", "B");
        graph.addEdge("C", "D");

        assertEquals(emptyList(), graph.getPath("A", "D"));
    }
}
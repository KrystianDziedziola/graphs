package edu.uz.graphs.repository;

import edu.uz.graphs.model.graph.Graph;
import org.springframework.stereotype.Repository;

@Repository
public class GraphRepository {

    private Graph graph = new Graph();

    public void update(final Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }
}

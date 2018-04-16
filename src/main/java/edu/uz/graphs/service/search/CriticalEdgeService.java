package edu.uz.graphs.service.search;

import edu.uz.graphs.model.graph.Edge;
import edu.uz.graphs.model.graph.Graph;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CriticalEdgeService {

    public Set<Edge> find(final Graph graph) {
        final Set<Edge> edges = graph.getEdges();
        final Set<Edge> criticalEdges = new HashSet<>();
        edges.forEach(edge -> {
            if (graph.isBridge(edge)) {
                criticalEdges.add(edge);
            }
        });

        return criticalEdges;
    }
}

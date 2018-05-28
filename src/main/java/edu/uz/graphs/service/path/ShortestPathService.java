package edu.uz.graphs.service.path;

import edu.uz.graphs.model.PathResult;
import edu.uz.graphs.model.graph.Graph;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShortestPathService {

    public List<PathResult> findAllPaths(final String rootNode, final Graph graph) {
        final List<String> destinationNodes =
                graph.getVertices()
                        .stream()
                        .filter(vertex -> !vertex.equals(rootNode))
                        .collect(Collectors.toList());

        return destinationNodes.stream()
                .map(destinationNode -> graph.findShortestPath(rootNode, destinationNode))
                .collect(Collectors.toList());
    }
}

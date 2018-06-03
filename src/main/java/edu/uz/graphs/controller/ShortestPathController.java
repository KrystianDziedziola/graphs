package edu.uz.graphs.controller;

import edu.uz.graphs.model.graph.Edge;
import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.input.SearchParameters;
import edu.uz.graphs.model.path.PathResult;
import edu.uz.graphs.model.representation.PathResultRepresentation;
import edu.uz.graphs.repository.GraphRepository;
import edu.uz.graphs.service.path.ShortestPathService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShortestPathController {

    private final GraphRepository graphRepository;
    private final ShortestPathService shortestPathService;

    public ShortestPathController(final GraphRepository graphRepository,
        final ShortestPathService shortestPathService) {
        this.graphRepository = graphRepository;
        this.shortestPathService = shortestPathService;
    }

    @GetMapping(value = "/path")
    public String path(final Model model) {
        final Graph graph = graphRepository.getGraph();
//        final Graph graph = exampleGraph();
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("searchParameters", new SearchParameters());
        return "path";
    }

    @PostMapping(value = "/path")
    public String shortestPathSearch(final Model model,
        @ModelAttribute final SearchParameters parameters) {
        final Graph graph = graphRepository.getGraph();
//        final Graph graph = exampleGraph();
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());

        final String rootNode = parameters.getRoot();
        final List<PathResult> allPaths = shortestPathService
            .findShortestPathsToEveryNode(rootNode, graph);
        model.addAttribute("result", PathResultRepresentation.of(allPaths));

        return "path";
    }

    private Graph exampleGraph() {
        final List<String> vertices = Arrays.asList("A", "B", "C", "D", "E");
        final List<Edge> edges = Arrays.asList(
            new Edge("A", "B", 6),
            new Edge("A", "D", 1),
            new Edge("B", "C", 5),
            new Edge("B", "E", 2),
            new Edge("B", "D", 2),
            new Edge("E", "D", 1),
            new Edge("E", "C", 5)
        );

        return new Graph(vertices, new HashSet<>(edges));
    }


}

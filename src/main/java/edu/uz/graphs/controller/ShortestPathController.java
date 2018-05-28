package edu.uz.graphs.controller;

import edu.uz.graphs.model.PathResult;
import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.input.SearchParameters;
import edu.uz.graphs.model.representation.PathResultRepresentation;
import edu.uz.graphs.repository.GraphRepository;
import edu.uz.graphs.service.path.ShortestPathService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ShortestPathController {

    private final GraphRepository graphRepository;
    private final ShortestPathService shortestPathService;

    public ShortestPathController(GraphRepository graphRepository, ShortestPathService shortestPathService) {
        this.graphRepository = graphRepository;
        this.shortestPathService = shortestPathService;
    }

    @GetMapping(value = "/path")
    public String path(final Model model) {
        final Graph graph = graphRepository.getGraph();
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("searchParameters", new SearchParameters());
        return "path";
    }

    @PostMapping(value = "/path")
    public String shortestPathSearch(final Model model, @ModelAttribute final SearchParameters parameters) {
        final Graph graph = graphRepository.getGraph();
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());

        final String rootNode = parameters.getRoot();
        final List<PathResult> allPaths = shortestPathService.findAllPaths(rootNode, graph);
        model.addAttribute("result", PathResultRepresentation.of(allPaths));

        return "path";
    }
}

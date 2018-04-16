package edu.uz.graphs.controller;

import edu.uz.graphs.model.graph.Edge;
import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.input.SearchParameters;
import edu.uz.graphs.repository.GraphRepository;
import edu.uz.graphs.service.search.CriticalEdgeService;
import edu.uz.graphs.service.search.DeepFirstSearch;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SearchController {

    private final GraphRepository graphRepository;
    private final CriticalEdgeService criticalEdgeService;
    private final DeepFirstSearch deepFirstSearchService;

    public SearchController(final GraphRepository graphRepository,
        final CriticalEdgeService criticalEdgeService,
        final DeepFirstSearch deepFirstSearchService) {
        this.graphRepository = graphRepository;
        this.criticalEdgeService = criticalEdgeService;
        this.deepFirstSearchService = deepFirstSearchService;
    }

    @GetMapping(value = "/search")
    public String search(final Model model) {
        final Graph graph = graphRepository.getGraph();
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("searchParameters", new SearchParameters());
        return "search";
    }

    @PostMapping(value = "/search", params = "action=deepFirstSearch")
    public String deepFirstSearch(final Model model,
        @ModelAttribute final SearchParameters parameters) {

        final Graph graph = graphRepository.getGraph();
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());

        final List<String> traverseOrder = deepFirstSearchService
            .traverse(parameters.getRoot(), graph);
        model.addAttribute("searchResult", traverseOrder);

        return "search";
    }

    @PostMapping(value = "/search", params = "action=criticalEdgeSearch")
    public String criticalEdgeSearch(final Model model,
        @ModelAttribute final SearchParameters parameters) {
        final Graph graph = graphRepository.getGraph();
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());

        final Set<Edge> criticalEdges = criticalEdgeService.find(graph);
        model.addAttribute("searchResult", criticalEdges);

        return "search";
    }
}

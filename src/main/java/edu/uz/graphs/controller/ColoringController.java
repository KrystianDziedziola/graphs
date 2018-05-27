package edu.uz.graphs.controller;

import edu.uz.graphs.model.graph.Color;
import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.graph.Vertex;
import edu.uz.graphs.model.input.SortingInput;
import edu.uz.graphs.repository.GraphRepository;
import edu.uz.graphs.service.coloring.GraphColoringService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ColoringController {

    private final GraphRepository graphRepository;
    private final GraphColoringService coloringService;

    public ColoringController(final GraphRepository graphRepository,
        final GraphColoringService coloringService) {
        this.graphRepository = graphRepository;
        this.coloringService = coloringService;
    }

    @GetMapping(value = "/coloring")
    public String coloring(final Model model) {
        final Graph graph = graphRepository.getGraph();
        model.addAttribute("sorting", new SortingInput());
        model.addAttribute("vertices", toColoredVertices(graph));
        model.addAttribute("edges", graph.getEdges());
        return "coloring";
    }

    private List<Vertex> toColoredVertices(final Graph graph) {
        return graph
            .getVertices()
            .stream()
            .map(vertex -> new Vertex(vertex, Color.GRAY))
            .collect(Collectors.toList());
    }

    @PostMapping(value = "/coloring")
    public String colorGraph(@ModelAttribute final SortingInput sorting, final Model model) {
        final Graph graph = graphRepository.getGraph();

        final List<Vertex> coloredVertices = coloringService.color(graph, sorting.getType());
        model.addAttribute("vertices", coloredVertices);
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("result", coloredVertices);
        model.addAttribute("sorting", sorting);
        return "coloring";
    }
}

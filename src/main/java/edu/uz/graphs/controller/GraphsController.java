package edu.uz.graphs.controller;

import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.input.EdgeInput;
import edu.uz.graphs.model.input.Input;
import edu.uz.graphs.service.GraphService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GraphsController {

    private final GraphService graphService;

    private Graph graph = new Graph();

    public GraphsController(final GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping(value = "/")
    public String index(final Model model) {
        model.addAttribute("input", new Input());
        model.addAttribute("edge", new EdgeInput());
        return "index";
    }

    @PostMapping(value = "/create")
    public String createGraph(@ModelAttribute final Input input, final Model model) {
        graph = graphService.createGraphFromNodeAdjacency(input.getText(), input.getType());

        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("edge", new EdgeInput());

        return "index";
    }

    @PostMapping(value = "/addEdge")
    public String addEdge(@ModelAttribute final EdgeInput edge, final Model model) {
        graph = graphService.addEdge(graph, edge);
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("input", new Input());
        model.addAttribute("edge", new EdgeInput());
        return "index";
    }
}

package edu.uz.graphs.controller;

import edu.uz.graphs.model.graph.Graph;
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

    public GraphsController(final GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping(value = "/")
    public String index(final Model model) {
        model.addAttribute("input", new Input());
        return "index";
    }

    @PostMapping(value = "/")
    public String submit(@ModelAttribute final Input input, final Model model) {
        final Graph graph = graphService.createGraph(input.getText(), input.getType());

        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());

        return "index";
    }
}

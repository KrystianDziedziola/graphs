package edu.uz.graphs.controller;

import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.input.EdgeInput;
import edu.uz.graphs.model.input.Input;
import edu.uz.graphs.model.input.VertexInput;
import edu.uz.graphs.repository.GraphRepository;
import edu.uz.graphs.service.GraphService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GraphsController {

    private final GraphService graphService;
    private final GraphRepository graphRepository;

    private Input input = new Input();

    public GraphsController(final GraphService graphService,
        final GraphRepository graphRepository) {
        this.graphService = graphService;
        this.graphRepository = graphRepository;
    }

    @GetMapping(value = "/")
    public String index(final Model model) {
        model.addAttribute("input", new Input());
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());
        return "index";
    }

    @PostMapping(value = "/graph", params = "action=create")
    public String createGraph(@ModelAttribute final Input input, final Model model) {
        this.input = input;
        final Graph graph = graphService.create(input.getText(), input.getType());
        graphRepository.update(graph);

        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());

        return "index";
    }

    @PostMapping(value = "/graph", params = "action=clear")
    public String clearGraph(@ModelAttribute final Input input, final Model model) {
        final Graph graph = graphRepository.getGraph();

        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());

        return "index";
    }

    @PostMapping(value = "/edges", params = "action=add")
    public String addEdge(@ModelAttribute final EdgeInput edge, final Model model) {
        final Graph graph = graphRepository.getGraph();
        graph.addEdge(edge.getSource(), edge.getTarget(), edge.getWeight());
        graphRepository.update(graph);

        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("input", input);
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());
        return "index";
    }

    @PostMapping(value = "/edges", params = "action=remove")
    public String removeEdge(@ModelAttribute final EdgeInput edge, final Model model) {
        final Graph graph = graphRepository.getGraph();
        graph.removeEdge(edge.getSource(), edge.getTarget());
        graphRepository.update(graph);

        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("input", input);
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());
        return "index";
    }

    @PostMapping(value = "/vertices", params = "action=add")
    public String addVertex(@ModelAttribute final VertexInput vertex, final Model model) {
        final Graph graph = graphRepository.getGraph();
        graph.addVertex(vertex.getName());
        graphRepository.update(graph);

        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("input", input);
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());
        return "index";
    }

    @PostMapping(value = "/vertices", params = "action=remove")
    public String removeVertex(@ModelAttribute final VertexInput vertex, final Model model) {
        final Graph graph = graphRepository.getGraph();
        graph.removeVertex(vertex.getName());
        graphRepository.update(graph);

        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("input", input);
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());
        return "index";
    }
}

package edu.uz.graphs.controller;

import edu.uz.graphs.model.graph.EulerianGraphInfo;
import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.input.EdgeInput;
import edu.uz.graphs.model.input.Input;
import edu.uz.graphs.model.input.VertexInput;
import edu.uz.graphs.service.CyclesService;
import edu.uz.graphs.service.GraphService;
import edu.uz.graphs.view.ViewContentFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GraphsController {

    private final GraphService graphService;
    private final CyclesService cyclesService;
    private final ViewContentFactory viewContentFactory;

    private Input input = new Input();
    private Graph graph = new Graph();

    public GraphsController(final GraphService graphService, final CyclesService cyclesService,
        final ViewContentFactory viewContentFactory) {
        this.graphService = graphService;
        this.cyclesService = cyclesService;
        this.viewContentFactory = viewContentFactory;
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
        graph = graphService.create(input.getText(), input.getType());

        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());

        return "index";
    }

    @PostMapping(value = "/graph", params = "action=clear")
    public String clearGraph(@ModelAttribute final Input input, final Model model) {
        graph = new Graph();

        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());

        return "index";
    }

    @PostMapping(value = "/edges", params = "action=add")
    public String addEdge(@ModelAttribute final EdgeInput edge, final Model model) {
        graph.addEdge(edge.getSource(), edge.getTarget(), edge.getWeight());
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("input", input);
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());
        return "index";
    }

    @PostMapping(value = "/edges", params = "action=remove")
    public String removeEdge(@ModelAttribute final EdgeInput edge, final Model model) {
        graph.removeEdge(edge.getSource(), edge.getTarget());
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("input", input);
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());
        return "index";
    }

    @PostMapping(value = "/vertices", params = "action=add")
    public String addVertex(@ModelAttribute final VertexInput vertex, final Model model) {
        graph.addVertex(vertex.getName());
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("input", input);
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());
        return "index";
    }

    @PostMapping(value = "/vertices", params = "action=remove")
    public String removeVertex(@ModelAttribute final VertexInput vertex, final Model model) {
        graph.removeVertex(vertex.getName());
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("input", input);
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());
        return "index";
    }

    @GetMapping(value = "/cycles")
    public String cycles() {
        return "cycles";
    }

    @PostMapping(value = "/cycles", params = "action=euler")
    public String eulerianCycle(final Model model) {
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("input", input);
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());

        final EulerianGraphInfo graphInfo = cyclesService.eulerianCycle(graph);
        model.addAttribute("cycleResult", viewContentFactory.createEulerianCycleResult(graphInfo));

        return "index";
    }

    @PostMapping(value = "/cycles", params = "action=hamilton")
    public String hamiltonCycle(final Model model) {
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("input", input);
        model.addAttribute("edge", new EdgeInput());
        model.addAttribute("vertex", new VertexInput());
        return "index";
    }
}

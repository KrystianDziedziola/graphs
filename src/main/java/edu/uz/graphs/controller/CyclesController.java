package edu.uz.graphs.controller;

import edu.uz.graphs.model.graph.EulerianGraphInfo;
import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.repository.GraphRepository;
import edu.uz.graphs.service.EulerianCycleService;
import edu.uz.graphs.view.ViewContentFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CyclesController {

    private final EulerianCycleService eulerianCycleService;
    private final GraphRepository graphRepository;
    private final ViewContentFactory viewContentFactory;

    public CyclesController(final EulerianCycleService eulerianCycleService,
        final GraphRepository graphRepository,
        final ViewContentFactory viewContentFactory) {
        this.eulerianCycleService = eulerianCycleService;
        this.graphRepository = graphRepository;
        this.viewContentFactory = viewContentFactory;
    }

    @GetMapping(value = "/cycles")
    public String cycles(final Model model) {
        final Graph graph = graphRepository.getGraph();
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        return "cycles";
    }

    @PostMapping(value = "/cycles", params = "action=euler")
    public String eulerianCycle(final Model model) {
        final Graph graph = graphRepository.getGraph();
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());

        final EulerianGraphInfo graphInfo = eulerianCycleService.eulerianCycle(graph);
        model.addAttribute("cycleResult", viewContentFactory.createEulerianCycleResult(graphInfo));
        return "cycles";
    }

    @PostMapping(value = "/cycles", params = "action=hamilton")
    public String hamiltonianCycle(final Model model) {
        final Graph graph = graphRepository.getGraph();
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        return "cycles";
    }
}

package edu.uz.graphs.controller;

import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.graph.GraphCycleInfo;
import edu.uz.graphs.repository.GraphRepository;
import edu.uz.graphs.service.cycle.CyclesService;
import edu.uz.graphs.view.ViewContentFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CyclesController {

    private final CyclesService cyclesService;
    private final GraphRepository graphRepository;
    private final ViewContentFactory viewContentFactory;

    public CyclesController(final CyclesService cyclesService,
        final GraphRepository graphRepository,
        final ViewContentFactory viewContentFactory) {
        this.cyclesService = cyclesService;
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

        final GraphCycleInfo graphInfo = cyclesService.eulerianCycle(graph);
        model.addAttribute("cycleResult", viewContentFactory.createCycleResult(graphInfo));
        return "cycles";
    }

    @PostMapping(value = "/cycles", params = "action=hamilton")
    public String hamiltonianCycle(final Model model) {
        final Graph graph = graphRepository.getGraph();
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());

        final GraphCycleInfo graphInfo = cyclesService.hamiltonianCycle(graph);
        model.addAttribute("cycleResult", viewContentFactory.createCycleResult(graphInfo));

        return "cycles";
    }
}

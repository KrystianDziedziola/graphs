package edu.uz.graphs.service.cycle;

import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.graph.GraphCycleInfo;
import org.springframework.stereotype.Service;

@Service
public class CyclesService {

    private final EulerianCycleService eulerianCycleService;
    private final HamiltonianCycleService hamiltonianCycleService;

    public CyclesService(final EulerianCycleService eulerianCycleService,
        final HamiltonianCycleService hamiltonianCycleService) {
        this.eulerianCycleService = eulerianCycleService;
        this.hamiltonianCycleService = hamiltonianCycleService;
    }

    public GraphCycleInfo eulerianCycle(final Graph graph) {
        return eulerianCycleService.cycle(graph);
    }

    public GraphCycleInfo hamiltonianCycle(final Graph graph) {
        return hamiltonianCycleService.cycle(graph);
    }
}

package edu.uz.graphs.service;

import edu.uz.graphs.model.graph.EulerianGraphInfo;
import edu.uz.graphs.model.graph.EulerianType;
import edu.uz.graphs.model.graph.Graph;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class CyclesService {

    public EulerianGraphInfo eulerianCycle(final Graph graph) {
        final EulerianType eulerianType = graph.eulerianType();
        System.out.println("type:" + eulerianType);

        return new EulerianGraphInfo(eulerianType, new ArrayList<>());
    }
}

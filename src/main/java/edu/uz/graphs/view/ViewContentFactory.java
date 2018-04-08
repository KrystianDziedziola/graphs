package edu.uz.graphs.view;

import edu.uz.graphs.model.graph.EulerianGraphInfo;
import org.springframework.stereotype.Component;

@Component
public class ViewContentFactory {

    public String createEulerianCycleResult(final EulerianGraphInfo info) {
        return "Graph type: " + info.getType() + "\n"
            + "Cycle: " + info.getCycle();
    }
}

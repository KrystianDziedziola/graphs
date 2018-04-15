package edu.uz.graphs.view;

import edu.uz.graphs.model.graph.GraphCycleInfo;
import org.springframework.stereotype.Component;

@Component
public class ViewContentFactory {

    public String createCycleResult(final GraphCycleInfo info) {
        return "Graph type: " + info.getType() + "\n"
            + "Cycle: " + info.getCycle();
    }
}

package edu.uz.graphs.service.search;

import edu.uz.graphs.model.graph.Graph;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DeepFirstSearch {

    public List<String> traverse(final String root, final Graph graph) {
        return graph.deepFirstTraverse(root);
    }
}

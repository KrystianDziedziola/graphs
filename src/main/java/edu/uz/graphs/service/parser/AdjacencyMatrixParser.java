package edu.uz.graphs.service.parser;

import edu.uz.graphs.model.representation.NodeAdjacency;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AdjacencyMatrixParser {

    public NodeAdjacency parse(final List<String> nodeNames, final String adjacencyList, final int rowNumber) {
        final String nodeName = nodeNames.get(rowNumber);
        final List<String> adjacencyValues = Arrays.asList(adjacencyList.split(" "));

        final List<String> adjacentNodeNames = new ArrayList<>();

        for (int index = 0; index < adjacencyValues.size(); index++) {
            final String value = adjacencyValues.get(index);
            if ("1".equals(value)) {
                adjacentNodeNames.add(nodeNames.get(index));
            }
        }

        return new NodeAdjacency(nodeName, adjacentNodeNames);
    }
}

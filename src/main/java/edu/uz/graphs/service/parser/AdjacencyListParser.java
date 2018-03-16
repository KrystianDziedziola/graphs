package edu.uz.graphs.service.parser;

import edu.uz.graphs.model.representation.NodeAdjacency;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class AdjacencyListParser {

    private static final String NODE_NAME_DELIMITER = ":";
    private static final int NODE_LABEL_POSITION = 0;
    private static final int ADJACENT_NODE_NAMES_POSITION = 1;
    private static final String WHITESPACE = "\\s";
    private static final String EMPTY = "";
    private static final String NODE_NAMES_DELIMITER = ",";

    public NodeAdjacency parse(final String line) {
        final String lineWithoutWhitespaces = line.replaceAll(WHITESPACE, EMPTY);
        final String lineParts[] = lineWithoutWhitespaces.split(NODE_NAME_DELIMITER);
        final String nodeLabel = lineParts[NODE_LABEL_POSITION];

        final String adjacentNodeNames;
        if (lineParts.length > 1) {
            adjacentNodeNames = lineParts[ADJACENT_NODE_NAMES_POSITION];
        } else {
            adjacentNodeNames = EMPTY;
        }

        return new NodeAdjacency(nodeLabel, parseAdjacentNodeNames(adjacentNodeNames));
    }

    private List<String> parseAdjacentNodeNames(final String nodeNames) {
        if (Strings.isEmpty(nodeNames)) {
            return new ArrayList<>();
        }

        return Arrays.asList(nodeNames.split(NODE_NAMES_DELIMITER));
    }
}

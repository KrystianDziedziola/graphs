package edu.uz.graphs.service.factory;

import edu.uz.graphs.model.representation.NodeAdjacency;
import edu.uz.graphs.service.parser.AdjacencyListParser;
import edu.uz.graphs.service.parser.AdjacencyMatrixParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class NodeAdjacencyFactory {

    private static final String NEW_LINE_DELIMITER = "\r\n";
    private static final int NODE_NAMES_INDEX = 0;

    private final AdjacencyListParser adjacencyListParser;
    private final AdjacencyMatrixParser adjacencyMatrixParser;

    public NodeAdjacencyFactory(
        AdjacencyListParser adjacencyListParser,
        AdjacencyMatrixParser adjacencyMatrixParser) {
        this.adjacencyListParser = adjacencyListParser;
        this.adjacencyMatrixParser = adjacencyMatrixParser;
    }

    public List<NodeAdjacency> createFromAdjacencyList(final String text) {
        final List<String> lines = prepareLines(text);

        return lines
            .stream()
            .map(adjacencyListParser::parse)
            .collect(Collectors.toList());

    }

    public List<NodeAdjacency> createFromAdjacencyMatrix(final String text) {
        final List<String> lines = prepareLines(text);

        final String namesLine = lines.get(NODE_NAMES_INDEX);
        final List<String> namesList = Arrays.asList(namesLine.split(" "));
        final List<String> adjacencyList = lines.subList(1, lines.size());

        final List<NodeAdjacency> nodeAdjacencies = new ArrayList<>();
        for (int lineNumber = 0; lineNumber < adjacencyList.size(); lineNumber++) {
            nodeAdjacencies.add(adjacencyMatrixParser
                .parse(namesList, adjacencyList.get(lineNumber), lineNumber));
        }

        return nodeAdjacencies;
    }

    private List<String> prepareLines(String text) {
        if (Strings.isEmpty(text)) {
            return new ArrayList<>();
        }

        return Arrays.asList(text.split(NEW_LINE_DELIMITER));
    }
}

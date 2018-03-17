package edu.uz.graphs.service.parser;

import edu.uz.graphs.model.graph.Graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class IncidenceMatrixParser {

    public Graph parse(final List<String> incidenceList, final List<String> namesList) {
        final Graph graph = new Graph();
        namesList.forEach(graph::addVertex);

        final String[][] matrix = createMatrix(incidenceList);
        final int matrixRows = matrix.length;
        final int matrixColumns = matrix[0].length;

        return createGraph(namesList, graph, matrix, matrixRows, matrixColumns);
    }

    private String[][] createMatrix(final List<String> incidenceList) {
        final String[][] matrix = new String[incidenceList.size()][];
        for (int row = 0; row < incidenceList.size(); row++) {
            final List<String> rowElements = Arrays.asList(incidenceList.get(row).split(" "));
            matrix[row] = new String[rowElements.size()];

            for (int elementIndex = 0; elementIndex < rowElements.size(); elementIndex++) {
                matrix[row][elementIndex] = rowElements.get(elementIndex);
            }
        }
        return matrix;
    }

    private Graph createGraph(final List<String> namesList, final Graph graph,
        final String[][] matrix,
        final int matrixRows, final int matrixColumns) {
        for (int column = 0; column < matrixColumns; column++) {
            final List<String> connectedNodes = createConnectedNodes(namesList, matrix, matrixRows,
                column);

            if (connectedNodes.size() == 2) {
                graph.addEdge(connectedNodes.get(0), connectedNodes.get(1));
            }
        }
        return graph;
    }

    private List<String> createConnectedNodes(final List<String> namesList, final String[][] matrix,
        final int matrixRows, final int column) {
        final List<String> connectedNodes = new ArrayList<>();
        for (int row = 0; row < matrixRows; row++) {
            final String element = matrix[row][column];
            if (Objects.equals(element, "1")) {
                connectedNodes.add(namesList.get(row));
            }
        }
        
        return connectedNodes;
    }
}

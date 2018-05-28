package edu.uz.graphs.model.representation;

import edu.uz.graphs.model.PathResult;
import java.util.List;

public class PathResultRepresentation {

    public static String of(final List<PathResult> pathResults) {
        final StringBuilder resultBuilder = new StringBuilder();
        pathResults.forEach(result -> resultBuilder.append(result).append("\n"));

        return resultBuilder.toString();
    }
}

package edu.uz.graphs.service.cycle;

import com.google.common.collect.Collections2;
import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.graph.GraphCycleInfo;
import edu.uz.graphs.model.graph.HamiltonianType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class HamiltonianCycleService {

    public static final ArrayList<String> EMPTY_CYCLE = new ArrayList<>();

    //    GraphCycleInfo cycle(final Graph graph) {
//        final HamiltonianType type = graph.hamiltonianType();
//        final ArrayList<Edge> cycle = new ArrayList<>();
//
//        if (type.equals(HamiltonianType.NON_HAMILTONIAN)) {
//            return new GraphCycleInfo(type, cycle);
//        }
//
//        return new GraphCycleInfo(type, cycle);
//    }

    /**
     * Brute force :)
     */
    GraphCycleInfo cycle(final Graph graph) {
        final Set<String> vertices = graph.getNonIsolatedVertices();

        final List<List<String>> permutations = new ArrayList<>(
            Collections2.permutations(vertices));
        Collections.shuffle(permutations);

        for (final List<String> permutation : permutations) {
            final List<String> cycle = cycle(graph, permutation);
            if (!cycle.isEmpty()) {
                return new GraphCycleInfo(HamiltonianType.HAMILTONIAN, cycle);
            }
        }

        return new GraphCycleInfo(HamiltonianType.NON_HAMILTONIAN, new ArrayList<>());
    }

    private boolean isCircuit(final List<String> cycle, final Graph graph) {
        final String target = cycle.get(0);
        final String source = cycle.get(cycle.size() - 1);
        return graph.edgeExists(source, target);
    }

    private List<String> cycle(final Graph graph, final List<String> permutation) {
        final List<String> cycle = new ArrayList<>();
        int firstIndex = 0;
        int secondIndex = 1;

        final int numberOfElements = permutation.size();
        while (secondIndex <= numberOfElements) {
            final String source = permutation.get(firstIndex);

            if (secondIndex == numberOfElements) {
                cycle.add(source);
                if (isCircuit(cycle, graph)) {
                    return cycle;
                } else {
                    return EMPTY_CYCLE;
                }
            }
            final String target = permutation.get(secondIndex);

            if (!graph.edgeExists(source, target)) {
                return EMPTY_CYCLE;
            } else {
                cycle.add(source);
            }
            firstIndex++;
            secondIndex++;
        }
        return EMPTY_CYCLE;
    }

}

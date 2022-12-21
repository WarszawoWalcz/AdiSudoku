import java.util.*;

public class AC3 {
    // The domains of the variables
    private Map<Field, List<Integer>> domains;
    private Field[][] grid;
    private Set<Arc> arcs;

    public AC3(Field[][] grid) {
        this.grid = grid;
        domains = new HashMap<>();
        arcs = new HashSet<>();
    }

    public boolean run() {
        // Filling in Map of domains
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                Field elemField = grid[row][column];
                if (elemField.getDomain().isEmpty()) {
                    List<Integer> fixedValue = new ArrayList<Integer>();
                    fixedValue.add(elemField.getValue());
                    domains.put(elemField, fixedValue);
                }
                else {
                    domains.put(elemField, elemField.getDomain());
                }
            }
        }

        // Collecting every arc for each field using attribute neighbours
        // Creating queue to-be-examined
        Queue<Arc> queue = new LinkedList<>();
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                for (Field neighbourField: grid[row][column].getNeighbours()) {
                    Arc arc = new Arc(grid[row][column], neighbourField);
                    arcs.add(arc);
                    queue.add(arc);
                }
            }
        }

        // Examining the consistency removing arc by arc
        while (!queue.isEmpty()) {
            Arc currentArc = queue.poll();
            if (revise(currentArc.getFirstField(), currentArc.getSecondField())) {
                if (domains.get(currentArc.getFirstField()).isEmpty()) {
                    return false;
                }
                // Add all the arcs that are connected to the variables i and j to the queue
                queue.addAll(getConnectedArcs(currentArc.getFirstField(), currentArc.getSecondField()));
            }
        }

        return true;
    }

//    private List<Arc> getConnectedArcs(Field firstField, Field secondField) {
//        List<Arc> revisedArcs = new LinkedList<>();
//        List<Integer> revisedDomain = reviseDomain(firstField, secondField);
//        if (revisedDomain.size() < domains.get(firstField).size()) {
//            domains.put(firstField, firstField.getDomain());
//            for (Field neighbour : firstField.getNeighbours()) {
//                if (!neighbour.equals(secondField)) {
//                    revisedArcs.add(new Arc(firstField, neighbour));
//                }
//            }
//        }
//        else if (revisedDomain.size() < domains.get(secondField).size()) {
//            domains.put(secondField, revisedDomain);
//        }
//
//        return revisedArcs;
//    }

    private List<Arc> getConnectedArcs(Field firstField, Field secondField) {
        List<Arc> revisedArcs = new LinkedList<>();

        // Intersect the domains of firstField and secondField
        List<Integer> revisedDomain = reviseDomain(firstField, secondField);

        // If the revised domain of firstField is smaller than its original domain,
        // update firstField's domain and add all the arcs that are connected to firstField
        // to the list of revised arcs
        if (revisedDomain.size() < firstField.getDomain().size()) {
            for (Integer value : firstField.getDomain()) {
                if (!revisedDomain.contains(value)) {
                    firstField.removeFromDomain(value);
                }
            }
            for (Field neighbour : firstField.getNeighbours()) {
                if (!neighbour.equals(secondField)) {
                    revisedArcs.add(new Arc(firstField, neighbour));
                }
            }
        }

        // If the revised domain of secondField is smaller than its original domain,
        // update secondField's domain
        else if (revisedDomain.size() < secondField.getDomain().size()) {
            for (Integer value : secondField.getDomain()) {
                if (!revisedDomain.contains(value)) {
                    secondField.removeFromDomain(value);
                }
            }
        }

        // Return the list of revised arcs
        return revisedArcs;
    }

    private List<Integer> reviseDomain(Field firstField, Field secondField) {
        List<Integer> domain1 = domains.get(firstField);
        List<Integer> domain2 = domains.get(secondField);

        // Intersect the two cells' domains to get the revised domain
        List<Integer> revisedDomain = new ArrayList<>(domain1);
        revisedDomain.removeAll(domain2);

        // Return the revised domain
        return revisedDomain;
    }

    private boolean revise(Field i, Field j) {
        // Create a copy of i's domain, so we can modify it
        List<Integer> revisedDomain = new ArrayList<>(i.getDomain());

        // Iterate over the values in i's domain
        for (Integer value : i.getDomain()) {
            // Check if the value is still allowed in j's domain
            if (!j.getDomain().contains(value)) {
                // If the value is not allowed in j's domain, remove it from i's domain
//                revisedDomain.remove(value);
                i.removeFromDomain(value);
            }
        }

        // If i's domain has changed, update it and return true
        if (revisedDomain.size() != i.getDomain().size()) {
//            i.setDomain(revisedDomain);
            return true;
        }

        // If i's domain hasn't changed, return false
        return false;
    }
}

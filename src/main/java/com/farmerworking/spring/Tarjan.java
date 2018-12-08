package com.farmerworking.spring;

import java.util.*;

public class Tarjan {
    private final DirectedGraph directedGraph;
    private int DISCOVER_TIME_INDEX_START_NUM = 0;
    private Map<Node, Boolean> nonVisited = new HashMap<>();
    private Map<Node, Integer> lowMap = new HashMap<>();
    private Stack<Node> stack = new Stack<>();
    private List<Scc> sccList = new ArrayList<>();

    public Tarjan(DirectedGraph<Node> directedGraph) {
        this.directedGraph = directedGraph;

        for(Node node : directedGraph.getVertices()) {
            if (this.nonVisited.getOrDefault(node, true)) {
                dfs(node);
            }
        }
    }

    private void dfs(Node node) {
        this.nonVisited.put(node, false);
        this.stack.push(node);
        int discoverTimeIndex = DISCOVER_TIME_INDEX_START_NUM++;
        lowMap.put(node, discoverTimeIndex);

        for(Node child : node.getChildren()) {
            if (this.nonVisited.getOrDefault(child, true)) {
                dfs(child);
            }

            lowMap.put(node, Math.min(lowMap.get(node), lowMap.get(child)));
        }

        if (lowMap.get(node) < discoverTimeIndex) {
            return;
        } else {
            Scc scc = new Scc();
            this.sccList.add(scc);

            Node item;
            do {
                item = stack.pop();
                scc.addNode(item);
                lowMap.put(item, directedGraph.getVertices().size());
            } while (!item.equals(node));
        }
    }

    public List<Scc> getSccList() {
        return sccList;
    }

    public DirectedGraph<Scc> generateSccGraph() {
        Map<Node, Scc> node2SccMap = new HashMap<>();

        for(Scc scc : sccList) {
            for(Node node : scc.getNodeSet()) {
                node2SccMap.put(node, scc);
            }
        }

        for(Scc scc : sccList) {
            for(Node node : scc.getAdjacentNodes()) {
                assert node2SccMap.get(node) != null;
                scc.addChild(node2SccMap.get(node));
            }
        }

        return new DirectedGraph<>(sccList);
    }
}

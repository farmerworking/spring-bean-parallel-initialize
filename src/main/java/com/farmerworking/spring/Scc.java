package com.farmerworking.spring;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Strong connected component
 */
public class Scc {
    private Set<Node> nodeSet = new HashSet<>();
    private Set<Scc> children = new HashSet<>();
    private int pins = 0;

    public Set<Node> getNodeSet() {
        return nodeSet;
    }

    public Set<Scc> getChildren() {
        return children;
    }

    public void addNode(Node node) {
        this.nodeSet.add(node);
    }

    public void addChild(Scc scc) {
        if (this.children.add(scc)) {
            this.pin();
        }
    }

    public Set<Node> getAdjacentNodes() {
        Set<Node> result = new HashSet<>();

        for(Node node : nodeSet) {
            for(Node child : node.getChildren()) {
                if (!nodeSet.contains(child)) {
                    result.add(child);
                }
            }
        }

        return result;
    }

    private void pin() {
        this.pins ++;
    }

    public int getPins() {
        return pins;
    }

    public void unpin() {
        assert this.pins > 0;
        this.pins --;
    }

    public boolean isFree() {
        return this.pins == 0;
    }

    public Set<String> toStringSet() {
        return nodeSet.stream().map(Node::getId).collect(Collectors.toSet());
    }
}

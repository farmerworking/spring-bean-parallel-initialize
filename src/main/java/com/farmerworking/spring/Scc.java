package com.farmerworking.spring;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Strong connected component
 */
public class Scc {
    private Set<Node> nodeSet = new HashSet<>();
    private Set<Scc> children = new HashSet<>();
    private Set<Scc> parents = new HashSet<>();
    private int pins = 0;

    public Set<Node> getNodeSet() {
        return nodeSet;
    }

    public Set<Scc> getChildren() {
        return children;
    }

    public Set<Scc> getParents() {
        return parents;
    }

    public void addNode(Node node) {
        this.nodeSet.add(node);
    }

    public void addChild(Scc scc) {
        if (this.children.add(scc)) {
            this.pin();
        }
    }

    public void addParent(Scc scc) {
        this.parents.add(scc);
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

    // must be thread-safe
    private synchronized void pin() {
        this.pins ++;
    }

    // must be thread-safe
    public synchronized int getPins() {
        return pins;
    }

    // must be thread-safe
    public synchronized int unpin() {
        assert this.pins > 0;
        this.pins --;
        return this.pins;
    }

    public Set<String> toStringSet() {
        return nodeSet.stream().map(Node::getId).collect(Collectors.toSet());
    }
}

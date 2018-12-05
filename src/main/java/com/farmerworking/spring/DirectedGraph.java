package com.farmerworking.spring;

import java.util.Collection;

public class DirectedGraph {
    private Collection<Node> nodeList;

    public DirectedGraph(Collection<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public Collection<Node> getNodeList() {
        return nodeList;
    }
}

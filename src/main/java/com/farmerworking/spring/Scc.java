package com.farmerworking.spring;

import java.util.ArrayList;
import java.util.List;

/**
 * Strong connected component
 */
public class Scc {
    private List<Node> nodeList = new ArrayList<>();

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void addNode(Node node) {
        this.nodeList.add(node);
    }
}

package com.farmerworking.spring;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String id;
    private List<Node> children = new ArrayList<>();

    public Node(String id) {
        this.id = id;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public String getId() {
        return id;
    }

    public List<Node> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return id.equals(node.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

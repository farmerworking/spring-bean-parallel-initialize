package com.farmerworking.spring;

import java.util.List;

public class DirectedGraph<V> {
    private List<V> vertices;

    public DirectedGraph(List<V> vertices) {
        this.vertices = vertices;
    }

    public List<V> getVertices() {
        return vertices;
    }
}

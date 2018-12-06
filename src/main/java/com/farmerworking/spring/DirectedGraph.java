package com.farmerworking.spring;

import java.util.Collection;

public class DirectedGraph<V> {
    private Collection<V> vertices;

    public DirectedGraph(Collection<V> vertices) {
        this.vertices = vertices;
    }

    public Collection<V> getVertices() {
        return vertices;
    }
}

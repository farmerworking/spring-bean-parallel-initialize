package com.farmerworking.spring;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class TestUtils {
    public static Set<String> nodeList2Set(Collection<Node> nodeCollection) {
        return nodeCollection.stream().map(Node::getId).collect(Collectors.toSet());
    }

    public static Set<String> nodeList2Set(Scc scc) {
        return scc.getNodeList().stream().map(Node::getId).collect(Collectors.toSet());
    }
}

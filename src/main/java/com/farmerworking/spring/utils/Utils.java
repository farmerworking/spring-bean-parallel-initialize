package com.farmerworking.spring.utils;

import com.farmerworking.spring.Node;
import com.farmerworking.spring.Scc;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
    public static Set<String> nodeList2IdStringSet(Collection<Node> nodeCollection) {
        return nodeCollection.stream().map(Node::getId).collect(Collectors.toSet());
    }

    public static Set<String> nodeList2IdStringSet(Scc scc) {
        return scc.getNodeSet().stream().map(Node::getId).collect(Collectors.toSet());
    }

    public static Set<Set<String>> sccSet2StringSetSet(Set<Scc> sccSet) {
        Set<Set<String>> result = new HashSet<>();
        sccSet.stream().forEach(scc -> result.add(scc.toStringSet()));
        return result;
    }
}

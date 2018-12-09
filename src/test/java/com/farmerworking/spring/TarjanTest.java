package com.farmerworking.spring;

import com.farmerworking.spring.utils.Utils;
import com.google.common.collect.Sets;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

/**
 * Created by John on 18/12/5.
 */
public class TarjanTest {
    @Test
    public void testSccDiscoveryCase1() throws Exception {
        Tarjan tarjan = TestUtils.initFromFile("simpleDG.txt");

        assertEquals(3, tarjan.getSccList().size());
        assertEquals(Sets.newHashSet("4"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(0)));
        assertEquals(Sets.newHashSet("3"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(1)));
        assertEquals(Sets.newHashSet("0", "1", "2"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(2)));
    }

    @Test
    public void testSccDiscoveryCase2() throws Exception {
        Tarjan tarjan = TestUtils.initFromFile("tinyDG.txt");
        assertEquals(5, tarjan.getSccList().size());
        assertEquals(Sets.newHashSet("1"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(0)));
        assertEquals(Sets.newHashSet("0", "2", "3", "4", "5"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(1)));
        assertEquals(Sets.newHashSet("9", "10", "11", "12"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(2)));
        assertEquals(Sets.newHashSet("6", "8"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(3)));
        assertEquals(Sets.newHashSet("7"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(4)));
    }

    @Test
    public void testSccDiscoveryCase3() throws Exception {
        String[] var = new String[] {"2", "5", "6", "8", "9", "11", "12", "13", "15", "16", "18", "19", "22", "23", "25", "26", "28", "29", "30", "31", "32", "33", "34", "35", "37", "38", "39", "40", "42", "43", "44", "46", "47", "48", "49"};

        Tarjan tarjan = TestUtils.initFromFile("mediumDG.txt");
        assertEquals(10, tarjan.getSccList().size());
        assertEquals(Sets.newHashSet("21"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(0)));
        assertEquals(Sets.newHashSet(var), Utils.nodeList2IdStringSet(tarjan.getSccList().get(1)));
        assertEquals(Sets.newHashSet("41"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(2)));
        assertEquals(Sets.newHashSet("7"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(3)));
        assertEquals(Sets.newHashSet("0"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(4)));
        assertEquals(Sets.newHashSet("14"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(5)));
        assertEquals(Sets.newHashSet("45"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(6)));
        assertEquals(Sets.newHashSet("1"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(7)));
        assertEquals(Sets.newHashSet("3", "4", "17", "20", "24", "27", "36"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(8)));
        assertEquals(Sets.newHashSet("10"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(9)));
    }

    @Test
    public void testSccGraphGenerationCase1() throws Exception {
        Tarjan tarjan = TestUtils.initFromFile("simpleDG.txt");
        DirectedGraph<Scc> graph = tarjan.generateSccGraph();
        assertTrue(graph.getVertices().get(0).getChildren().isEmpty());
        assertTrue(graph.getVertices().get(0).isFree());
        assertEquals(0, graph.getVertices().get(0).getPins());

        assertEquals(setOfScc(Sets.newHashSet("4")), Utils.sccSet2StringSetSet(graph.getVertices().get(1).getChildren()));
        assertFalse(graph.getVertices().get(0).isFree());
        assertEquals(1, graph.getVertices().get(1).getPins());

        assertEquals(setOfScc(Sets.newHashSet("3")), Utils.sccSet2StringSetSet(graph.getVertices().get(2).getChildren()));
        assertFalse(graph.getVertices().get(0).isFree());
        assertEquals(1, graph.getVertices().get(2).getPins());
    }

    @Test
    public void testSccGraphGenerationCase2() throws Exception {
        Tarjan tarjan = TestUtils.initFromFile("tinyDG.txt");
        DirectedGraph<Scc> graph = tarjan.generateSccGraph();
        assertTrue(graph.getVertices().get(0).getChildren().isEmpty());
        assertTrue(graph.getVertices().get(0).isFree());
        assertEquals(0, graph.getVertices().get(0).getPins());

        assertEquals(setOfScc(Sets.newHashSet("1")), Utils.sccSet2StringSetSet(graph.getVertices().get(1).getChildren()));
        assertFalse(graph.getVertices().get(1).isFree());
        assertEquals(1, graph.getVertices().get(1).getPins());

        assertEquals(setOfScc(Sets.newHashSet("0", "2", "3", "4", "5")), Utils.sccSet2StringSetSet(graph.getVertices().get(2).getChildren()));
        assertFalse(graph.getVertices().get(2).isFree());
        assertEquals(1, graph.getVertices().get(2).getPins());

        assertEquals(setOfScc(
                Sets.newHashSet("0", "2", "3", "4", "5"),
                Sets.newHashSet("9", "10", "11", "12")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(3).getChildren()));
        assertFalse(graph.getVertices().get(3).isFree());
        assertEquals(2, graph.getVertices().get(3).getPins());

        assertEquals(setOfScc(
                Sets.newHashSet("6", "8"),
                Sets.newHashSet("9", "10", "11", "12")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(4).getChildren()));
        assertFalse(graph.getVertices().get(4).isFree());
        assertEquals(2, graph.getVertices().get(4).getPins());
    }

    @Test
    public void testSccGraphGenerationCase3() throws Exception {
        String[] var = new String[] {"2", "5", "6", "8", "9", "11", "12", "13", "15", "16", "18", "19", "22", "23", "25", "26", "28", "29", "30", "31", "32", "33", "34", "35", "37", "38", "39", "40", "42", "43", "44", "46", "47", "48", "49"};
        Tarjan tarjan = TestUtils.initFromFile("mediumDG.txt");
        DirectedGraph<Scc> graph = tarjan.generateSccGraph();
        assertTrue(graph.getVertices().get(0).getChildren().isEmpty());
        assertTrue(graph.getVertices().get(0).isFree());
        assertEquals(0, graph.getVertices().get(0).getPins());

        assertEquals(setOfScc(Sets.newHashSet("21")), Utils.sccSet2StringSetSet(graph.getVertices().get(1).getChildren()));
        assertFalse(graph.getVertices().get(1).isFree());
        assertEquals(1, graph.getVertices().get(1).getPins());

        assertEquals(setOfScc(Sets.newHashSet(var)), Utils.sccSet2StringSetSet(graph.getVertices().get(2).getChildren()));
        assertFalse(graph.getVertices().get(2).isFree());
        assertEquals(1, graph.getVertices().get(2).getPins());

        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("41")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(3).getChildren()));
        assertFalse(graph.getVertices().get(3).isFree());
        assertEquals(2, graph.getVertices().get(3).getPins());

        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("7")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(4).getChildren()));
        assertFalse(graph.getVertices().get(4).isFree());
        assertEquals(2, graph.getVertices().get(4).getPins());

        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("21")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(5).getChildren()));
        assertFalse(graph.getVertices().get(5).isFree());
        assertEquals(2, graph.getVertices().get(5).getPins());

        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("14")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(6).getChildren()));
        assertFalse(graph.getVertices().get(6).isFree());
        assertEquals(2, graph.getVertices().get(6).getPins());

        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("21"),
                Sets.newHashSet("14"), Sets.newHashSet("45")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(7).getChildren()));
        assertFalse(graph.getVertices().get(7).isFree());
        assertEquals(4, graph.getVertices().get(7).getPins());

        assertEquals(setOfScc(Sets.newHashSet(var)), Utils.sccSet2StringSetSet(graph.getVertices().get(8).getChildren()));
        assertFalse(graph.getVertices().get(8).isFree());
        assertEquals(1, graph.getVertices().get(8).getPins());

        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("41"),
                Sets.newHashSet("7"), Sets.newHashSet("0")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(9).getChildren()));
        assertFalse(graph.getVertices().get(9).isFree());
        assertEquals(4, graph.getVertices().get(9).getPins());
    }

    private Set<Set<String>> setOfScc(Set<String>... sets) {
        Set<Set<String>> result = new HashSet<>();
        for(Set<String> item : sets) {
            result.add(item);
        }
        return result;
    }
}
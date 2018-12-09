package com.farmerworking.spring;

import com.farmerworking.spring.utils.Utils;
import com.google.common.collect.Lists;
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

        List<TestStruct> list = Lists.newArrayList(
                new TestStruct(setOfScc(Sets.newHashSet("3")), new HashSet<>(), true),
                new TestStruct(setOfScc(Sets.newHashSet("0", "1", "2")), setOfScc(Sets.newHashSet("4")), false),
                new TestStruct(new HashSet<>(), setOfScc(Sets.newHashSet("3")), false)
        );

        testSuit(graph, list);
    }

    @Test
    public void testSccGraphGenerationCase2() throws Exception {
        Tarjan tarjan = TestUtils.initFromFile("tinyDG.txt");
        DirectedGraph<Scc> graph = tarjan.generateSccGraph();

        List<TestStruct> list = Lists.newArrayList(
                new TestStruct(setOfScc(Sets.newHashSet("0", "2", "3", "4", "5")), new HashSet<>(), true),
                new TestStruct(setOfScc(Sets.newHashSet("9", "10", "11", "12"), Sets.newHashSet("6", "8")), setOfScc(Sets.newHashSet("1")), false),
                new TestStruct(setOfScc(
                        Sets.newHashSet("6", "8"),
                        Sets.newHashSet("7")), setOfScc(Sets.newHashSet("0", "2", "3", "4", "5")), false),
                new TestStruct(setOfScc(
                        Sets.newHashSet("7")), setOfScc(
                        Sets.newHashSet("0", "2", "3", "4", "5"),
                        Sets.newHashSet("9", "10", "11", "12")),
                        false),
                new TestStruct(new HashSet<>(), setOfScc(
                        Sets.newHashSet("6", "8"),
                        Sets.newHashSet("9", "10", "11", "12")),
                        false)
        );

        testSuit(graph, list);
    }

    @Test
    public void testSccGraphGenerationCase3() throws Exception {
        String[] var = new String[] {"2", "5", "6", "8", "9", "11", "12", "13", "15", "16", "18", "19", "22", "23", "25", "26", "28", "29", "30", "31", "32", "33", "34", "35", "37", "38", "39", "40", "42", "43", "44", "46", "47", "48", "49"};
        Tarjan tarjan = TestUtils.initFromFile("mediumDG.txt");
        DirectedGraph<Scc> graph = tarjan.generateSccGraph();

        List<TestStruct> list = Lists.newArrayList(
                new TestStruct(setOfScc(
                        Sets.newHashSet(var),
                        Sets.newHashSet("1"),
                        Sets.newHashSet("14")), new HashSet<>(), true),
                new TestStruct(setOfScc(
                        Sets.newHashSet("0"),
                        Sets.newHashSet("45"),
                        Sets.newHashSet("1"),
                        Sets.newHashSet("14"),
                        Sets.newHashSet("7"),
                        Sets.newHashSet("41"),
                        Sets.newHashSet("10"),
                        Sets.newHashSet("24", "36", "3", "4", "27", "17", "20")), setOfScc(Sets.newHashSet("21")), false),
                new TestStruct(setOfScc(Sets.newHashSet("7"), Sets.newHashSet("10")), setOfScc(Sets.newHashSet(var)), false),
                new TestStruct(setOfScc(Sets.newHashSet("0"), Sets.newHashSet("10")), setOfScc(Sets.newHashSet(var), Sets.newHashSet("41")), false),
                new TestStruct(setOfScc(Sets.newHashSet("10")), setOfScc(Sets.newHashSet(var), Sets.newHashSet("7")), false),
                new TestStruct(setOfScc(Sets.newHashSet("45"), Sets.newHashSet("1")), setOfScc(Sets.newHashSet(var), Sets.newHashSet("21")), false),
                new TestStruct(setOfScc(Sets.newHashSet("1")), setOfScc(Sets.newHashSet(var), Sets.newHashSet("14")), false),
                new TestStruct(new HashSet<>(), setOfScc(
                        Sets.newHashSet(var),
                        Sets.newHashSet("21"),
                        Sets.newHashSet("14"),
                        Sets.newHashSet("45")), false),
                new TestStruct(new HashSet<>(), setOfScc(Sets.newHashSet(var)), false),
                new TestStruct(new HashSet<>(), setOfScc(
                        Sets.newHashSet(var),
                        Sets.newHashSet("41"),
                        Sets.newHashSet("7"),
                        Sets.newHashSet("0")), false)
        );

        testSuit(graph, list);
    }

    class TestStruct {
        Set<Set<String>> children;
        Set<Set<String>> parents;
        boolean isFree;

        public TestStruct(Set<Set<String>> parents, Set<Set<String>> children, boolean isFree) {
            this.parents = parents;
            this.children= children;
            this.isFree = isFree;
        }
    }

    private Set<Set<String>> setOfScc(Set<String>... sets) {
        Set<Set<String>> result = new HashSet<>();
        for(Set<String> item : sets) {
            result.add(item);
        }
        return result;
    }

    private void testSuit(DirectedGraph<Scc> graph, List<TestStruct> list) {
        for (int i = 0; i < graph.getVertices().size(); i++) {
            Scc scc = graph.getVertices().get(i);
            TestStruct testStruct = list.get(i);

            assertEquals(testStruct.children, Utils.sccSet2StringSetSet(scc.getChildren()));
            assertEquals(testStruct.isFree, scc.isFree());
            assertEquals(testStruct.children.size(), scc.getPins());
            assertEquals(testStruct.parents, Utils.sccSet2StringSetSet(scc.getParents()));
        }
    }
}
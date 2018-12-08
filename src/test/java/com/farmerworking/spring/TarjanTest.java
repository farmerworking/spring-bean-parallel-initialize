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
    public void testCase1() throws Exception {
        Tarjan tarjan = initFromFile("simpleDG.txt");

        assertEquals(3, tarjan.getSccList().size());
        assertEquals(Sets.newHashSet("4"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(0)));
        assertEquals(Sets.newHashSet("3"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(1)));
        assertEquals(Sets.newHashSet("0", "1", "2"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(2)));

        // test scc dependency relation
        DirectedGraph<Scc> graph = tarjan.generateSccGraph();
        assertTrue(graph.getVertices().get(0).getChildren().isEmpty());
        assertEquals(setOfScc(Sets.newHashSet("4")), Utils.sccSet2StringSetSet(graph.getVertices().get(1).getChildren()));
        assertEquals(setOfScc(Sets.newHashSet("3")), Utils.sccSet2StringSetSet(graph.getVertices().get(2).getChildren()));
    }

    @Test
    public void testCase2() throws Exception {
        Tarjan tarjan = initFromFile("tinyDG.txt");
        assertEquals(5, tarjan.getSccList().size());
        assertEquals(Sets.newHashSet("1"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(0)));
        assertEquals(Sets.newHashSet("0", "2", "3", "4", "5"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(1)));
        assertEquals(Sets.newHashSet("9", "10", "11", "12"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(2)));
        assertEquals(Sets.newHashSet("6", "8"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(3)));
        assertEquals(Sets.newHashSet("7"), Utils.nodeList2IdStringSet(tarjan.getSccList().get(4)));

        // test scc dependency relation
        DirectedGraph<Scc> graph = tarjan.generateSccGraph();
        assertTrue(graph.getVertices().get(0).getChildren().isEmpty());
        assertEquals(setOfScc(Sets.newHashSet("1")), Utils.sccSet2StringSetSet(graph.getVertices().get(1).getChildren()));
        assertEquals(setOfScc(Sets.newHashSet("0", "2", "3", "4", "5")), Utils.sccSet2StringSetSet(graph.getVertices().get(2).getChildren()));
        assertEquals(setOfScc(
                Sets.newHashSet("0", "2", "3", "4", "5"),
                Sets.newHashSet("9", "10", "11", "12")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(3).getChildren()));
        assertEquals(setOfScc(
                Sets.newHashSet("6", "8"),
                Sets.newHashSet("9", "10", "11", "12")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(4).getChildren()));
    }

    @Test
    public void testCase3() throws Exception {
        String[] var = new String[] {"2", "5", "6", "8", "9", "11", "12", "13", "15", "16", "18", "19", "22", "23", "25", "26", "28", "29", "30", "31", "32", "33", "34", "35", "37", "38", "39", "40", "42", "43", "44", "46", "47", "48", "49"};

        Tarjan tarjan = initFromFile("mediumDG.txt");
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

        // test scc dependency relation
        DirectedGraph<Scc> graph = tarjan.generateSccGraph();
        assertTrue(graph.getVertices().get(0).getChildren().isEmpty());
        assertEquals(setOfScc(Sets.newHashSet("21")), Utils.sccSet2StringSetSet(graph.getVertices().get(1).getChildren()));
        assertEquals(setOfScc(Sets.newHashSet(var)), Utils.sccSet2StringSetSet(graph.getVertices().get(2).getChildren()));
        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("41")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(3).getChildren()));
        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("7")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(4).getChildren()));
        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("21")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(5).getChildren()));
        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("14")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(6).getChildren()));
        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("21"),
                Sets.newHashSet("14"), Sets.newHashSet("45")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(7).getChildren()));
        assertEquals(setOfScc(Sets.newHashSet(var)), Utils.sccSet2StringSetSet(graph.getVertices().get(8).getChildren()));
        assertEquals(setOfScc(Sets.newHashSet(var), Sets.newHashSet("41"),
                Sets.newHashSet("7"), Sets.newHashSet("0")
        ), Utils.sccSet2StringSetSet(graph.getVertices().get(9).getChildren()));
    }

    private Tarjan initFromFile(String filename) throws IOException {
        Scanner scanner = getScannerFromFile(filename);
        int nodeCount = scanner.nextInt();
        int edgeCount = scanner.nextInt();

        List<Node> nodeList = new ArrayList<>();
        Map<Integer, Node> map = new HashMap<>();
        for (int i = 0; i < nodeCount; i++) {
            Node node = new Node(String.valueOf(i));
            nodeList.add(node);
            map.put(i, node);
        }

        for (int i = 0; i < edgeCount; i++) {
            Node src = map.get(scanner.nextInt());
            Node dst = map.get(scanner.nextInt());
            src.addChild(dst);
        }

        DirectedGraph<Node> graph = new DirectedGraph<>(nodeList);
        return new Tarjan(graph);
    }

    private Scanner getScannerFromFile(String filename) throws IOException{
        File file = new File("data/" + filename);
        FileInputStream fis = new FileInputStream(file);
        Scanner scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");
        return scanner;
    }

    private Set<Set<String>> setOfScc(Set<String>... sets) {
        Set<Set<String>> result = new HashSet<>();
        for(Set<String> item : sets) {
            result.add(item);
        }
        return result;
    }
}
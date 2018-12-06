package com.farmerworking.spring;

import com.google.common.collect.Sets;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by John on 18/12/5.
 */
public class TarjanTest {
    @Test
    public void testCase1() throws Exception {
        Tarjan tarjan = initFromFile("simpleDG.txt");

        assertEquals(3, tarjan.getSccList().size());
        assertEquals(Sets.newHashSet("4"), nodeList2Set(tarjan.getSccList().get(0)));
        assertEquals(Sets.newHashSet("3"), nodeList2Set(tarjan.getSccList().get(1)));
        assertEquals(Sets.newHashSet("0", "1", "2"), nodeList2Set(tarjan.getSccList().get(2)));
    }

    @Test
    public void testCase2() throws Exception {
        Tarjan tarjan = initFromFile("tinyDG.txt");
        assertEquals(5, tarjan.getSccList().size());
        assertEquals(Sets.newHashSet("1"), nodeList2Set(tarjan.getSccList().get(0)));
        assertEquals(Sets.newHashSet("0", "2", "3", "4", "5"), nodeList2Set(tarjan.getSccList().get(1)));
        assertEquals(Sets.newHashSet("9", "10", "11", "12"), nodeList2Set(tarjan.getSccList().get(2)));
        assertEquals(Sets.newHashSet("6", "8"), nodeList2Set(tarjan.getSccList().get(3)));
        assertEquals(Sets.newHashSet("7"), nodeList2Set(tarjan.getSccList().get(4)));
    }

    @Test
    public void testCase3() throws Exception {
        Tarjan tarjan = initFromFile("mediumDG.txt");
        assertEquals(10, tarjan.getSccList().size());
        assertEquals(Sets.newHashSet("21"), nodeList2Set(tarjan.getSccList().get(0)));
        assertEquals(Sets.newHashSet("2", "5", "6", "8", "9", "11", "12", "13", "15", "16", "18", "19", "22", "23", "25", "26", "28", "29", "30", "31", "32", "33", "34", "35", "37", "38", "39", "40", "42", "43", "44", "46", "47", "48", "49"), nodeList2Set(tarjan.getSccList().get(1)));
        assertEquals(Sets.newHashSet("41"), nodeList2Set(tarjan.getSccList().get(2)));
        assertEquals(Sets.newHashSet("7"), nodeList2Set(tarjan.getSccList().get(3)));
        assertEquals(Sets.newHashSet("0"), nodeList2Set(tarjan.getSccList().get(4)));
        assertEquals(Sets.newHashSet("14"), nodeList2Set(tarjan.getSccList().get(5)));
        assertEquals(Sets.newHashSet("45"), nodeList2Set(tarjan.getSccList().get(6)));
        assertEquals(Sets.newHashSet("1"), nodeList2Set(tarjan.getSccList().get(7)));
        assertEquals(Sets.newHashSet("3", "4", "17", "20", "24", "27", "36"), nodeList2Set(tarjan.getSccList().get(8)));
        assertEquals(Sets.newHashSet("10"), nodeList2Set(tarjan.getSccList().get(9)));
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

    private Set<String> nodeList2Set(Scc scc) {
        return scc.getNodeList().stream().map(Node::getId).collect(Collectors.toSet());
    }
}
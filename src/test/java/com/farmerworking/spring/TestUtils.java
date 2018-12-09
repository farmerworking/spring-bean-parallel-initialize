package com.farmerworking.spring;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class TestUtils {
    public static Tarjan initFromFile(String filename) throws IOException {
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

    private static Scanner getScannerFromFile(String filename) throws IOException {
        File file = new File("data/" + filename);
        FileInputStream fis = new FileInputStream(file);
        Scanner scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");
        return scanner;
    }
}

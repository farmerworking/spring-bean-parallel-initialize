package com.farmerworking.spring;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * Created by John on 18/12/9.
 */
public class ParallelExecutorTest {
    @Before
    public void setUp() throws Exception {
        Stage.SEQUENCE = new AtomicInteger(1);
    }

    @Test
    public void testCase1() throws Exception {
        ParallelExecutor executor = prepareTest("simpleDG.txt");
        executor.run();
        assertEquals(3, executor.getStageCount());
    }

    @Test
    public void testCase2() throws Exception {
        ParallelExecutor executor = prepareTest("tinyDG.txt");
        executor.run();
        assertEquals(5, executor.getStageCount());
    }

    @Test
    public void testCase3() throws Exception {
        ParallelExecutor executor = prepareTest("mediumDG.txt");
        executor.run();
        assertEquals(8, executor.getStageCount());
    }

    private ParallelExecutor prepareTest(String dataFile) throws IOException {
        Tarjan tarjan = TestUtils.initFromFile(dataFile);
        DirectedGraph<Scc> graph = tarjan.generateSccGraph();
        return new ParallelExecutor(graph);
    }
}
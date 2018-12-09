package com.farmerworking.spring;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ParallelExecutor {
    private final DirectedGraph<Scc> graph;
    private Queue<Stage> queue;
    private ExecutorService executorService;
    private Map<Scc, AtomicInteger> sccProcessCounter;
    private AtomicInteger stageCount;

    public ParallelExecutor(DirectedGraph<Scc> graph) {
        this.graph = graph;
        this.queue = new ConcurrentLinkedQueue<>();
        this.sccProcessCounter = new HashMap<>();
        this.stageCount = new AtomicInteger(0);
        this.executorService = Executors.newFixedThreadPool(4);

        initQueue();
    }

    private void initQueue() {
        List<Scc> sccList = new ArrayList<>();
        for(Scc scc : graph.getVertices()) {
            sccProcessCounter.put(scc, new AtomicInteger(0));

            if (scc.getChildren().isEmpty()) {
                sccList.add(scc);
            }
        }

        addStageIfNeed(sccList);
    }

    public void run() throws InterruptedException {
        AtomicInteger dealingSccCount = new AtomicInteger(0);

        while (true) {
            synchronized (this) {
                while (queue.isEmpty() && dealingSccCount.get() != 0) {
                    wait();
                }
            }

            Stage stage = queue.poll();
            if (stage == null) {
                break; // no dealing scc and no waiting scc
            }

            for(Scc scc : stage.getSccList()) {
                executorService.submit(new SccRunnable(scc, dealingSccCount, this));
                dealingSccCount.incrementAndGet();
            }
        }

        executorService.shutdown();

        assert executorService.isTerminated();
        assert executorService.isShutdown();

        // all scc are processed only once
        assert sccProcessCounter.values().stream().filter(item -> item.get() == 1).count() == sccProcessCounter.size();
    }

    private class SccRunnable implements Runnable {
        private final Scc scc;
        private final AtomicInteger dealingSccCount;
        private final Object lock;

        public SccRunnable(Scc scc, AtomicInteger dealingSccCount, Object lock) {
            this.scc = scc;
            this.dealingSccCount = dealingSccCount;
            this.lock = lock;
        }

        @Override
        public void run() {
            sccProcessCounter.get(scc).incrementAndGet();
            dealWithScc(scc);
            List<Scc> list = scc.getParents().stream().filter(item -> item.unpin() == 0).collect(Collectors.toList());
            addStageIfNeed(list);
            dealingSccCount.decrementAndGet();

            synchronized (lock) {
                lock.notify();
            }
        }
    }

    private void dealWithScc(Scc scc) {
        System.out.println(scc.toStringSet());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addStageIfNeed(List<Scc> sccList) {
        if (!sccList.isEmpty()) {
            Stage stage = new Stage(sccList);
            stageCount.incrementAndGet();
            queue.add(stage);
        }
    }

    public int getStageCount() {
        return stageCount.get();
    }
}

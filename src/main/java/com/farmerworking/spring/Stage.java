package com.farmerworking.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Stage {
    public static AtomicInteger SEQUENCE = new AtomicInteger(1);

    private int sequence;
    private List<Scc> sccList = new ArrayList<>();

    public Stage(List<Scc> sccList) {
        this.sequence = SEQUENCE.getAndIncrement();
        this.sccList = sccList;
    }

    public List<Scc> getSccList() {
        return sccList;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "sequence=" + sequence + ",count=" + sccList.size() +
                '}';
    }
}

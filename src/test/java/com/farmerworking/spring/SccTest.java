package com.farmerworking.spring;

import com.farmerworking.spring.utils.Utils;
import com.google.common.collect.Sets;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by John on 18/12/6.
 */
public class SccTest {
    @Test
    public void testGetAdjacentNodes() throws Exception {
        Scc scc = new Scc();
        Node A = new Node("A");
        Node B = new Node("B");
        A.addChild(B);
        B.addChild(A);

        scc.addNode(A);
        scc.addNode(B);

        assertTrue(scc.getAdjacentNodes().isEmpty());

        Node C = new Node("C");
        A.addChild(C);
        assertEquals(Sets.newHashSet("C"), Utils.nodeList2IdStringSet(scc.getAdjacentNodes()));

        B.addChild(C);
        assertEquals(Sets.newHashSet("C"), Utils.nodeList2IdStringSet(scc.getAdjacentNodes()));

        Node D = new Node("D");
        C.addChild(D);
        assertEquals(Sets.newHashSet("C"), Utils.nodeList2IdStringSet(scc.getAdjacentNodes()));

        B.addChild(D);
        assertEquals(Sets.newHashSet("C", "D"), Utils.nodeList2IdStringSet(scc.getAdjacentNodes()));
    }

    @Test
    public void testToStringSet() throws Exception {
        Scc scc = new Scc();
        assertTrue(scc.toStringSet().isEmpty());

        Node A = new Node("A");
        Node B = new Node("B");
        scc.addNode(A);
        scc.addNode(B);
        assertEquals(Sets.newHashSet("A", "B"), scc.toStringSet());
    }

    @Test(expected = AssertionError.class)
    public void testUnpin() throws Exception {
        Scc scc = new Scc();
        scc.unpin();
    }
}
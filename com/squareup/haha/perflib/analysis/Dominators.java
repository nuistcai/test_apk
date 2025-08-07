package com.squareup.haha.perflib.analysis;

import com.squareup.haha.guava.collect.ImmutableList;
import com.squareup.haha.guava.collect.Iterables;
import com.squareup.haha.perflib.Heap;
import com.squareup.haha.perflib.Instance;
import com.squareup.haha.perflib.Snapshot;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Dominators {
    private final Snapshot mSnapshot;
    private final ImmutableList<Instance> mTopSort;

    public Dominators(Snapshot snapshot, ImmutableList<Instance> topSort) {
        this.mSnapshot = snapshot;
        this.mTopSort = topSort;
        Iterator i$ = snapshot.getGCRoots().iterator();
        while (i$.hasNext()) {
            Instance ref = i$.next().getReferredInstance();
            if (ref != null) {
                ref.setImmediateDominator(Snapshot.SENTINEL_ROOT);
            }
        }
    }

    private void computeDominators() {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i < this.mTopSort.size(); i++) {
                Instance node = this.mTopSort.get(i);
                if (node.getImmediateDominator() != Snapshot.SENTINEL_ROOT) {
                    Instance dominator = null;
                    for (int j = 0; j < node.getHardReferences().size(); j++) {
                        Instance predecessor = node.getHardReferences().get(j);
                        if (predecessor.getImmediateDominator() != null) {
                            if (dominator == null) {
                                dominator = predecessor;
                            } else {
                                Instance fingerA = dominator;
                                Instance fingerB = predecessor;
                                while (fingerA != fingerB) {
                                    if (fingerA.getTopologicalOrder() < fingerB.getTopologicalOrder()) {
                                        fingerB = fingerB.getImmediateDominator();
                                    } else {
                                        fingerA = fingerA.getImmediateDominator();
                                    }
                                }
                                dominator = fingerA;
                            }
                        }
                    }
                    if (node.getImmediateDominator() != dominator) {
                        node.setImmediateDominator(dominator);
                        changed = true;
                    }
                }
            }
        }
    }

    public void computeRetainedSizes() {
        for (Heap heap : this.mSnapshot.getHeaps()) {
            Iterator i$ = Iterables.concat(heap.getClasses(), heap.getInstances()).iterator();
            while (i$.hasNext()) {
                ((Instance) i$.next()).resetRetainedSize();
            }
        }
        computeDominators();
        for (Instance node : this.mSnapshot.getReachableInstances()) {
            int heapIndex = this.mSnapshot.getHeapIndex(node.getHeap());
            for (Instance dom = node.getImmediateDominator(); dom != Snapshot.SENTINEL_ROOT; dom = dom.getImmediateDominator()) {
                dom.addRetainedSize(heapIndex, node.getSize());
            }
        }
    }
}
package com.squareup.haha.perflib.analysis;

import com.squareup.haha.perflib.Instance;
import com.squareup.haha.perflib.NonRecursiveVisitor;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/* loaded from: classes.dex */
public class ShortestDistanceVisitor extends NonRecursiveVisitor {
    private PriorityQueue<Instance> mPriorityQueue = new PriorityQueue<>(1024, new Comparator<Instance>() { // from class: com.squareup.haha.perflib.analysis.ShortestDistanceVisitor.1
        @Override // java.util.Comparator
        public int compare(Instance o1, Instance o2) {
            return o1.getDistanceToGcRoot() - o2.getDistanceToGcRoot();
        }
    });
    private Instance mPreviousInstance = null;
    private int mVisitDistance = 0;

    @Override // com.squareup.haha.perflib.NonRecursiveVisitor, com.squareup.haha.perflib.Visitor
    public void visitLater(Instance parent, Instance child) {
        if (this.mVisitDistance < child.getDistanceToGcRoot()) {
            if (parent == null || child.getSoftReferences() == null || !child.getSoftReferences().contains(parent) || child.getIsSoftReference()) {
                child.setDistanceToGcRoot(this.mVisitDistance);
                child.setNextInstanceToGcRoot(this.mPreviousInstance);
                this.mPriorityQueue.add(child);
            }
        }
    }

    @Override // com.squareup.haha.perflib.NonRecursiveVisitor
    public void doVisit(Iterable<? extends Instance> startNodes) {
        Iterator i$ = startNodes.iterator();
        while (i$.hasNext()) {
            i$.next().accept(this);
        }
        while (!this.mPriorityQueue.isEmpty()) {
            Instance node = this.mPriorityQueue.poll();
            this.mVisitDistance = node.getDistanceToGcRoot() + 1;
            this.mPreviousInstance = node;
            node.accept(this);
        }
    }
}
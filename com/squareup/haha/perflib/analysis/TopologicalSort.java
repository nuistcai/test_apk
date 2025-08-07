package com.squareup.haha.perflib.analysis;

import com.squareup.haha.guava.base.Joiner;
import com.squareup.haha.guava.collect.ImmutableList;
import com.squareup.haha.perflib.Instance;
import com.squareup.haha.perflib.NonRecursiveVisitor;
import com.squareup.haha.perflib.RootObj;
import com.squareup.haha.perflib.Snapshot;
import com.squareup.haha.trove.TLongHashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class TopologicalSort {
    public static ImmutableList<Instance> compute(Iterable<RootObj> roots) {
        TopologicalSortVisitor visitor = new TopologicalSortVisitor();
        visitor.doVisit(roots);
        ImmutableList<Instance> instances = visitor.getOrderedInstances();
        Snapshot.SENTINEL_ROOT.setTopologicalOrder(0);
        int currentIndex = 0;
        Iterator i$ = instances.iterator();
        while (i$.hasNext()) {
            currentIndex++;
            ((Instance) i$.next()).setTopologicalOrder(currentIndex);
        }
        return instances;
    }

    static class TopologicalSortVisitor extends NonRecursiveVisitor {
        private final List<Instance> mPostorder;
        private final TLongHashSet mVisited;

        private TopologicalSortVisitor() {
            this.mVisited = new TLongHashSet();
            this.mPostorder = new ArrayList();
        }

        @Override // com.squareup.haha.perflib.NonRecursiveVisitor, com.squareup.haha.perflib.Visitor
        public void visitLater(Instance parent, Instance child) {
            if (!this.mSeen.contains(child.getId())) {
                this.mStack.push(child);
            }
        }

        @Override // com.squareup.haha.perflib.NonRecursiveVisitor
        public void doVisit(Iterable<? extends Instance> startNodes) {
            Iterator i$ = startNodes.iterator();
            while (i$.hasNext()) {
                i$.next().accept(this);
            }
            while (!this.mStack.isEmpty()) {
                Instance node = this.mStack.peek();
                if (this.mSeen.add(node.getId())) {
                    node.accept(this);
                } else {
                    this.mStack.pop();
                    if (this.mVisited.add(node.getId())) {
                        this.mPostorder.add(node);
                    }
                }
            }
        }

        ImmutableList<Instance> getOrderedInstances() {
            return ImmutableList.copyOf((Collection) Joiner.reverse(this.mPostorder));
        }
    }
}
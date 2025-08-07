package com.squareup.haha.perflib;

import com.squareup.haha.trove.TLongHashSet;
import java.util.ArrayDeque;
import java.util.Deque;

/* loaded from: classes.dex */
public class NonRecursiveVisitor implements Visitor {
    public final Deque<Instance> mStack = new ArrayDeque();
    public final TLongHashSet mSeen = new TLongHashSet();

    protected void defaultAction(Instance instance) {
    }

    @Override // com.squareup.haha.perflib.Visitor
    public void visitRootObj(RootObj root) {
        defaultAction(root);
    }

    @Override // com.squareup.haha.perflib.Visitor
    public void visitArrayInstance(ArrayInstance instance) {
        defaultAction(instance);
    }

    @Override // com.squareup.haha.perflib.Visitor
    public void visitClassInstance(ClassInstance instance) {
        defaultAction(instance);
    }

    @Override // com.squareup.haha.perflib.Visitor
    public void visitClassObj(ClassObj instance) {
        defaultAction(instance);
    }

    @Override // com.squareup.haha.perflib.Visitor
    public void visitLater(Instance parent, Instance child) {
        this.mStack.push(child);
    }

    public void doVisit(Iterable<? extends Instance> startNodes) {
        for (Instance node : startNodes) {
            if (!(node instanceof RootObj)) {
                visitLater(null, node);
            } else {
                node.accept(this);
            }
        }
        while (!this.mStack.isEmpty()) {
            Instance node2 = this.mStack.pop();
            if (this.mSeen.add(node2.getId())) {
                node2.accept(this);
            }
        }
    }
}
package pl.droidsonroids.gif;

/* loaded from: classes.dex */
class ConditionVariable {
    private volatile boolean mCondition;

    ConditionVariable() {
    }

    synchronized void set(boolean state) {
        if (state) {
            open();
        } else {
            close();
        }
    }

    synchronized void open() {
        boolean old = this.mCondition;
        this.mCondition = true;
        if (!old) {
            notify();
        }
    }

    synchronized void close() {
        this.mCondition = false;
    }

    synchronized void block() throws InterruptedException {
        while (!this.mCondition) {
            wait();
        }
    }
}
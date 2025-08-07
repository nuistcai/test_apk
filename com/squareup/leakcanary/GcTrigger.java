package com.squareup.leakcanary;

/* loaded from: classes.dex */
public interface GcTrigger {
    public static final GcTrigger DEFAULT = new GcTrigger() { // from class: com.squareup.leakcanary.GcTrigger.1
        @Override // com.squareup.leakcanary.GcTrigger
        public void runGc() throws InterruptedException {
            Runtime.getRuntime().gc();
            enqueueReferences();
            System.runFinalization();
        }

        private void enqueueReferences() throws InterruptedException {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new AssertionError();
            }
        }
    };

    void runGc();
}
package kotlin.concurrent;

import kotlin.Metadata;

/* compiled from: Thread.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, d2 = {"kotlin/concurrent/ThreadsKt$thread$thread$1", "Ljava/lang/Thread;", "run", "", "kotlin-stdlib"}, k = 1, mv = {1, 1, 15})
/* renamed from: kotlin.concurrent.ThreadsKt$thread$thread$1 */
/* loaded from: classes.dex */
public final class Thread2 extends Thread {
    Thread2() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        block.invoke();
    }
}
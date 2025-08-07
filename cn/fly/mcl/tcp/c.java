package cn.fly.mcl.tcp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class c implements Future<e> {
    final CountDownLatch a = new CountDownLatch(1);
    final AtomicReference<e> b = new AtomicReference<>();

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return this.a.getCount() == 0;
    }

    @Override // java.util.concurrent.Future
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public e get() throws ExecutionException, InterruptedException {
        this.a.await();
        return this.b.get();
    }

    @Override // java.util.concurrent.Future
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public e get(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        if (!this.a.await(j, timeUnit)) {
            throw new TimeoutException("tcp get msg timeout");
        }
        return this.b.get();
    }

    public void a(e eVar) {
        synchronized (this.a) {
            this.b.set(eVar);
            this.a.countDown();
        }
    }
}
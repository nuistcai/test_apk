package cn.fly.verify;

import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.verify.common.exception.VerifyException;

/* loaded from: classes.dex */
public interface ResultCallback<R> extends PublicMemberKeeper {

    public static class a<R> {
        public final c<R> a;
        public final d b;

        public a(b<R> bVar) {
            this.a = ((b) bVar).a;
            this.b = ((b) bVar).b;
        }
    }

    public static class b<R> implements PublicMemberKeeper {
        private c<R> a;
        private d b;
    }

    public interface c<R> {
        void a(R r);
    }

    public interface d {
        void a(VerifyException verifyException);
    }

    void initCallback(b<R> bVar);
}
package cn.fly.verify;

import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.verify.ResultCallback;
import cn.fly.verify.datatype.VerifyResult;

/* loaded from: classes.dex */
public interface VerifyResultCallback extends PublicMemberKeeper {

    public interface a {
        void a();
    }

    public interface b {
        void a();
    }

    public static class c extends ResultCallback.b<VerifyResult> implements PublicMemberKeeper {
        private b a;
        private a b;
    }

    public static class d extends ResultCallback.a<VerifyResult> {
        public b c;
        public a d;

        public d(c cVar) {
            super(cVar);
            this.c = cVar.a;
            this.d = cVar.b;
        }
    }

    void initCallback(c cVar);
}
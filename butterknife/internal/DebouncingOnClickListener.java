package butterknife.internal;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

/* loaded from: classes.dex */
public abstract class DebouncingOnClickListener implements View.OnClickListener {
    private static final Runnable ENABLE_AGAIN = new Runnable() { // from class: butterknife.internal.DebouncingOnClickListener$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            DebouncingOnClickListener.enabled = true;
        }
    };
    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    static boolean enabled = true;

    public abstract void doClick(View view);

    @Override // android.view.View.OnClickListener
    public final void onClick(View v) {
        if (enabled) {
            enabled = false;
            MAIN.post(ENABLE_AGAIN);
            doClick(v);
        }
    }
}
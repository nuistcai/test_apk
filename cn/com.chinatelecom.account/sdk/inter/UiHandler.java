package cn.com.chinatelecom.account.sdk.inter;

import android.view.View;

/* loaded from: classes.dex */
public interface UiHandler {
    void closeActivity();

    void continueExecution();

    void execOtherLoginWayAction();

    void executeLogin();

    View findViewById(int i);
}
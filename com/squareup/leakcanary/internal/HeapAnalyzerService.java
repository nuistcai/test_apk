package com.squareup.leakcanary.internal;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.squareup.leakcanary.AbstractAnalysisResultService;
import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.CanaryLog;
import com.squareup.leakcanary.HeapAnalyzer;
import com.squareup.leakcanary.HeapDump;

/* loaded from: classes.dex */
public final class HeapAnalyzerService extends IntentService {
    private static final String HEAPDUMP_EXTRA = "heapdump_extra";
    private static final String LISTENER_CLASS_EXTRA = "listener_class_extra";

    public static void runAnalysis(Context context, HeapDump heapDump, Class<? extends AbstractAnalysisResultService> listenerServiceClass) {
        Intent intent = new Intent(context, (Class<?>) HeapAnalyzerService.class);
        intent.putExtra(LISTENER_CLASS_EXTRA, listenerServiceClass.getName());
        intent.putExtra(HEAPDUMP_EXTRA, heapDump);
        context.startService(intent);
    }

    public HeapAnalyzerService() {
        super(HeapAnalyzerService.class.getSimpleName());
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) throws ClassNotFoundException {
        if (intent == null) {
            CanaryLog.d("HeapAnalyzerService received a null intent, ignoring.", new Object[0]);
            return;
        }
        String listenerClassName = intent.getStringExtra(LISTENER_CLASS_EXTRA);
        HeapDump heapDump = (HeapDump) intent.getSerializableExtra(HEAPDUMP_EXTRA);
        HeapAnalyzer heapAnalyzer = new HeapAnalyzer(heapDump.excludedRefs);
        AnalysisResult result = heapAnalyzer.checkForLeak(heapDump.heapDumpFile, heapDump.referenceKey);
        AbstractAnalysisResultService.sendResultToListener(this, listenerClassName, heapDump, result);
    }
}
package com.squareup.leakcanary.internal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.BuildConfig;
import com.squareup.leakcanary.CanaryLog;
import com.squareup.leakcanary.DefaultLeakDirectoryProvider;
import com.squareup.leakcanary.HeapDump;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.LeakDirectoryProvider;
import com.squareup.leakcanary.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class DisplayLeakActivity extends Activity {
    private static final String SHOW_LEAK_EXTRA = "show_latest";
    private static LeakDirectoryProvider leakDirectoryProvider = null;
    private Button actionButton;
    private TextView failureView;
    List<Leak> leaks;
    private ListView listView;
    String visibleLeakRefKey;

    public static PendingIntent createPendingIntent(Context context) {
        return createPendingIntent(context, null);
    }

    public static PendingIntent createPendingIntent(Context context, String referenceKey) {
        Intent intent = new Intent(context, (Class<?>) DisplayLeakActivity.class);
        intent.putExtra(SHOW_LEAK_EXTRA, referenceKey);
        intent.setFlags(335544320);
        return PendingIntent.getActivity(context, 1, intent, 134217728);
    }

    public static void setLeakDirectoryProvider(LeakDirectoryProvider leakDirectoryProvider2) {
        leakDirectoryProvider = leakDirectoryProvider2;
    }

    private static LeakDirectoryProvider leakDirectoryProvider(Context context) {
        LeakDirectoryProvider leakDirectoryProvider2 = leakDirectoryProvider;
        if (leakDirectoryProvider2 == null) {
            return new DefaultLeakDirectoryProvider(context);
        }
        return leakDirectoryProvider2;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.visibleLeakRefKey = savedInstanceState.getString("visibleLeakRefKey");
        } else {
            Intent intent = getIntent();
            if (intent.hasExtra(SHOW_LEAK_EXTRA)) {
                this.visibleLeakRefKey = intent.getStringExtra(SHOW_LEAK_EXTRA);
            }
        }
        this.leaks = (List) getLastNonConfigurationInstance();
        setContentView(R.layout.leak_canary_display_leak);
        this.listView = (ListView) findViewById(R.id.leak_canary_display_leak_list);
        this.failureView = (TextView) findViewById(R.id.leak_canary_display_leak_failure);
        this.actionButton = (Button) findViewById(R.id.leak_canary_action);
        updateUi();
    }

    @Override // android.app.Activity
    public Object onRetainNonConfigurationInstance() {
        return this.leaks;
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("visibleLeakRefKey", this.visibleLeakRefKey);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        LoadLeaks.load(this, leakDirectoryProvider(this));
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public void setTheme(int resid) {
        if (resid != R.style.leak_canary_LeakCanary_Base) {
            return;
        }
        super.setTheme(resid);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        LoadLeaks.forgetActivity();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        Leak visibleLeak = getVisibleLeak();
        if (visibleLeak != null) {
            menu.add(R.string.leak_canary_share_leak).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.squareup.leakcanary.internal.DisplayLeakActivity.1
                @Override // android.view.MenuItem.OnMenuItemClickListener
                public boolean onMenuItemClick(MenuItem item) throws PackageManager.NameNotFoundException {
                    DisplayLeakActivity.this.shareLeak();
                    return true;
                }
            });
            if (visibleLeak.heapDump.heapDumpFile.exists()) {
                menu.add(R.string.leak_canary_share_heap_dump).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.squareup.leakcanary.internal.DisplayLeakActivity.2
                    @Override // android.view.MenuItem.OnMenuItemClickListener
                    public boolean onMenuItemClick(MenuItem item) {
                        DisplayLeakActivity.this.shareHeapDump();
                        return true;
                    }
                });
                return true;
            }
            return true;
        }
        return false;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            this.visibleLeakRefKey = null;
            updateUi();
            return true;
        }
        return true;
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.visibleLeakRefKey != null) {
            this.visibleLeakRefKey = null;
            updateUi();
        } else {
            super.onBackPressed();
        }
    }

    void shareLeak() throws PackageManager.NameNotFoundException {
        Leak visibleLeak = getVisibleLeak();
        String leakInfo = LeakCanary.leakInfo(this, visibleLeak.heapDump, visibleLeak.result, true);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", leakInfo);
        startActivity(Intent.createChooser(intent, getString(R.string.leak_canary_share_with)));
    }

    void shareHeapDump() {
        Leak visibleLeak = getVisibleLeak();
        File heapDumpFile = visibleLeak.heapDump.heapDumpFile;
        heapDumpFile.setReadable(true, false);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("application/octet-stream");
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(heapDumpFile));
        startActivity(Intent.createChooser(intent, getString(R.string.leak_canary_share_with)));
    }

    void deleteVisibleLeak() {
        Leak visibleLeak = getVisibleLeak();
        File heapDumpFile = visibleLeak.heapDump.heapDumpFile;
        File resultFile = visibleLeak.resultFile;
        boolean resultDeleted = resultFile.delete();
        if (!resultDeleted) {
            CanaryLog.d("Could not delete result file %s", resultFile.getPath());
        }
        boolean heapDumpDeleted = heapDumpFile.delete();
        if (!heapDumpDeleted) {
            CanaryLog.d("Could not delete heap dump file %s", heapDumpFile.getPath());
        }
        this.visibleLeakRefKey = null;
        this.leaks.remove(visibleLeak);
        updateUi();
    }

    void deleteAllLeaks() {
        leakDirectoryProvider(this).clearLeakDirectory();
        this.leaks = Collections.emptyList();
        updateUi();
    }

    void updateUi() {
        final DisplayLeakAdapter adapter;
        if (this.leaks == null) {
            setTitle("Loading leaks...");
            return;
        }
        if (this.leaks.isEmpty()) {
            this.visibleLeakRefKey = null;
        }
        Leak visibleLeak = getVisibleLeak();
        if (visibleLeak == null) {
            this.visibleLeakRefKey = null;
        }
        ListAdapter listAdapter = this.listView.getAdapter();
        this.listView.setVisibility(0);
        this.failureView.setVisibility(8);
        if (visibleLeak != null) {
            AnalysisResult result = visibleLeak.result;
            if (result.failure != null) {
                this.listView.setVisibility(8);
                this.failureView.setVisibility(0);
                String failureMessage = getString(R.string.leak_canary_failure_report) + BuildConfig.LIBRARY_VERSION + " " + BuildConfig.GIT_SHA + "\n" + Log.getStackTraceString(result.failure);
                this.failureView.setText(failureMessage);
                setTitle(R.string.leak_canary_analysis_failed);
                invalidateOptionsMenu();
                getActionBar().setDisplayHomeAsUpEnabled(true);
                this.actionButton.setVisibility(0);
                this.actionButton.setText(R.string.leak_canary_delete);
                this.actionButton.setOnClickListener(new View.OnClickListener() { // from class: com.squareup.leakcanary.internal.DisplayLeakActivity.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        DisplayLeakActivity.this.deleteVisibleLeak();
                    }
                });
                this.listView.setAdapter((ListAdapter) null);
                return;
            }
            if (listAdapter instanceof DisplayLeakAdapter) {
                adapter = (DisplayLeakAdapter) listAdapter;
            } else {
                adapter = new DisplayLeakAdapter();
                this.listView.setAdapter((ListAdapter) adapter);
                this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.squareup.leakcanary.internal.DisplayLeakActivity.4
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        adapter.toggleRow(position);
                    }
                });
                invalidateOptionsMenu();
                getActionBar().setDisplayHomeAsUpEnabled(true);
                this.actionButton.setVisibility(0);
                this.actionButton.setText(R.string.leak_canary_delete);
                this.actionButton.setOnClickListener(new View.OnClickListener() { // from class: com.squareup.leakcanary.internal.DisplayLeakActivity.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        DisplayLeakActivity.this.deleteVisibleLeak();
                    }
                });
            }
            HeapDump heapDump = visibleLeak.heapDump;
            adapter.update(result.leakTrace, heapDump.referenceKey, heapDump.referenceName);
            String size = Formatter.formatShortFileSize(this, result.retainedHeapSize);
            String className = classSimpleName(result.className);
            setTitle(getString(R.string.leak_canary_class_has_leaked, new Object[]{className, size}));
            return;
        }
        if (listAdapter instanceof LeakListAdapter) {
            ((LeakListAdapter) listAdapter).notifyDataSetChanged();
        } else {
            this.listView.setAdapter((ListAdapter) new LeakListAdapter());
            this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.squareup.leakcanary.internal.DisplayLeakActivity.6
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DisplayLeakActivity.this.visibleLeakRefKey = DisplayLeakActivity.this.leaks.get(position).heapDump.referenceKey;
                    DisplayLeakActivity.this.updateUi();
                }
            });
            invalidateOptionsMenu();
            setTitle(getString(R.string.leak_canary_leak_list_title, new Object[]{getPackageName()}));
            getActionBar().setDisplayHomeAsUpEnabled(false);
            this.actionButton.setText(R.string.leak_canary_delete_all);
            this.actionButton.setOnClickListener(new View.OnClickListener() { // from class: com.squareup.leakcanary.internal.DisplayLeakActivity.7
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    new AlertDialog.Builder(DisplayLeakActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.leak_canary_delete_all).setMessage(R.string.leak_canary_delete_all_leaks_title).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.squareup.leakcanary.internal.DisplayLeakActivity.7.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                            DisplayLeakActivity.this.deleteAllLeaks();
                        }
                    }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
                }
            });
        }
        this.actionButton.setVisibility(this.leaks.size() == 0 ? 8 : 0);
    }

    Leak getVisibleLeak() {
        if (this.leaks == null) {
            return null;
        }
        for (Leak leak : this.leaks) {
            if (leak.heapDump.referenceKey.equals(this.visibleLeakRefKey)) {
                return leak;
            }
        }
        return null;
    }

    class LeakListAdapter extends BaseAdapter {
        LeakListAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return DisplayLeakActivity.this.leaks.size();
        }

        @Override // android.widget.Adapter
        public Leak getItem(int position) {
            return DisplayLeakActivity.this.leaks.get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            String title;
            if (convertView == null) {
                convertView = LayoutInflater.from(DisplayLeakActivity.this).inflate(R.layout.leak_canary_leak_row, parent, false);
            }
            TextView titleView = (TextView) convertView.findViewById(R.id.leak_canary_row_text);
            TextView timeView = (TextView) convertView.findViewById(R.id.leak_canary_row_time);
            Leak leak = getItem(position);
            String index = (DisplayLeakActivity.this.leaks.size() - position) + ". ";
            if (leak.result.failure == null) {
                String className = DisplayLeakActivity.classSimpleName(leak.result.className);
                String size = Formatter.formatShortFileSize(DisplayLeakActivity.this, leak.result.retainedHeapSize);
                String title2 = DisplayLeakActivity.this.getString(R.string.leak_canary_class_has_leaked, new Object[]{className, size});
                if (leak.result.excludedLeak) {
                    title2 = DisplayLeakActivity.this.getString(R.string.leak_canary_excluded_row, new Object[]{title2});
                }
                title = index + title2;
            } else {
                title = index + leak.result.failure.getClass().getSimpleName() + " " + leak.result.failure.getMessage();
            }
            titleView.setText(title);
            String time = DateUtils.formatDateTime(DisplayLeakActivity.this, leak.resultFile.lastModified(), 17);
            timeView.setText(time);
            return convertView;
        }
    }

    static class Leak {
        final HeapDump heapDump;
        final AnalysisResult result;
        final File resultFile;

        Leak(HeapDump heapDump, AnalysisResult result, File resultFile) {
            this.heapDump = heapDump;
            this.result = result;
            this.resultFile = resultFile;
        }
    }

    static class LoadLeaks implements Runnable {
        DisplayLeakActivity activityOrNull;
        private final LeakDirectoryProvider leakDirectoryProvider;
        private final Handler mainHandler = new Handler(Looper.getMainLooper());
        static final List<LoadLeaks> inFlight = new ArrayList();
        static final Executor backgroundExecutor = LeakCanaryInternals.newSingleThreadExecutor("LoadLeaks");

        static void load(DisplayLeakActivity activity, LeakDirectoryProvider leakDirectoryProvider) {
            LoadLeaks loadLeaks = new LoadLeaks(activity, leakDirectoryProvider);
            inFlight.add(loadLeaks);
            backgroundExecutor.execute(loadLeaks);
        }

        static void forgetActivity() {
            for (LoadLeaks loadLeaks : inFlight) {
                loadLeaks.activityOrNull = null;
            }
            inFlight.clear();
        }

        LoadLeaks(DisplayLeakActivity activity, LeakDirectoryProvider leakDirectoryProvider) {
            this.activityOrNull = activity;
            this.leakDirectoryProvider = leakDirectoryProvider;
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            final List<Leak> leaks = new ArrayList<>();
            List<File> files = this.leakDirectoryProvider.listFiles(new FilenameFilter() { // from class: com.squareup.leakcanary.internal.DisplayLeakActivity.LoadLeaks.1
                @Override // java.io.FilenameFilter
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".result");
                }
            });
            for (File resultFile : files) {
                FileInputStream fis = null;
                try {
                    try {
                        fis = new FileInputStream(resultFile);
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        HeapDump heapDump = (HeapDump) ois.readObject();
                        AnalysisResult result = (AnalysisResult) ois.readObject();
                        leaks.add(new Leak(heapDump, result, resultFile));
                        try {
                            fis.close();
                        } catch (IOException e) {
                        }
                    } catch (IOException | ClassNotFoundException e2) {
                        boolean deleted = resultFile.delete();
                        if (deleted) {
                            CanaryLog.d(e2, "Could not read result file %s, deleted it.", resultFile);
                        } else {
                            CanaryLog.d(e2, "Could not read result file %s, could not delete it either.", resultFile);
                        }
                        if (fis != null) {
                            fis.close();
                        }
                    }
                } catch (Throwable th) {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e3) {
                        }
                    }
                    throw th;
                }
            }
            Collections.sort(leaks, new Comparator<Leak>() { // from class: com.squareup.leakcanary.internal.DisplayLeakActivity.LoadLeaks.2
                @Override // java.util.Comparator
                public int compare(Leak lhs, Leak rhs) {
                    return Long.valueOf(rhs.resultFile.lastModified()).compareTo(Long.valueOf(lhs.resultFile.lastModified()));
                }
            });
            this.mainHandler.post(new Runnable() { // from class: com.squareup.leakcanary.internal.DisplayLeakActivity.LoadLeaks.3
                @Override // java.lang.Runnable
                public void run() {
                    LoadLeaks.inFlight.remove(LoadLeaks.this);
                    if (LoadLeaks.this.activityOrNull != null) {
                        LoadLeaks.this.activityOrNull.leaks = leaks;
                        LoadLeaks.this.activityOrNull.updateUi();
                    }
                }
            });
        }
    }

    static String classSimpleName(String className) {
        int separator = className.lastIndexOf(46);
        if (separator == -1) {
            return className;
        }
        return className.substring(separator + 1);
    }
}
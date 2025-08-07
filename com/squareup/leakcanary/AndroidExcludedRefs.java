package com.squareup.leakcanary;

import android.os.Build;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.EnumSet;
import java.util.Iterator;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'SPAN_CONTROLLER' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes.dex */
public abstract class AndroidExcludedRefs {
    private static final /* synthetic */ AndroidExcludedRefs[] $VALUES;
    public static final AndroidExcludedRefs ACCOUNT_MANAGER;
    public static final AndroidExcludedRefs ACTIVITY_CHOOSE_MODEL;
    public static final AndroidExcludedRefs ACTIVITY_CLIENT_RECORD__NEXT_IDLE;
    public static final AndroidExcludedRefs ACTIVITY_MANAGER_MCONTEXT;
    public static final AndroidExcludedRefs APP_WIDGET_HOST_CALLBACKS;
    public static final AndroidExcludedRefs AUDIO_MANAGER;
    public static final AndroidExcludedRefs AUDIO_MANAGER__MCONTEXT_STATIC;
    public static final AndroidExcludedRefs AW_RESOURCE__SRESOURCES;
    public static final AndroidExcludedRefs BLOCKING_QUEUE;
    public static final AndroidExcludedRefs BUBBLE_POPUP_HELPER__SHELPER;
    public static final AndroidExcludedRefs CLIPBOARD_UI_MANAGER__SINSTANCE;
    public static final AndroidExcludedRefs CONNECTIVITY_MANAGER__SINSTANCE;
    public static final AndroidExcludedRefs DEVICE_POLICY_MANAGER__SETTINGS_OBSERVER;
    public static final AndroidExcludedRefs EDITTEXT_BLINK_MESSAGEQUEUE;
    public static final AndroidExcludedRefs EVENT_RECEIVER__MMESSAGE_QUEUE;
    public static final AndroidExcludedRefs FINALIZER_WATCHDOG_DAEMON;
    public static final AndroidExcludedRefs GESTURE_BOOST_MANAGER;
    public static final AndroidExcludedRefs INPUT_METHOD_MANAGER__ROOT_VIEW;
    public static final AndroidExcludedRefs INPUT_METHOD_MANAGER__SERVED_VIEW;
    public static final AndroidExcludedRefs INSTRUMENTATION_RECOMMEND_ACTIVITY;
    public static final AndroidExcludedRefs LAYOUT_TRANSITION;
    public static final AndroidExcludedRefs LEAK_CANARY_THREAD;
    public static final AndroidExcludedRefs LGCONTEXT__MCONTEXT;
    public static final AndroidExcludedRefs MAIN;
    public static final AndroidExcludedRefs MAPPER_CLIENT;
    public static final AndroidExcludedRefs MEDIA_SCANNER_CONNECTION;
    public static final AndroidExcludedRefs MEDIA_SESSION_LEGACY_HELPER__SINSTANCE;
    public static final AndroidExcludedRefs PERSONA_MANAGER;
    public static final AndroidExcludedRefs RESOURCES__MCONTEXT;
    public static final AndroidExcludedRefs SEM_CLIPBOARD_MANAGER__MCONTEXT;
    public static final AndroidExcludedRefs SEM_EMERGENCY_MANAGER__MCONTEXT;
    public static final AndroidExcludedRefs SOFT_REFERENCES;
    public static final AndroidExcludedRefs SPAN_CONTROLLER;
    public static final AndroidExcludedRefs SPEECH_RECOGNIZER;
    public static final AndroidExcludedRefs SPELL_CHECKER_SESSION;
    public static final AndroidExcludedRefs SPEN_GESTURE_MANAGER;
    public static final AndroidExcludedRefs SYSTEM_SENSOR_MANAGER__MAPPCONTEXTIMPL;
    public static final AndroidExcludedRefs TEXT_LINE__SCACHED;
    public static final AndroidExcludedRefs TEXT_VIEW__MLAST_HOVERED_VIEW;
    public static final AndroidExcludedRefs USER_MANAGER__SINSTANCE;
    public static final AndroidExcludedRefs VIEW_CONFIGURATION__MCONTEXT;
    final boolean applies;

    abstract void add(ExcludedRefs.Builder builder);

    public static AndroidExcludedRefs valueOf(String name) {
        return (AndroidExcludedRefs) Enum.valueOf(AndroidExcludedRefs.class, name);
    }

    public static AndroidExcludedRefs[] values() {
        return (AndroidExcludedRefs[]) $VALUES.clone();
    }

    static {
        int i = 21;
        int i2 = 1;
        ACTIVITY_CLIENT_RECORD__NEXT_IDLE = new AndroidExcludedRefs("ACTIVITY_CLIENT_RECORD__NEXT_IDLE", 0, Build.VERSION.SDK_INT <= 21) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.1
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.app.ActivityThread$ActivityClientRecord", "nextIdle").reason("Android AOSP sometimes keeps a reference to a destroyed activity as a nextIdle client record in the android.app.ActivityThread.mActivities map. Not sure what's going on there, input welcome.");
            }
        };
        int i3 = 19;
        SPAN_CONTROLLER = new AndroidExcludedRefs("SPAN_CONTROLLER", i2, Build.VERSION.SDK_INT <= 19) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.2
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.widget.Editor$EasyEditSpanController", "this$0").reason("Editor inserts a special span, which has a reference to the EditText. That span is a NoCopySpan, which makes sure it gets dropped when creating a new SpannableStringBuilder from a given CharSequence. TextView.onSaveInstanceState() does a copy of its mText before saving it in the bundle. Prior to KitKat, that copy was done using the SpannableString constructor, instead of SpannableStringBuilder. The SpannableString constructor does not drop NoCopySpan spans. So we end up with a saved state that holds a reference to the textview and therefore the entire view hierarchy & activity context. Fix: https://github.com/android/platform_frameworks_base/commit/af7dcdf35a37d7a7dbaad7d9869c1c91bce2272b . To fix this, you could override TextView.onSaveInstanceState(), and then use reflection to access TextView.SavedState.mText and clear the NoCopySpan spans.");
                excluded.instanceField("android.widget.Editor$SpanController", "this$0").reason("Editor inserts a special span, which has a reference to the EditText. That span is a NoCopySpan, which makes sure it gets dropped when creating a new SpannableStringBuilder from a given CharSequence. TextView.onSaveInstanceState() does a copy of its mText before saving it in the bundle. Prior to KitKat, that copy was done using the SpannableString constructor, instead of SpannableStringBuilder. The SpannableString constructor does not drop NoCopySpan spans. So we end up with a saved state that holds a reference to the textview and therefore the entire view hierarchy & activity context. Fix: https://github.com/android/platform_frameworks_base/commit/af7dcdf35a37d7a7dbaad7d9869c1c91bce2272b . To fix this, you could override TextView.onSaveInstanceState(), and then use reflection to access TextView.SavedState.mText and clear the NoCopySpan spans.");
            }
        };
        MEDIA_SESSION_LEGACY_HELPER__SINSTANCE = new AndroidExcludedRefs("MEDIA_SESSION_LEGACY_HELPER__SINSTANCE", 2, Build.VERSION.SDK_INT == 21) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.3
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.staticField("android.media.session.MediaSessionLegacyHelper", "sInstance").reason("MediaSessionLegacyHelper is a static singleton that is lazily instantiated and keeps a reference to the context it's given the first time MediaSessionLegacyHelper.getHelper() is called. This leak was introduced in android-5.0.1_r1 and fixed in Android 5.1.0_r1 by calling context.getApplicationContext(). Fix: https://github.com/android/platform_frameworks_base/commit/9b5257c9c99c4cb541d8e8e78fb04f008b1a9091 To fix this, you could call MediaSessionLegacyHelper.getHelper() early in Application.onCreate() and pass it the application context.");
            }
        };
        int i4 = 22;
        TEXT_LINE__SCACHED = new AndroidExcludedRefs("TEXT_LINE__SCACHED", 3, Build.VERSION.SDK_INT <= 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.4
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.staticField("android.text.TextLine", "sCached").reason("TextLine.sCached is a pool of 3 TextLine instances. TextLine.recycle() has had at least two bugs that created memory leaks by not correctly clearing the recycled TextLine instances. The first was fixed in android-5.1.0_r1: https://github.com/android/platform_frameworks_base/commit/893d6fe48d37f71e683f722457bea646994a10 The second was fixed, not released yet: https://github.com/android/platform_frameworks_base/commit/b3a9bc038d3a218b1dbdf7b5668e3d6c12be5e To fix this, you could access TextLine.sCached and clear the pool every now and then (e.g. on activity destroy).");
            }
        };
        BLOCKING_QUEUE = new AndroidExcludedRefs("BLOCKING_QUEUE", 4, Build.VERSION.SDK_INT <= 21) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.5
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.os.Message", "obj").reason("Prior to ART, a thread waiting on a blocking queue will leak the last dequeued object as a stack local reference. So when a HandlerThread becomes idle, it keeps a local reference to the last message it received. That message then gets recycled and can be used again. As long as all messages are recycled after beingused, this won't be a problem, because these references are cleared when beingrecycled. However, dialogs create template Message instances to be copied when amessage needs to be sent. These Message templates holds references to the dialoglisteners, which most likely leads to holding a reference onto the activity in someway. Dialogs never recycle their template Message, assuming these Message instances will get GCed when the dialog is GCed. The combination of these two things creates a high potential for memory leaks as soon as you use dialogs. These memory leaks might be temporary, but some handler threads sleep for a long time. To fix this, you could post empty messages to the idle handler threads from time to time. This won't be easy because you cannot access all handler threads, but a librarythat is widely used should consider doing this for its own handler threads.");
                excluded.instanceField("android.os.Message", "next").reason("Prior to ART, a thread waiting on a blocking queue will leak the last dequeued object as a stack local reference. So when a HandlerThread becomes idle, it keeps a local reference to the last message it received. That message then gets recycled and can be used again. As long as all messages are recycled after beingused, this won't be a problem, because these references are cleared when beingrecycled. However, dialogs create template Message instances to be copied when amessage needs to be sent. These Message templates holds references to the dialoglisteners, which most likely leads to holding a reference onto the activity in someway. Dialogs never recycle their template Message, assuming these Message instances will get GCed when the dialog is GCed. The combination of these two things creates a high potential for memory leaks as soon as you use dialogs. These memory leaks might be temporary, but some handler threads sleep for a long time. To fix this, you could post empty messages to the idle handler threads from time to time. This won't be easy because you cannot access all handler threads, but a librarythat is widely used should consider doing this for its own handler threads.");
                excluded.instanceField("android.os.Message", "target").reason("Prior to ART, a thread waiting on a blocking queue will leak the last dequeued object as a stack local reference. So when a HandlerThread becomes idle, it keeps a local reference to the last message it received. That message then gets recycled and can be used again. As long as all messages are recycled after beingused, this won't be a problem, because these references are cleared when beingrecycled. However, dialogs create template Message instances to be copied when amessage needs to be sent. These Message templates holds references to the dialoglisteners, which most likely leads to holding a reference onto the activity in someway. Dialogs never recycle their template Message, assuming these Message instances will get GCed when the dialog is GCed. The combination of these two things creates a high potential for memory leaks as soon as you use dialogs. These memory leaks might be temporary, but some handler threads sleep for a long time. To fix this, you could post empty messages to the idle handler threads from time to time. This won't be easy because you cannot access all handler threads, but a librarythat is widely used should consider doing this for its own handler threads.");
            }
        };
        int i5 = 25;
        INPUT_METHOD_MANAGER__SERVED_VIEW = new AndroidExcludedRefs("INPUT_METHOD_MANAGER__SERVED_VIEW", 5, Build.VERSION.SDK_INT <= 25) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.6
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.view.inputmethod.InputMethodManager", "mNextServedView").reason("When we detach a view that receives keyboard input, the InputMethodManager leaks a reference to it until a new view asks for keyboard input. Tracked here: https://code.google.com/p/android/issues/detail?id=171190 Hack: https://gist.github.com/pyricau/4df64341cc978a7de414");
                excluded.instanceField("android.view.inputmethod.InputMethodManager", "mServedView").reason("When we detach a view that receives keyboard input, the InputMethodManager leaks a reference to it until a new view asks for keyboard input. Tracked here: https://code.google.com/p/android/issues/detail?id=171190 Hack: https://gist.github.com/pyricau/4df64341cc978a7de414");
                excluded.instanceField("android.view.inputmethod.InputMethodManager", "mServedInputConnection").reason("When we detach a view that receives keyboard input, the InputMethodManager leaks a reference to it until a new view asks for keyboard input. Tracked here: https://code.google.com/p/android/issues/detail?id=171190 Hack: https://gist.github.com/pyricau/4df64341cc978a7de414");
            }
        };
        int i6 = 23;
        INPUT_METHOD_MANAGER__ROOT_VIEW = new AndroidExcludedRefs("INPUT_METHOD_MANAGER__ROOT_VIEW", 6, Build.VERSION.SDK_INT <= 23) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.7
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.view.inputmethod.InputMethodManager", "mCurRootView").reason("The singleton InputMethodManager is holding a reference to mCurRootView long after the activity has been destroyed. Observed on ICS MR1: https://github.com/square/leakcanary/issues/1#issuecomment-100579429 Hack: https://gist.github.com/pyricau/4df64341cc978a7de414");
            }
        };
        LAYOUT_TRANSITION = new AndroidExcludedRefs("LAYOUT_TRANSITION", 7, Build.VERSION.SDK_INT <= 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.8
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.animation.LayoutTransition$1", "val$parent").reason("LayoutTransition leaks parent ViewGroup through ViewTreeObserver.OnPreDrawListener When triggered, this leaks stays until the window is destroyed. Tracked here: https://code.google.com/p/android/issues/detail?id=171830");
            }
        };
        int i7 = 24;
        SPELL_CHECKER_SESSION = new AndroidExcludedRefs("SPELL_CHECKER_SESSION", 8, Build.VERSION.SDK_INT <= 22 || Build.VERSION.SDK_INT >= 24) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.9
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.view.textservice.SpellCheckerSession$1", "this$0").reason("SpellCheckerSessionListenerImpl.mHandler is leaking destroyed Activity when the SpellCheckerSession is closed before the service is connected. Tracked here: https://code.google.com/p/android/issues/detail?id=172542");
            }
        };
        ACTIVITY_CHOOSE_MODEL = new AndroidExcludedRefs("ACTIVITY_CHOOSE_MODEL", 9, Build.VERSION.SDK_INT <= 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.10
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.support.v7.internal.widget.ActivityChooserModel", "mActivityChoserModelPolicy").reason("ActivityChooserModel holds a static reference to the last set ActivityChooserModelPolicy which can be an activity context. Tracked here: https://code.google.com/p/android/issues/detail?id=172659 Hack: https://gist.github.com/andaag/b05ab66ed0f06167d6e0");
                excluded.instanceField("android.widget.ActivityChooserModel", "mActivityChoserModelPolicy").reason("ActivityChooserModel holds a static reference to the last set ActivityChooserModelPolicy which can be an activity context. Tracked here: https://code.google.com/p/android/issues/detail?id=172659 Hack: https://gist.github.com/andaag/b05ab66ed0f06167d6e0");
            }
        };
        SPEECH_RECOGNIZER = new AndroidExcludedRefs("SPEECH_RECOGNIZER", 10, Build.VERSION.SDK_INT < 21) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.11
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.speech.SpeechRecognizer$InternalListener", "this$0").reason("Prior to Android 5, SpeechRecognizer.InternalListener was a non static inner class and leaked the SpeechRecognizer which leaked an activity context. Fixed in AOSP: https://github.com/android/platform_frameworks_base/commit /b37866db469e81aca534ff6186bdafd44352329b");
            }
        };
        ACCOUNT_MANAGER = new AndroidExcludedRefs("ACCOUNT_MANAGER", 11, Build.VERSION.SDK_INT <= 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.12
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.accounts.AccountManager$AmsTask$Response", "this$1").reason("AccountManager$AmsTask$Response is a stub and is held in memory by native code, probably because the reference to the response in the other process hasn't been cleared. AccountManager$AmsTask is holding on to the activity reference to use for launching a new sub- Activity. Tracked here: https://code.google.com/p/android/issues/detail?id=173689 Fix: Pass a null activity reference to the AccountManager methods and then deal with the returned future to to get the result and correctly start an activity when it's available.");
            }
        };
        MEDIA_SCANNER_CONNECTION = new AndroidExcludedRefs("MEDIA_SCANNER_CONNECTION", 12, Build.VERSION.SDK_INT <= 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.13
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.media.MediaScannerConnection", "mContext").reason("The static method MediaScannerConnection.scanFile() takes an activity context but the service might not disconnect after the activity has been destroyed. Tracked here: https://code.google.com/p/android/issues/detail?id=173788 Fix: Create an instance of MediaScannerConnection yourself and pass in the application context. Call connect() and disconnect() manually.");
            }
        };
        USER_MANAGER__SINSTANCE = new AndroidExcludedRefs("USER_MANAGER__SINSTANCE", 13, Build.VERSION.SDK_INT <= 23) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.14
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.os.UserManager", "mContext").reason("UserManager has a static sInstance field that creates an instance and caches it the first time UserManager.get() is called. This instance is created with the outer context (which is an activity base context). Tracked here: https://code.google.com/p/android/issues/detail?id=173789 Introduced by: https://github.com/android/platform_frameworks_base/commit/27db46850b708070452c0ce49daf5f79503fbde6 Fix: trigger a call to UserManager.get() in Application.onCreate(), so that the UserManager instance gets cached with a reference to the application context.");
            }
        };
        APP_WIDGET_HOST_CALLBACKS = new AndroidExcludedRefs("APP_WIDGET_HOST_CALLBACKS", 14, Build.VERSION.SDK_INT < 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.15
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.appwidget.AppWidgetHost$Callbacks", "this$0").reason("android.appwidget.AppWidgetHost$Callbacks is a stub and is held in memory native code. The reference to the `mContext` was not being cleared, which caused the Callbacks instance to retain this reference Fixed in AOSP: https://github.com/android/platform_frameworks_base/commit/7a96f3c917e0001ee739b65da37b2fadec7d7765");
            }
        };
        AUDIO_MANAGER = new AndroidExcludedRefs("AUDIO_MANAGER", 15, Build.VERSION.SDK_INT <= 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.16
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.media.AudioManager$1", "this$0").reason("Prior to Android M, VideoView required audio focus from AudioManager and never abandoned it, which leaks the Activity context through the AudioManager. The root of the problem is that AudioManager uses whichever context it receives, which in the case of the VideoView example is an Activity, even though it only needs the application's context. The issue is fixed in Android M, and the AudioManager now uses the application's context. Tracked here: https://code.google.com/p/android/issues/detail?id=152173 Fix: https://gist.github.com/jankovd/891d96f476f7a9ce24e2");
            }
        };
        EDITTEXT_BLINK_MESSAGEQUEUE = new AndroidExcludedRefs("EDITTEXT_BLINK_MESSAGEQUEUE", 16, Build.VERSION.SDK_INT <= 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.17
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.widget.Editor$Blink", "this$0").reason("The EditText Blink of the Cursor is implemented using a callback and Messages, which trigger the display of the Cursor. If an AlertDialog or DialogFragment that contains a blinking cursor is detached, a message is posted with a delay after the dialog has been closed and as a result leaks the Activity. This can be fixed manually by calling TextView.setCursorVisible(false) in the dismiss() method of the dialog. Tracked here: https://code.google.com/p/android/issues/detail?id=188551 Fixed in AOSP: https://android.googlesource.com/platform/frameworks/base/+/5b734f2430e9f26c769d6af8ea5645e390fcf5af%5E%21/");
            }
        };
        CONNECTIVITY_MANAGER__SINSTANCE = new AndroidExcludedRefs("CONNECTIVITY_MANAGER__SINSTANCE", 17, Build.VERSION.SDK_INT <= 23) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.18
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.net.ConnectivityManager", "sInstance").reason("ConnectivityManager has a sInstance field that is set when the firstConnectivityManager instance is created. ConnectivityManager has a mContext field.When calling activity.getSystemService(Context.CONNECTIVITY_SERVICE) , the firstConnectivityManager instance is created with the activity context and stored insInstance. That activity context then leaks forever.Until this is fixed, app developers can prevent this leak by making sure the ConnectivityManager is first created with an App Context. E.g. in some static init do: context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) Tracked here: https://code.google.com/p/android/issues/detail?id=198852 Introduced here: https://github.com/android/platform_frameworks_base/commit/e0bef71662d81caaaa0d7214fb0bef5d39996a69");
            }
        };
        INSTRUMENTATION_RECOMMEND_ACTIVITY = new AndroidExcludedRefs("INSTRUMENTATION_RECOMMEND_ACTIVITY", 18, LeakCanaryInternals.MEIZU.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.19
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.staticField("android.app.Instrumentation", "mRecommendActivity").reason("Instrumentation would leak com.android.internal.app.RecommendActivity (in framework.jar) in Meizu FlymeOS 4.5 and above, which is based on Android 5.0 and above");
            }
        };
        DEVICE_POLICY_MANAGER__SETTINGS_OBSERVER = new AndroidExcludedRefs("DEVICE_POLICY_MANAGER__SETTINGS_OBSERVER", i3, LeakCanaryInternals.MOTOROLA.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT <= 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.20
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                if (LeakCanaryInternals.MOTOROLA.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 19) {
                    excluded.instanceField("android.app.admin.DevicePolicyManager$SettingsObserver", "this$0").reason("DevicePolicyManager keeps a reference to the context it has been created with instead of extracting the application context. In this Motorola build, DevicePolicyManager has an inner SettingsObserver class that is a content observer, which is held into memory by a binder transport object.");
                }
            }
        };
        SPEN_GESTURE_MANAGER = new AndroidExcludedRefs("SPEN_GESTURE_MANAGER", 20, LeakCanaryInternals.SAMSUNG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 19) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.21
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.staticField("com.samsung.android.smartclip.SpenGestureManager", "mContext").reason("SpenGestureManager has a static mContext field that leaks a reference to the activity. Yes, a STATIC mContext field.");
            }
        };
        GESTURE_BOOST_MANAGER = new AndroidExcludedRefs("GESTURE_BOOST_MANAGER", i, LeakCanaryInternals.HUAWEI.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT >= 24 && Build.VERSION.SDK_INT <= 25) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.22
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.staticField("android.gestureboost.GestureBoostManager", "mContext").reason("GestureBoostManager is a static singleton that leaks an activity context.Fix: https://github.com/square/leakcanary/issues/696#issuecomment-296420756");
            }
        };
        CLIPBOARD_UI_MANAGER__SINSTANCE = new AndroidExcludedRefs("CLIPBOARD_UI_MANAGER__SINSTANCE", i4, LeakCanaryInternals.SAMSUNG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT <= 21) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.23
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.sec.clipboard.ClipboardUIManager", "mContext").reason("ClipboardUIManager is a static singleton that leaks an activity context. Fix: trigger a call to ClipboardUIManager.getInstance() in Application.onCreate() , so that the ClipboardUIManager instance gets cached with a reference to the application context. Example: https://gist.github.com/cypressious/91c4fb1455470d803a602838dfcd5774");
            }
        };
        SEM_CLIPBOARD_MANAGER__MCONTEXT = new AndroidExcludedRefs("SEM_CLIPBOARD_MANAGER__MCONTEXT", i6, LeakCanaryInternals.SAMSUNG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT <= 24) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.24
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("com.samsung.android.content.clipboard.SemClipboardManager", "mContext").reason("SemClipboardManager is held in memory by an anonymous inner class implementation of android.os.Binder, thereby leaking an activity context.");
            }
        };
        SEM_EMERGENCY_MANAGER__MCONTEXT = new AndroidExcludedRefs("SEM_EMERGENCY_MANAGER__MCONTEXT", i7, LeakCanaryInternals.SAMSUNG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT <= 24) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.25
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("com.samsung.android.emergencymode.SemEmergencyManager", "mContext").reason("SemEmergencyManager is a static singleton that leaks a DecorContext.");
            }
        };
        BUBBLE_POPUP_HELPER__SHELPER = new AndroidExcludedRefs("BUBBLE_POPUP_HELPER__SHELPER", i5, LeakCanaryInternals.LG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT <= 21) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.26
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.staticField("android.widget.BubblePopupHelper", "sHelper").reason("A static helper for EditText bubble popups leaks a reference to the latestfocused view.");
            }
        };
        LGCONTEXT__MCONTEXT = new AndroidExcludedRefs("LGCONTEXT__MCONTEXT", 26, LeakCanaryInternals.LG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 21) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.27
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("com.lge.systemservice.core.LGContext", "mContext").reason("LGContext is a static singleton that leaks an activity context.");
            }
        };
        AW_RESOURCE__SRESOURCES = new AndroidExcludedRefs("AW_RESOURCE__SRESOURCES", 27, LeakCanaryInternals.SAMSUNG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 19) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.28
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.staticField("com.android.org.chromium.android_webview.AwResource", "sResources");
            }
        };
        MAPPER_CLIENT = new AndroidExcludedRefs("MAPPER_CLIENT", 28, LeakCanaryInternals.NVIDIA.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 19) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.29
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("com.nvidia.ControllerMapper.MapperClient$ServiceClient", "this$0").reason("Not sure exactly what ControllerMapper is about, but there is an anonymous Handler in ControllerMapper.MapperClient.ServiceClient, which leaks ControllerMapper.MapperClient which leaks the activity context.");
            }
        };
        TEXT_VIEW__MLAST_HOVERED_VIEW = new AndroidExcludedRefs("TEXT_VIEW__MLAST_HOVERED_VIEW", 29, LeakCanaryInternals.SAMSUNG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT <= 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.30
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.staticField("android.widget.TextView", "mLastHoveredView").reason("mLastHoveredView is a static field in TextView that leaks the last hovered view.");
            }
        };
        PERSONA_MANAGER = new AndroidExcludedRefs("PERSONA_MANAGER", 30, LeakCanaryInternals.SAMSUNG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 19) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.31
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.os.PersonaManager", "mContext").reason("android.app.LoadedApk.mResources has a reference to android.content.res.Resources.mPersonaManager which has a reference to android.os.PersonaManager.mContext which is an activity.");
            }
        };
        RESOURCES__MCONTEXT = new AndroidExcludedRefs("RESOURCES__MCONTEXT", 31, LeakCanaryInternals.SAMSUNG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 19) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.32
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.content.res.Resources", "mContext").reason("In AOSP the Resources class does not have a context. Here we have ZygoteInit.mResources (static field) holding on to a Resources instance that has a context that is the activity. Observed here: https://github.com/square/leakcanary/issues/1#issue-74450184");
            }
        };
        VIEW_CONFIGURATION__MCONTEXT = new AndroidExcludedRefs("VIEW_CONFIGURATION__MCONTEXT", 32, LeakCanaryInternals.SAMSUNG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 19) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.33
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.view.ViewConfiguration", "mContext").reason("In AOSP the ViewConfiguration class does not have a context. Here we have ViewConfiguration.sConfigurations (static field) holding on to a ViewConfiguration instance that has a context that is the activity. Observed here: https://github.com/square/leakcanary/issues/1#issuecomment-100324683");
            }
        };
        SYSTEM_SENSOR_MANAGER__MAPPCONTEXTIMPL = new AndroidExcludedRefs("SYSTEM_SENSOR_MANAGER__MAPPCONTEXTIMPL", 33, (LeakCanaryInternals.LENOVO.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 19) || (LeakCanaryInternals.VIVO.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 22)) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.34
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.staticField("android.hardware.SystemSensorManager", "mAppContextImpl").reason("SystemSensorManager stores a reference to context in a static field in its constructor.Fix: use application context to get SensorManager");
            }
        };
        AUDIO_MANAGER__MCONTEXT_STATIC = new AndroidExcludedRefs("AUDIO_MANAGER__MCONTEXT_STATIC", 34, LeakCanaryInternals.SAMSUNG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 19) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.35
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.staticField("android.media.AudioManager", "mContext_static").reason("Samsung added a static mContext_static field to AudioManager, holds a reference to the activity. Observed here: https://github.com/square/leakcanary/issues/32");
            }
        };
        ACTIVITY_MANAGER_MCONTEXT = new AndroidExcludedRefs("ACTIVITY_MANAGER_MCONTEXT", 35, LeakCanaryInternals.SAMSUNG.equals(Build.MANUFACTURER) && Build.VERSION.SDK_INT == 22) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.36
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.staticField("android.app.ActivityManager", "mContext").reason("Samsung added a static mContext field to ActivityManager, holds a reference to the activity. Observed here: https://github.com/square/leakcanary/issues/177 Fix in comment: https://github.com/square/leakcanary/issues/177#issuecomment-222724283");
            }
        };
        SOFT_REFERENCES = new AndroidExcludedRefs("SOFT_REFERENCES", 36) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.37
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.clazz(WeakReference.class.getName()).alwaysExclude();
                excluded.clazz(SoftReference.class.getName()).alwaysExclude();
                excluded.clazz(PhantomReference.class.getName()).alwaysExclude();
                excluded.clazz("java.lang.ref.Finalizer").alwaysExclude();
                excluded.clazz("java.lang.ref.FinalizerReference").alwaysExclude();
            }
        };
        FINALIZER_WATCHDOG_DAEMON = new AndroidExcludedRefs("FINALIZER_WATCHDOG_DAEMON", 37) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.38
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.thread("FinalizerWatchdogDaemon").alwaysExclude();
            }
        };
        MAIN = new AndroidExcludedRefs("MAIN", 38) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.39
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.thread("main").alwaysExclude();
            }
        };
        LEAK_CANARY_THREAD = new AndroidExcludedRefs("LEAK_CANARY_THREAD", 39) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.40
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.thread("LeakCanary-Heap-Dump").alwaysExclude();
            }
        };
        EVENT_RECEIVER__MMESSAGE_QUEUE = new AndroidExcludedRefs("EVENT_RECEIVER__MMESSAGE_QUEUE", 40) { // from class: com.squareup.leakcanary.AndroidExcludedRefs.41
            @Override // com.squareup.leakcanary.AndroidExcludedRefs
            void add(ExcludedRefs.Builder excluded) {
                excluded.instanceField("android.view.Choreographer$FrameDisplayEventReceiver", "mMessageQueue").alwaysExclude();
            }
        };
        $VALUES = new AndroidExcludedRefs[]{ACTIVITY_CLIENT_RECORD__NEXT_IDLE, SPAN_CONTROLLER, MEDIA_SESSION_LEGACY_HELPER__SINSTANCE, TEXT_LINE__SCACHED, BLOCKING_QUEUE, INPUT_METHOD_MANAGER__SERVED_VIEW, INPUT_METHOD_MANAGER__ROOT_VIEW, LAYOUT_TRANSITION, SPELL_CHECKER_SESSION, ACTIVITY_CHOOSE_MODEL, SPEECH_RECOGNIZER, ACCOUNT_MANAGER, MEDIA_SCANNER_CONNECTION, USER_MANAGER__SINSTANCE, APP_WIDGET_HOST_CALLBACKS, AUDIO_MANAGER, EDITTEXT_BLINK_MESSAGEQUEUE, CONNECTIVITY_MANAGER__SINSTANCE, INSTRUMENTATION_RECOMMEND_ACTIVITY, DEVICE_POLICY_MANAGER__SETTINGS_OBSERVER, SPEN_GESTURE_MANAGER, GESTURE_BOOST_MANAGER, CLIPBOARD_UI_MANAGER__SINSTANCE, SEM_CLIPBOARD_MANAGER__MCONTEXT, SEM_EMERGENCY_MANAGER__MCONTEXT, BUBBLE_POPUP_HELPER__SHELPER, LGCONTEXT__MCONTEXT, AW_RESOURCE__SRESOURCES, MAPPER_CLIENT, TEXT_VIEW__MLAST_HOVERED_VIEW, PERSONA_MANAGER, RESOURCES__MCONTEXT, VIEW_CONFIGURATION__MCONTEXT, SYSTEM_SENSOR_MANAGER__MAPPCONTEXTIMPL, AUDIO_MANAGER__MCONTEXT_STATIC, ACTIVITY_MANAGER_MCONTEXT, SOFT_REFERENCES, FINALIZER_WATCHDOG_DAEMON, MAIN, LEAK_CANARY_THREAD, EVENT_RECEIVER__MMESSAGE_QUEUE};
    }

    public static ExcludedRefs.Builder createAndroidDefaults() {
        return createBuilder(EnumSet.of(SOFT_REFERENCES, FINALIZER_WATCHDOG_DAEMON, MAIN, LEAK_CANARY_THREAD, EVENT_RECEIVER__MMESSAGE_QUEUE));
    }

    public static ExcludedRefs.Builder createAppDefaults() {
        return createBuilder(EnumSet.allOf(AndroidExcludedRefs.class));
    }

    public static ExcludedRefs.Builder createBuilder(EnumSet<AndroidExcludedRefs> refs) {
        ExcludedRefs.Builder excluded = ExcludedRefs.builder();
        Iterator it = refs.iterator();
        while (it.hasNext()) {
            AndroidExcludedRefs ref = (AndroidExcludedRefs) it.next();
            if (ref.applies) {
                ref.add(excluded);
                ((ExcludedRefs.BuilderWithParams) excluded).named(ref.name());
            }
        }
        return excluded;
    }

    private AndroidExcludedRefs(String str, int i) {
        this(str, i, true);
    }

    private AndroidExcludedRefs(String str, int i, boolean applies) {
        this.applies = applies;
    }
}
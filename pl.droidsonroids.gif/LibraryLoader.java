package pl.droidsonroids.gif;

import android.content.Context;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class LibraryLoader {
    static final String BASE_LIBRARY_NAME = "pl_droidsonroids_gif";
    static final String SURFACE_LIBRARY_NAME = "pl_droidsonroids_gif_surface";
    private static Context sAppContext;

    private LibraryLoader() {
    }

    public static void initialize(Context context) {
        sAppContext = context.getApplicationContext();
    }

    private static Context getContext() throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        if (sAppContext == null) {
            try {
                Class<?> activityThread = Class.forName("android.app.ActivityThread");
                Method currentApplicationMethod = activityThread.getDeclaredMethod("currentApplication", new Class[0]);
                sAppContext = (Context) currentApplicationMethod.invoke(null, new Object[0]);
            } catch (Exception e) {
                throw new IllegalStateException("LibraryLoader not initialized. Call LibraryLoader.initialize() before using library classes.", e);
            }
        }
        return sAppContext;
    }

    static void loadLibrary() {
        try {
            System.loadLibrary(BASE_LIBRARY_NAME);
        } catch (UnsatisfiedLinkError e) {
            ReLinker.loadLibrary(getContext());
        }
    }
}
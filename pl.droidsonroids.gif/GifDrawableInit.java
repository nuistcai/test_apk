package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import pl.droidsonroids.gif.GifDrawableInit;
import pl.droidsonroids.gif.InputSource;

/* loaded from: classes.dex */
public abstract class GifDrawableInit<T extends GifDrawableInit<T>> {
    private ScheduledThreadPoolExecutor mExecutor;
    private InputSource mInputSource;
    private GifDrawable mOldDrawable;
    private boolean mIsRenderingTriggeredOnDraw = true;
    private GifOptions mOptions = new GifOptions();

    protected abstract T self();

    public T sampleSize(int i) {
        this.mOptions.setInSampleSize(i);
        return (T) self();
    }

    public GifDrawable build() throws IOException {
        if (this.mInputSource == null) {
            throw new NullPointerException("Source is not set");
        }
        return this.mInputSource.build(this.mOldDrawable, this.mExecutor, this.mIsRenderingTriggeredOnDraw, this.mOptions);
    }

    public T with(GifDrawable gifDrawable) {
        this.mOldDrawable = gifDrawable;
        return (T) self();
    }

    public T threadPoolSize(int i) {
        this.mExecutor = new ScheduledThreadPoolExecutor(i);
        return (T) self();
    }

    public T taskExecutor(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        this.mExecutor = scheduledThreadPoolExecutor;
        return (T) self();
    }

    public T renderingTriggeredOnDraw(boolean z) {
        this.mIsRenderingTriggeredOnDraw = z;
        return (T) self();
    }

    public T setRenderingTriggeredOnDraw(boolean z) {
        return (T) renderingTriggeredOnDraw(z);
    }

    public T options(GifOptions gifOptions) {
        this.mOptions.setFrom(gifOptions);
        return (T) self();
    }

    public T from(InputStream inputStream) {
        this.mInputSource = new InputSource.InputStreamSource(inputStream);
        return (T) self();
    }

    public T from(AssetFileDescriptor assetFileDescriptor) {
        this.mInputSource = new InputSource.AssetFileDescriptorSource(assetFileDescriptor);
        return (T) self();
    }

    public T from(FileDescriptor fileDescriptor) {
        this.mInputSource = new InputSource.FileDescriptorSource(fileDescriptor);
        return (T) self();
    }

    public T from(AssetManager assetManager, String str) {
        this.mInputSource = new InputSource.AssetSource(assetManager, str);
        return (T) self();
    }

    public T from(ContentResolver contentResolver, Uri uri) {
        this.mInputSource = new InputSource.UriSource(contentResolver, uri);
        return (T) self();
    }

    public T from(File file) {
        this.mInputSource = new InputSource.FileSource(file);
        return (T) self();
    }

    public T from(String str) {
        this.mInputSource = new InputSource.FileSource(str);
        return (T) self();
    }

    public T from(byte[] bArr) {
        this.mInputSource = new InputSource.ByteArraySource(bArr);
        return (T) self();
    }

    public T from(ByteBuffer byteBuffer) {
        this.mInputSource = new InputSource.DirectByteBufferSource(byteBuffer);
        return (T) self();
    }

    public T from(Resources resources, int i) {
        this.mInputSource = new InputSource.ResourcesSource(resources, i);
        return (T) self();
    }

    public InputSource getInputSource() {
        return this.mInputSource;
    }

    public GifDrawable getOldDrawable() {
        return this.mOldDrawable;
    }

    public ScheduledThreadPoolExecutor getExecutor() {
        return this.mExecutor;
    }

    public boolean isRenderingTriggeredOnDraw() {
        return this.mIsRenderingTriggeredOnDraw;
    }

    public GifOptions getOptions() {
        return this.mOptions;
    }
}
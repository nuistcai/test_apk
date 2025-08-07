package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.system.ErrnoException;
import android.system.Os;
import android.view.Surface;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import kotlin.text.Typography;

/* loaded from: classes.dex */
final class GifInfoHandle {
    private volatile long gifInfoPtr;

    private static native void bindSurface(long j, Surface surface, long[] jArr);

    static native int createTempNativeFileDescriptor() throws GifIOException;

    static native int extractNativeFileDescriptor(FileDescriptor fileDescriptor, boolean z) throws GifIOException;

    private static native void free(long j);

    private static native long getAllocationByteCount(long j);

    private static native String getComment(long j);

    private static native int getCurrentFrameIndex(long j);

    private static native int getCurrentLoop(long j);

    private static native int getCurrentPosition(long j);

    private static native int getDuration(long j);

    private static native int getFrameDuration(long j, int i);

    private static native int getHeight(long j);

    private static native int getLoopCount(long j);

    private static native long getMetadataByteCount(long j);

    private static native int getNativeErrorCode(long j);

    private static native int getNumberOfFrames(long j);

    private static native long[] getSavedState(long j);

    private static native long getSourceLength(long j);

    private static native int getWidth(long j);

    private static native void glTexImage2D(long j, int i, int i2);

    private static native void glTexSubImage2D(long j, int i, int i2);

    private static native void initTexImageDescriptor(long j);

    private static native boolean isAnimationCompleted(long j);

    private static native boolean isOpaque(long j);

    static native long openByteArray(byte[] bArr) throws GifIOException;

    static native long openDirectByteBuffer(ByteBuffer byteBuffer) throws GifIOException;

    static native long openFile(String str) throws GifIOException;

    static native long openNativeFileDescriptor(int i, long j) throws GifIOException;

    static native long openStream(InputStream inputStream) throws GifIOException;

    private static native void postUnbindSurface(long j);

    private static native long renderFrame(long j, Bitmap bitmap);

    private static native boolean reset(long j);

    private static native long restoreRemainder(long j);

    private static native int restoreSavedState(long j, long[] jArr, Bitmap bitmap);

    private static native void saveRemainder(long j);

    private static native void seekToFrame(long j, int i, Bitmap bitmap);

    private static native void seekToFrameGL(long j, int i);

    private static native void seekToTime(long j, int i, Bitmap bitmap);

    private static native void setLoopCount(long j, char c);

    private static native void setOptions(long j, char c, boolean z);

    private static native void setSpeedFactor(long j, float f);

    private static native void startDecoderThread(long j);

    private static native void stopDecoderThread(long j);

    static {
        LibraryLoader.loadLibrary();
    }

    GifInfoHandle() {
    }

    GifInfoHandle(FileDescriptor fileDescriptor) throws GifIOException {
        this.gifInfoPtr = openFileDescriptor(fileDescriptor, 0L, true);
    }

    GifInfoHandle(byte[] bytes) throws GifIOException {
        this.gifInfoPtr = openByteArray(bytes);
    }

    GifInfoHandle(ByteBuffer buffer) throws GifIOException {
        this.gifInfoPtr = openDirectByteBuffer(buffer);
    }

    GifInfoHandle(String filePath) throws GifIOException {
        this.gifInfoPtr = openFile(filePath);
    }

    GifInfoHandle(InputStream stream) throws GifIOException {
        if (!stream.markSupported()) {
            throw new IllegalArgumentException("InputStream does not support marking");
        }
        this.gifInfoPtr = openStream(stream);
    }

    GifInfoHandle(AssetFileDescriptor afd) throws IOException {
        try {
            this.gifInfoPtr = openFileDescriptor(afd.getFileDescriptor(), afd.getStartOffset(), false);
            try {
                afd.close();
            } catch (IOException e) {
            }
        } catch (Throwable th) {
            try {
                afd.close();
            } catch (IOException e2) {
            }
            throw th;
        }
    }

    private static long openFileDescriptor(FileDescriptor fileDescriptor, long offset, boolean closeOriginalDescriptor) throws GifIOException {
        int nativeFileDescriptor;
        if (Build.VERSION.SDK_INT > 27) {
            try {
                nativeFileDescriptor = getNativeFileDescriptor(fileDescriptor, closeOriginalDescriptor);
            } catch (Exception e) {
                throw new GifIOException(GifError.OPEN_FAILED.errorCode, e.getMessage());
            }
        } else {
            nativeFileDescriptor = extractNativeFileDescriptor(fileDescriptor, closeOriginalDescriptor);
        }
        return openNativeFileDescriptor(nativeFileDescriptor, offset);
    }

    private static int getNativeFileDescriptor(FileDescriptor fileDescriptor, boolean closeOriginalDescriptor) throws GifIOException, ErrnoException {
        try {
            int nativeFileDescriptor = createTempNativeFileDescriptor();
            Os.dup2(fileDescriptor, nativeFileDescriptor);
            return nativeFileDescriptor;
        } finally {
            if (closeOriginalDescriptor) {
                Os.close(fileDescriptor);
            }
        }
    }

    static GifInfoHandle openUri(ContentResolver resolver, Uri uri) throws IOException {
        if ("file".equals(uri.getScheme())) {
            return new GifInfoHandle(uri.getPath());
        }
        AssetFileDescriptor assetFileDescriptor = resolver.openAssetFileDescriptor(uri, "r");
        if (assetFileDescriptor == null) {
            throw new IOException("Could not open AssetFileDescriptor for " + uri);
        }
        return new GifInfoHandle(assetFileDescriptor);
    }

    synchronized long renderFrame(Bitmap frameBuffer) {
        return renderFrame(this.gifInfoPtr, frameBuffer);
    }

    void bindSurface(Surface surface, long[] savedState) {
        bindSurface(this.gifInfoPtr, surface, savedState);
    }

    synchronized void recycle() {
        free(this.gifInfoPtr);
        this.gifInfoPtr = 0L;
    }

    synchronized long restoreRemainder() {
        return restoreRemainder(this.gifInfoPtr);
    }

    synchronized boolean reset() {
        return reset(this.gifInfoPtr);
    }

    synchronized void saveRemainder() {
        saveRemainder(this.gifInfoPtr);
    }

    synchronized String getComment() {
        return getComment(this.gifInfoPtr);
    }

    synchronized int getLoopCount() {
        return getLoopCount(this.gifInfoPtr);
    }

    void setLoopCount(int loopCount) {
        if (loopCount < 0 || loopCount > 65535) {
            throw new IllegalArgumentException("Loop count of range <0, 65535>");
        }
        synchronized (this) {
            setLoopCount(this.gifInfoPtr, (char) loopCount);
        }
    }

    synchronized long getSourceLength() {
        return getSourceLength(this.gifInfoPtr);
    }

    synchronized int getNativeErrorCode() {
        return getNativeErrorCode(this.gifInfoPtr);
    }

    void setSpeedFactor(float factor) {
        float factor2;
        if (factor <= 0.0f || Float.isNaN(factor)) {
            throw new IllegalArgumentException("Speed factor is not positive");
        }
        if (factor >= 4.656613E-10f) {
            factor2 = factor;
        } else {
            factor2 = 4.656613E-10f;
        }
        synchronized (this) {
            setSpeedFactor(this.gifInfoPtr, factor2);
        }
    }

    synchronized int getDuration() {
        return getDuration(this.gifInfoPtr);
    }

    synchronized int getCurrentPosition() {
        return getCurrentPosition(this.gifInfoPtr);
    }

    synchronized int getCurrentFrameIndex() {
        return getCurrentFrameIndex(this.gifInfoPtr);
    }

    synchronized int getCurrentLoop() {
        return getCurrentLoop(this.gifInfoPtr);
    }

    synchronized void seekToTime(int position, Bitmap buffer) {
        seekToTime(this.gifInfoPtr, position, buffer);
    }

    synchronized void seekToFrame(int frameIndex, Bitmap buffer) {
        seekToFrame(this.gifInfoPtr, frameIndex, buffer);
    }

    synchronized long getAllocationByteCount() {
        return getAllocationByteCount(this.gifInfoPtr);
    }

    synchronized long getMetadataByteCount() {
        return getMetadataByteCount(this.gifInfoPtr);
    }

    synchronized boolean isRecycled() {
        return this.gifInfoPtr == 0;
    }

    protected void finalize() throws Throwable {
        try {
            recycle();
        } finally {
            super.finalize();
        }
    }

    synchronized void postUnbindSurface() {
        postUnbindSurface(this.gifInfoPtr);
    }

    synchronized boolean isAnimationCompleted() {
        return isAnimationCompleted(this.gifInfoPtr);
    }

    synchronized long[] getSavedState() {
        return getSavedState(this.gifInfoPtr);
    }

    synchronized int restoreSavedState(long[] savedState, Bitmap mBuffer) {
        return restoreSavedState(this.gifInfoPtr, savedState, mBuffer);
    }

    synchronized int getFrameDuration(int index) {
        throwIfFrameIndexOutOfBounds(index);
        return getFrameDuration(this.gifInfoPtr, index);
    }

    void setOptions(char sampleSize, boolean isOpaque) {
        setOptions(this.gifInfoPtr, sampleSize, isOpaque);
    }

    synchronized int getWidth() {
        return getWidth(this.gifInfoPtr);
    }

    synchronized int getHeight() {
        return getHeight(this.gifInfoPtr);
    }

    synchronized int getNumberOfFrames() {
        return getNumberOfFrames(this.gifInfoPtr);
    }

    synchronized boolean isOpaque() {
        return isOpaque(this.gifInfoPtr);
    }

    void glTexImage2D(int target, int level) {
        glTexImage2D(this.gifInfoPtr, target, level);
    }

    void glTexSubImage2D(int target, int level) {
        glTexSubImage2D(this.gifInfoPtr, target, level);
    }

    void startDecoderThread() {
        startDecoderThread(this.gifInfoPtr);
    }

    void stopDecoderThread() {
        stopDecoderThread(this.gifInfoPtr);
    }

    void initTexImageDescriptor() {
        initTexImageDescriptor(this.gifInfoPtr);
    }

    void seekToFrameGL(int index) {
        throwIfFrameIndexOutOfBounds(index);
        seekToFrameGL(this.gifInfoPtr, index);
    }

    private void throwIfFrameIndexOutOfBounds(int index) {
        int numberOfFrames = getNumberOfFrames(this.gifInfoPtr);
        if (index < 0 || index >= numberOfFrames) {
            throw new IndexOutOfBoundsException("Frame index is not in range <0;" + numberOfFrames + Typography.greater);
        }
    }
}
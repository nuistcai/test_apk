package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.StrictMode;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    public static File getTempFile(Context context) {
        String prefix = CACHE_FILE_PREFIX + Process.myPid() + "-" + Process.myTid() + "-";
        for (int i = 0; i < 100; i++) {
            File file = new File(context.getCacheDir(), prefix + i);
            if (file.createNewFile()) {
                return file;
            }
        }
        return null;
    }

    private static ByteBuffer mmap(File file) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(file);
            try {
                FileChannel channel = fis.getChannel();
                long size = channel.size();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
                fis.close();
                return map;
            } finally {
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static ByteBuffer mmap(Context context, CancellationSignal cancellationSignal, Uri uri) throws IOException {
        ContentResolver resolver = context.getContentResolver();
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r", cancellationSignal);
            if (pfd != null) {
                try {
                    FileInputStream fis = new FileInputStream(pfd.getFileDescriptor());
                    try {
                        FileChannel channel = fis.getChannel();
                        long size = channel.size();
                        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
                        fis.close();
                        if (pfd != null) {
                            pfd.close();
                        }
                        return map;
                    } finally {
                    }
                } finally {
                }
            } else {
                if (pfd != null) {
                    pfd.close();
                }
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        File tmpFile = getTempFile(context);
        ByteBuffer byteBufferMmap = null;
        if (tmpFile == null) {
            return null;
        }
        try {
            if (copyToFile(tmpFile, res, id)) {
                byteBufferMmap = mmap(tmpFile);
            }
            return byteBufferMmap;
        } finally {
            tmpFile.delete();
        }
    }

    public static boolean copyToFile(File file, InputStream is) throws IOException {
        FileOutputStream os = null;
        StrictMode.ThreadPolicy old = StrictMode.allowThreadDiskWrites();
        try {
            try {
                os = new FileOutputStream(file, false);
                byte[] buffer = new byte[1024];
                while (true) {
                    int readLen = is.read(buffer);
                    if (readLen == -1) {
                        closeQuietly(os);
                        StrictMode.setThreadPolicy(old);
                        return true;
                    }
                    os.write(buffer, 0, readLen);
                }
            } catch (IOException e) {
                Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
                closeQuietly(os);
                StrictMode.setThreadPolicy(old);
                return false;
            }
        } catch (Throwable th) {
            closeQuietly(os);
            StrictMode.setThreadPolicy(old);
            throw th;
        }
    }

    public static boolean copyToFile(File file, Resources res, int id) throws IOException {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            return copyToFile(file, is);
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) throws IOException {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
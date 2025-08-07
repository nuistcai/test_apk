package pl.droidsonroids.gif;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: classes.dex */
class ReLinker {
    private static final int COPY_BUFFER_SIZE = 8192;
    private static final String LIB_DIR = "lib";
    private static final String MAPPED_BASE_LIB_NAME = System.mapLibraryName("pl_droidsonroids_gif");
    private static final int MAX_TRIES = 5;

    private ReLinker() {
    }

    static void loadLibrary(Context context) {
        synchronized (ReLinker.class) {
            File workaroundFile = unpackLibrary(context);
            System.load(workaroundFile.getAbsolutePath());
        }
    }

    private static File unpackLibrary(Context context) throws IOException {
        String outputFileName = MAPPED_BASE_LIB_NAME + BuildConfig.VERSION_NAME;
        File outputFile = new File(context.getDir(LIB_DIR, 0), outputFileName);
        if (outputFile.isFile()) {
            return outputFile;
        }
        File cachedLibraryFile = new File(context.getCacheDir(), outputFileName);
        if (cachedLibraryFile.isFile()) {
            return cachedLibraryFile;
        }
        final String mappedSurfaceLibraryName = System.mapLibraryName("pl_droidsonroids_gif_surface");
        FilenameFilter filter = new FilenameFilter() { // from class: pl.droidsonroids.gif.ReLinker.1
            @Override // java.io.FilenameFilter
            public boolean accept(File dir, String filename) {
                return filename.startsWith(ReLinker.MAPPED_BASE_LIB_NAME) || filename.startsWith(mappedSurfaceLibraryName);
            }
        };
        clearOldLibraryFiles(outputFile, filter);
        clearOldLibraryFiles(cachedLibraryFile, filter);
        ApplicationInfo appInfo = context.getApplicationInfo();
        File apkFile = new File(appInfo.sourceDir);
        ZipFile zipFile = null;
        try {
            zipFile = openZipFile(apkFile);
            int tries = 0;
            while (true) {
                int tries2 = tries + 1;
                if (tries >= 5) {
                    break;
                }
                ZipEntry libraryEntry = findLibraryEntry(zipFile);
                if (libraryEntry == null) {
                    throw new IllegalStateException("Library " + MAPPED_BASE_LIB_NAME + " for supported ABIs not found in APK file");
                }
                InputStream inputStream = null;
                FileOutputStream fileOut = null;
                try {
                    inputStream = zipFile.getInputStream(libraryEntry);
                    fileOut = new FileOutputStream(outputFile);
                    copy(inputStream, fileOut);
                    closeSilently(inputStream);
                    closeSilently(fileOut);
                    setFilePermissions(outputFile);
                    break;
                } catch (IOException e) {
                    if (tries2 > 2) {
                        outputFile = cachedLibraryFile;
                    }
                    closeSilently(inputStream);
                    closeSilently(fileOut);
                    tries = tries2;
                } catch (Throwable th) {
                    closeSilently(inputStream);
                    closeSilently(fileOut);
                    throw th;
                }
            }
            return outputFile;
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    private static ZipEntry findLibraryEntry(ZipFile zipFile) {
        for (String abi : getSupportedABIs()) {
            ZipEntry libraryEntry = getEntry(zipFile, abi);
            if (libraryEntry != null) {
                return libraryEntry;
            }
        }
        return null;
    }

    private static String[] getSupportedABIs() {
        if (Build.VERSION.SDK_INT >= 21) {
            return Build.SUPPORTED_ABIS;
        }
        return new String[]{Build.CPU_ABI, Build.CPU_ABI2};
    }

    private static ZipEntry getEntry(ZipFile zipFile, String abi) {
        return zipFile.getEntry("lib/" + abi + "/" + MAPPED_BASE_LIB_NAME);
    }

    private static ZipFile openZipFile(File apkFile) {
        int tries = 0;
        ZipFile zipFile = null;
        while (true) {
            int tries2 = tries + 1;
            if (tries >= 5) {
                break;
            }
            try {
                zipFile = new ZipFile(apkFile, 1);
                break;
            } catch (IOException e) {
                tries = tries2;
            }
        }
        if (zipFile == null) {
            throw new IllegalStateException("Could not open APK file: " + apkFile.getAbsolutePath());
        }
        return zipFile;
    }

    private static void clearOldLibraryFiles(File outputFile, FilenameFilter filter) {
        File[] fileList = outputFile.getParentFile().listFiles(filter);
        if (fileList != null) {
            for (File file : fileList) {
                file.delete();
            }
        }
    }

    private static void setFilePermissions(File outputFile) {
        outputFile.setReadable(true, false);
        outputFile.setExecutable(true, false);
        outputFile.setWritable(true);
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[8192];
        while (true) {
            int bytesRead = in.read(buf);
            if (bytesRead != -1) {
                out.write(buf, 0, bytesRead);
            } else {
                return;
            }
        }
    }

    private static void closeSilently(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
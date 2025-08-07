package cn.fly.commons.cc;

import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public final class u extends RuntimeException {
    public u(String str, Throwable th) {
        super(str, th);
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        printStream.println("" + getMessage());
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        printWriter.println("" + getMessage());
    }
}
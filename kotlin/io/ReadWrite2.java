package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.PlatformImplementations2;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;

/* compiled from: ReadWrite.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000b0\r\u001a\u0010\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u000e*\u00020\u0002\u001a\u0017\u0010\u0016\u001a\u00020\u000e*\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0087\b\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u000eH\u0087\b\u001a5\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c*\u00020\u00022\u0018\u0010\u001d\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\u0004\u0012\u0002H\u001c0\rH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001f\u0082\u0002\b\n\u0006\b\u0011(\u001e0\u0001¨\u0006 "}, d2 = {"buffered", "Ljava/io/BufferedReader;", "Ljava/io/Reader;", "bufferSize", "", "Ljava/io/BufferedWriter;", "Ljava/io/Writer;", "copyTo", "", "out", "forEachLine", "", "action", "Lkotlin/Function1;", "", "lineSequence", "Lkotlin/sequences/Sequence;", "readBytes", "", "Ljava/net/URL;", "readLines", "", "readText", "charset", "Ljava/nio/charset/Charset;", "reader", "Ljava/io/StringReader;", "useLines", "T", "block", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Reader;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}, k = 2, mv = {1, 1, 15})
/* renamed from: kotlin.io.TextStreamsKt, reason: use source file name */
/* loaded from: classes.dex */
public final class ReadWrite2 {
    static /* synthetic */ BufferedReader buffered$default(Reader $this$buffered, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            bufferSize = 8192;
        }
        return $this$buffered instanceof BufferedReader ? (BufferedReader) $this$buffered : new BufferedReader($this$buffered, bufferSize);
    }

    private static final BufferedReader buffered(Reader $this$buffered, int bufferSize) {
        return $this$buffered instanceof BufferedReader ? (BufferedReader) $this$buffered : new BufferedReader($this$buffered, bufferSize);
    }

    static /* synthetic */ BufferedWriter buffered$default(Writer $this$buffered, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            bufferSize = 8192;
        }
        return $this$buffered instanceof BufferedWriter ? (BufferedWriter) $this$buffered : new BufferedWriter($this$buffered, bufferSize);
    }

    private static final BufferedWriter buffered(Writer $this$buffered, int bufferSize) {
        return $this$buffered instanceof BufferedWriter ? (BufferedWriter) $this$buffered : new BufferedWriter($this$buffered, bufferSize);
    }

    public static final void forEachLine(Reader forEachLine, Function1<? super String, Unit> action) {
        Intrinsics.checkParameterIsNotNull(forEachLine, "$this$forEachLine");
        Intrinsics.checkParameterIsNotNull(action, "action");
        BufferedReader bufferedReader = forEachLine instanceof BufferedReader ? (BufferedReader) forEachLine : new BufferedReader(forEachLine, 8192);
        try {
            BufferedReader it$iv = bufferedReader;
            Sequence it = lineSequence(it$iv);
            for (Object element$iv : it) {
                action.invoke(element$iv);
            }
            Unit unit = Unit.INSTANCE;
            Closeable.closeFinally(bufferedReader, null);
        } finally {
        }
    }

    public static final List<String> readLines(Reader readLines) {
        Intrinsics.checkParameterIsNotNull(readLines, "$this$readLines");
        final ArrayList result = new ArrayList();
        forEachLine(readLines, new Function1<String, Unit>() { // from class: kotlin.io.TextStreamsKt.readLines.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                result.add(it);
            }
        });
        return result;
    }

    public static final <T> T useLines(Reader useLines, Function1<? super Sequence<String>, ? extends T> block) throws IOException {
        Intrinsics.checkParameterIsNotNull(useLines, "$this$useLines");
        Intrinsics.checkParameterIsNotNull(block, "block");
        BufferedReader bufferedReader = useLines instanceof BufferedReader ? (BufferedReader) useLines : new BufferedReader(useLines, 8192);
        try {
            BufferedReader it = bufferedReader;
            T tInvoke = block.invoke(lineSequence(it));
            InlineMarker.finallyStart(1);
            if (PlatformImplementations2.apiVersionIsAtLeast(1, 1, 0)) {
                Closeable.closeFinally(bufferedReader, null);
            } else {
                bufferedReader.close();
            }
            InlineMarker.finallyEnd(1);
            return tInvoke;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                InlineMarker.finallyStart(1);
                if (PlatformImplementations2.apiVersionIsAtLeast(1, 1, 0)) {
                    Closeable.closeFinally(bufferedReader, th);
                } else {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th3) {
                    }
                }
                InlineMarker.finallyEnd(1);
                throw th2;
            }
        }
    }

    private static final StringReader reader(String $this$reader) {
        return new StringReader($this$reader);
    }

    public static final Sequence<String> lineSequence(BufferedReader lineSequence) {
        Intrinsics.checkParameterIsNotNull(lineSequence, "$this$lineSequence");
        return SequencesKt.constrainOnce(new ReadWrite(lineSequence));
    }

    public static final String readText(Reader readText) {
        Intrinsics.checkParameterIsNotNull(readText, "$this$readText");
        StringWriter buffer = new StringWriter();
        copyTo$default(readText, buffer, 0, 2, null);
        String string = buffer.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "buffer.toString()");
        return string;
    }

    public static /* synthetic */ long copyTo$default(Reader reader, Writer writer, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        return copyTo(reader, writer, i);
    }

    public static final long copyTo(Reader copyTo, Writer out, int bufferSize) throws IOException {
        Intrinsics.checkParameterIsNotNull(copyTo, "$this$copyTo");
        Intrinsics.checkParameterIsNotNull(out, "out");
        long charsCopied = 0;
        char[] buffer = new char[bufferSize];
        int chars = copyTo.read(buffer);
        while (chars >= 0) {
            out.write(buffer, 0, chars);
            charsCopied += chars;
            chars = copyTo.read(buffer);
        }
        return charsCopied;
    }

    private static final String readText(URL $this$readText, Charset charset) {
        return new String(readBytes($this$readText), charset);
    }

    static /* synthetic */ String readText$default(URL $this$readText, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return new String(readBytes($this$readText), charset);
    }

    public static final byte[] readBytes(URL readBytes) throws IOException {
        Intrinsics.checkParameterIsNotNull(readBytes, "$this$readBytes");
        InputStream inputStreamOpenStream = readBytes.openStream();
        try {
            InputStream it = inputStreamOpenStream;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            byte[] bytes = IOStreams.readBytes(it);
            Closeable.closeFinally(inputStreamOpenStream, null);
            return bytes;
        } finally {
        }
    }
}
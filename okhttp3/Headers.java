package okhttp3;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Tuples;
import kotlin.Tuples3;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.ArrayIterator2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.markers.KMarkers;
import kotlin.ranges.Progressions2;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okhttp3.internal.http.dates;

/* compiled from: Headers.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010 \n\u0002\b\u0006\u0018\u0000 '2\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0001:\u0002&'B\u0015\b\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u000b\u001a\u00020\fJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0012\u001a\u00020\u0003H\u0086\u0002J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0012\u001a\u00020\u0003J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0012\u001a\u00020\u0003H\u0007J\b\u0010\u0017\u001a\u00020\tH\u0016J\u001b\u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0019H\u0096\u0002J\u000e\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\tJ\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\u001cJ\u0006\u0010\u001d\u001a\u00020\u001eJ\r\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\b\u001fJ\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\"0!J\b\u0010#\u001a\u00020\u0003H\u0016J\u000e\u0010$\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\tJ\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00030\"2\u0006\u0010\u0012\u001a\u00020\u0003R\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8G¢\u0006\u0006\u001a\u0004\b\b\u0010\n¨\u0006("}, d2 = {"Lokhttp3/Headers;", "", "Lkotlin/Pair;", "", "namesAndValues", "", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "size", "", "()I", "byteCount", "", "equals", "", "other", "", "get", "name", "getDate", "Ljava/util/Date;", "getInstant", "Ljava/time/Instant;", "hashCode", "iterator", "", "index", "names", "", "newBuilder", "Lokhttp3/Headers$Builder;", "-deprecated_size", "toMultimap", "", "", "toString", "value", "values", "Builder", "Companion", "okhttp"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class Headers implements Iterable<Tuples<? extends String, ? extends String>>, KMarkers {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String[] namesAndValues;

    @JvmStatic
    public static final Headers of(Map<String, String> map) {
        return INSTANCE.of(map);
    }

    @JvmStatic
    public static final Headers of(String... strArr) {
        return INSTANCE.of(strArr);
    }

    private Headers(String[] namesAndValues) {
        this.namesAndValues = namesAndValues;
    }

    public /* synthetic */ Headers(String[] namesAndValues, DefaultConstructorMarker $constructor_marker) {
        this(namesAndValues);
    }

    public final String get(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return INSTANCE.get(this.namesAndValues, name);
    }

    public final Date getDate(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        String str = get(name);
        if (str != null) {
            return dates.toHttpDateOrNull(str);
        }
        return null;
    }

    public final Instant getInstant(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Date value = getDate(name);
        if (value != null) {
            return value.toInstant();
        }
        return null;
    }

    public final int size() {
        return this.namesAndValues.length / 2;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "size", imports = {}))
    /* renamed from: -deprecated_size, reason: not valid java name */
    public final int m1031deprecated_size() {
        return size();
    }

    public final String name(int index) {
        return this.namesAndValues[index * 2];
    }

    public final String value(int index) {
        return this.namesAndValues[(index * 2) + 1];
    }

    public final Set<String> names() {
        TreeSet result = new TreeSet(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
        int size = size();
        for (int i = 0; i < size; i++) {
            result.add(name(i));
        }
        Set<String> setUnmodifiableSet = Collections.unmodifiableSet(result);
        Intrinsics.checkExpressionValueIsNotNull(setUnmodifiableSet, "Collections.unmodifiableSet(result)");
        return setUnmodifiableSet;
    }

    public final List<String> values(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        List result = (List) null;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (StringsKt.equals(name, name(i), true)) {
                if (result == null) {
                    List result2 = new ArrayList(2);
                    result = result2;
                }
                result.add(value(i));
            }
        }
        if (result != null) {
            List<String> listUnmodifiableList = Collections.unmodifiableList(result);
            Intrinsics.checkExpressionValueIsNotNull(listUnmodifiableList, "Collections.unmodifiableList(result)");
            return listUnmodifiableList;
        }
        return CollectionsKt.emptyList();
    }

    public final long byteCount() {
        long result = this.namesAndValues.length * 2;
        for (int i = 0; i < this.namesAndValues.length; i++) {
            result += this.namesAndValues[i].length();
        }
        return result;
    }

    @Override // java.lang.Iterable
    public Iterator<Tuples<? extends String, ? extends String>> iterator() {
        int size = size();
        Tuples[] tuplesArr = new Tuples[size];
        for (int i = 0; i < size; i++) {
            int it = i;
            tuplesArr[i] = Tuples3.to(name(it), value(it));
        }
        return ArrayIterator2.iterator(tuplesArr);
    }

    public final Builder newBuilder() {
        Builder result = new Builder();
        CollectionsKt.addAll(result.getNamesAndValues$okhttp(), this.namesAndValues);
        return result;
    }

    public boolean equals(Object other) {
        return (other instanceof Headers) && Arrays.equals(this.namesAndValues, ((Headers) other).namesAndValues);
    }

    public int hashCode() {
        return Arrays.hashCode(this.namesAndValues);
    }

    public String toString() {
        StringBuilder $this$buildString = new StringBuilder();
        int size = size();
        for (int i = 0; i < size; i++) {
            $this$buildString.append(name(i));
            $this$buildString.append(": ");
            $this$buildString.append(value(i));
            $this$buildString.append("\n");
        }
        String string = $this$buildString.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public final Map<String, List<String>> toMultimap() {
        TreeMap result = new TreeMap(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
        int size = size();
        for (int i = 0; i < size; i++) {
            String strName = name(i);
            Locale locale = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(locale, "Locale.US");
            if (strName == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String name = strName.toLowerCase(locale);
            Intrinsics.checkExpressionValueIsNotNull(name, "(this as java.lang.String).toLowerCase(locale)");
            List values = (List) result.get(name);
            if (values == null) {
                values = new ArrayList(2);
                result.put(name, values);
            }
            values.add(value(i));
        }
        return result;
    }

    /* compiled from: Headers.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0005J\u0018\u0010\b\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0016\u0010\b\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\rJ\u0016\u0010\b\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0010J\u0015\u0010\u0011\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u0012J\u001d\u0010\u0011\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u0012J\u0016\u0010\u0013\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005J\u0006\u0010\u0014\u001a\u00020\u0010J\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u00052\u0006\u0010\n\u001a\u00020\u0005H\u0086\u0002J\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0005J\u0019\u0010\u0017\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0087\u0002J\u0019\u0010\u0017\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\rH\u0086\u0002J\u0019\u0010\u0017\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0086\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0018"}, d2 = {"Lokhttp3/Headers$Builder;", "", "()V", "namesAndValues", "", "", "getNamesAndValues$okhttp", "()Ljava/util/List;", "add", "line", "name", "value", "Ljava/time/Instant;", "Ljava/util/Date;", "addAll", "headers", "Lokhttp3/Headers;", "addLenient", "addLenient$okhttp", "addUnsafeNonAscii", "build", "get", "removeAll", "set", "okhttp"}, k = 1, mv = {1, 1, 15})
    public static final class Builder {
        private final List<String> namesAndValues = new ArrayList(20);

        public final List<String> getNamesAndValues$okhttp() {
            return this.namesAndValues;
        }

        public final Builder addLenient$okhttp(String line) {
            Intrinsics.checkParameterIsNotNull(line, "line");
            Builder $this$apply = this;
            int index = StringsKt.indexOf$default((CharSequence) line, ':', 1, false, 4, (Object) null);
            if (index != -1) {
                String strSubstring = line.substring(0, index);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                String strSubstring2 = line.substring(index + 1);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.String).substring(startIndex)");
                $this$apply.addLenient$okhttp(strSubstring, strSubstring2);
            } else if (line.charAt(0) == ':') {
                String strSubstring3 = line.substring(1);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring3, "(this as java.lang.String).substring(startIndex)");
                $this$apply.addLenient$okhttp("", strSubstring3);
            } else {
                $this$apply.addLenient$okhttp("", line);
            }
            return this;
        }

        public final Builder add(String line) {
            Intrinsics.checkParameterIsNotNull(line, "line");
            Builder $this$apply = this;
            int index = StringsKt.indexOf$default((CharSequence) line, ':', 0, false, 6, (Object) null);
            if (!(index != -1)) {
                throw new IllegalArgumentException(("Unexpected header: " + line).toString());
            }
            String strSubstring = line.substring(0, index);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            if (strSubstring != null) {
                String string = StringsKt.trim((CharSequence) strSubstring).toString();
                String strSubstring2 = line.substring(index + 1);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.String).substring(startIndex)");
                $this$apply.add(string, strSubstring2);
                return this;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }

        public final Builder add(String name, String value) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder $this$apply = this;
            Headers.INSTANCE.checkName(name);
            Headers.INSTANCE.checkValue(value, name);
            $this$apply.addLenient$okhttp(name, value);
            return this;
        }

        public final Builder addUnsafeNonAscii(String name, String value) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder $this$apply = this;
            Headers.INSTANCE.checkName(name);
            $this$apply.addLenient$okhttp(name, value);
            return this;
        }

        public final Builder addAll(Headers headers) {
            Intrinsics.checkParameterIsNotNull(headers, "headers");
            Builder $this$apply = this;
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                $this$apply.addLenient$okhttp(headers.name(i), headers.value(i));
            }
            return this;
        }

        public final Builder add(String name, Date value) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder $this$apply = this;
            $this$apply.add(name, dates.toHttpDateString(value));
            return this;
        }

        public final Builder add(String name, Instant value) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder $this$apply = this;
            $this$apply.add(name, new Date(value.toEpochMilli()));
            return this;
        }

        public final Builder set(String name, Date value) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder $this$apply = this;
            $this$apply.set(name, dates.toHttpDateString(value));
            return this;
        }

        public final Builder set(String name, Instant value) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder $this$apply = this;
            return $this$apply.set(name, new Date(value.toEpochMilli()));
        }

        public final Builder addLenient$okhttp(String name, String value) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder $this$apply = this;
            $this$apply.namesAndValues.add(name);
            $this$apply.namesAndValues.add(StringsKt.trim((CharSequence) value).toString());
            return this;
        }

        public final Builder removeAll(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Builder $this$apply = this;
            int i = 0;
            while (i < $this$apply.namesAndValues.size()) {
                if (StringsKt.equals(name, $this$apply.namesAndValues.get(i), true)) {
                    $this$apply.namesAndValues.remove(i);
                    $this$apply.namesAndValues.remove(i);
                    i -= 2;
                }
                i += 2;
            }
            return this;
        }

        public final Builder set(String name, String value) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder $this$apply = this;
            Headers.INSTANCE.checkName(name);
            Headers.INSTANCE.checkValue(value, name);
            $this$apply.removeAll(name);
            $this$apply.addLenient$okhttp(name, value);
            return this;
        }

        public final String get(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Progressions2 progressions2Step = RangesKt.step(RangesKt.downTo(this.namesAndValues.size() - 2, 0), 2);
            int i = progressions2Step.getFirst();
            int last = progressions2Step.getLast();
            int step = progressions2Step.getStep();
            if (step >= 0) {
                if (i > last) {
                    return null;
                }
            } else if (i < last) {
                return null;
            }
            while (!StringsKt.equals(name, this.namesAndValues.get(i), true)) {
                if (i == last) {
                    return null;
                }
                i += step;
            }
            return this.namesAndValues.get(i + 1);
        }

        public final Headers build() {
            Collection $this$toTypedArray$iv = this.namesAndValues;
            Object[] array = $this$toTypedArray$iv.toArray(new String[0]);
            if (array != null) {
                return new Headers((String[]) array, null);
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    /* compiled from: Headers.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J%\u0010\t\u001a\u0004\u0018\u00010\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¢\u0006\u0002\u0010\fJ#\u0010\r\u001a\u00020\u000e2\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u000b\"\u00020\u0006H\u0007¢\u0006\u0004\b\u000f\u0010\u0010J#\u0010\u000f\u001a\u00020\u000e2\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u000b\"\u00020\u0006H\u0007¢\u0006\u0004\b\u0011\u0010\u0010J!\u0010\u000f\u001a\u00020\u000e2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0013H\u0007¢\u0006\u0002\b\u0011J\u001d\u0010\u0014\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0013H\u0007¢\u0006\u0002\b\u000f¨\u0006\u0015"}, d2 = {"Lokhttp3/Headers$Companion;", "", "()V", "checkName", "", "name", "", "checkValue", "value", "get", "namesAndValues", "", "([Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "headersOf", "Lokhttp3/Headers;", "of", "([Ljava/lang/String;)Lokhttp3/Headers;", "-deprecated_of", "headers", "", "toHeaders", "okhttp"}, k = 1, mv = {1, 1, 15})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String get(String[] namesAndValues, String name) {
            Progressions2 progressions2Step = RangesKt.step(RangesKt.downTo(namesAndValues.length - 2, 0), 2);
            int i = progressions2Step.getFirst();
            int last = progressions2Step.getLast();
            int step = progressions2Step.getStep();
            if (step >= 0) {
                if (i > last) {
                    return null;
                }
            } else if (i < last) {
                return null;
            }
            while (!StringsKt.equals(name, namesAndValues[i], true)) {
                if (i == last) {
                    return null;
                }
                i += step;
            }
            return namesAndValues[i + 1];
        }

        @JvmStatic
        public final Headers of(String... namesAndValues) throws CloneNotSupportedException {
            Intrinsics.checkParameterIsNotNull(namesAndValues, "namesAndValues");
            if (!(namesAndValues.length % 2 == 0)) {
                throw new IllegalArgumentException("Expected alternating header names and values".toString());
            }
            Object objClone = namesAndValues.clone();
            if (objClone == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
            }
            String[] namesAndValues2 = (String[]) objClone;
            int length = namesAndValues2.length;
            for (int i = 0; i < length; i++) {
                if (!(namesAndValues2[i] != null)) {
                    throw new IllegalArgumentException("Headers cannot be null".toString());
                }
                String str = namesAndValues2[i];
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                namesAndValues2[i] = StringsKt.trim((CharSequence) str).toString();
            }
            Progressions2 progressions2Step = RangesKt.step(RangesKt.until(0, namesAndValues2.length), 2);
            int i2 = progressions2Step.getFirst();
            int last = progressions2Step.getLast();
            int step = progressions2Step.getStep();
            if (step < 0 ? i2 >= last : i2 <= last) {
                while (true) {
                    String name = namesAndValues2[i2];
                    String value = namesAndValues2[i2 + 1];
                    checkName(name);
                    checkValue(value, name);
                    if (i2 == last) {
                        break;
                    }
                    i2 += step;
                }
            }
            return new Headers(namesAndValues2, null);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "function name changed", replaceWith = @ReplaceWith(expression = "headersOf(*namesAndValues)", imports = {}))
        /* renamed from: -deprecated_of, reason: not valid java name */
        public final Headers m1033deprecated_of(String... namesAndValues) {
            Intrinsics.checkParameterIsNotNull(namesAndValues, "namesAndValues");
            return of((String[]) Arrays.copyOf(namesAndValues, namesAndValues.length));
        }

        @JvmStatic
        public final Headers of(Map<String, String> toHeaders) {
            Intrinsics.checkParameterIsNotNull(toHeaders, "$this$toHeaders");
            String[] strArr = new String[toHeaders.size() * 2];
            int i = 0;
            for (Map.Entry<String, String> entry : toHeaders.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                String string = StringsKt.trim((CharSequence) key).toString();
                if (value == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                String string2 = StringsKt.trim((CharSequence) value).toString();
                checkName(string);
                checkValue(string2, string);
                strArr[i] = string;
                strArr[i + 1] = string2;
                i += 2;
            }
            return new Headers(strArr, null);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "function moved to extension", replaceWith = @ReplaceWith(expression = "headers.toHeaders()", imports = {}))
        /* renamed from: -deprecated_of, reason: not valid java name */
        public final Headers m1032deprecated_of(Map<String, String> headers) {
            Intrinsics.checkParameterIsNotNull(headers, "headers");
            return of(headers);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void checkName(String name) {
            if (!(name.length() > 0)) {
                throw new IllegalArgumentException("name is empty".toString());
            }
            int length = name.length();
            for (int i = 0; i < length; i++) {
                char c = name.charAt(i);
                if (!('!' <= c && '~' >= c)) {
                    throw new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in header name: %s", Integer.valueOf(c), Integer.valueOf(i), name).toString());
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void checkValue(String value, String name) {
            int length = value.length();
            for (int i = 0; i < length; i++) {
                char c = value.charAt(i);
                if (!(c == '\t' || (' ' <= c && '~' >= c))) {
                    throw new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in %s value: %s", Integer.valueOf(c), Integer.valueOf(i), name, value).toString());
                }
            }
        }
    }
}
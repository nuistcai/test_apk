package kotlin.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Tuples;
import kotlin.Tuples3;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.IndexedValue;
import kotlin.collections.IndexingIterable;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.collections.SlidingWindow3;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.Progressions2;
import kotlin.ranges.Ranges5;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* compiled from: _Strings.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000Ü\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u001f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a!\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0010\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b*\u00020\u0002\u001a\u0010\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n*\u00020\u0002\u001aE\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b\u001a3\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00050\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aM\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aN\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u00020\u00050\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001ah\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b¢\u0006\u0002\u0010\u0019\u001a`\u0010\u001a\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001a3\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b\u001aN\u0010\u001d\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\u000e\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u00020\u0005\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b¢\u0006\u0002\u0010\u0018\u001a\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u001a\u0010$\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010$\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\r\u0010%\u001a\u00020\"*\u00020\u0002H\u0087\b\u001a!\u0010%\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010&\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a!\u0010)\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010)\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a)\u0010+\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u001c\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"H\u0087\b¢\u0006\u0002\u0010/\u001a!\u00100\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u00100\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a6\u00101\u001a\u00020\u0002*\u00020\u00022'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001a6\u00101\u001a\u00020 *\u00020 2'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001aQ\u00105\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b¢\u0006\u0002\u00109\u001a!\u0010:\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010:\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a<\u0010;\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010<\u001a<\u0010=\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010<\u001a(\u0010>\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b¢\u0006\u0002\u0010?\u001a(\u0010@\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b¢\u0006\u0002\u0010?\u001a\n\u0010A\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010A\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a(\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a3\u0010D\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b\u001aL\u0010E\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001aI\u0010H\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010L\u001a^\u0010M\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0NH\u0086\b¢\u0006\u0002\u0010O\u001aI\u0010P\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010L\u001a^\u0010Q\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#0NH\u0086\b¢\u0006\u0002\u0010O\u001a!\u0010R\u001a\u00020S*\u00020\u00022\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0086\b\u001a6\u0010U\u001a\u00020S*\u00020\u00022'\u0010T\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S02H\u0086\b\u001a)\u0010V\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u0019\u0010W\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"¢\u0006\u0002\u0010/\u001a9\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u001f0\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aS\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u001f0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aR\u0010Y\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u001c\b\u0001\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001al\u0010Y\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u001c\b\u0002\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b¢\u0006\u0002\u0010\u0019\u001a5\u0010[\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\\\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0014\b\u0004\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0087\b\u001a!\u0010]\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010^\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010_\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010_\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a(\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010a\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u001aB\u0010b\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b\u001aH\u0010c\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\u0000\u0010#*\u00020d*\u00020\u00022)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b\u001aa\u0010e\u001a\u0002H6\"\b\b\u0000\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b¢\u0006\u0002\u0010f\u001a[\u0010g\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010f\u001a3\u0010h\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\u0000\u0010#*\u00020d*\u00020\u00022\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b\u001aL\u0010i\u001a\u0002H6\"\b\b\u0000\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001aF\u0010j\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001a\u0011\u0010k\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a8\u0010l\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010o\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050qj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`r¢\u0006\u0002\u0010s\u001a\u0011\u0010t\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a8\u0010u\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010v\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050qj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`r¢\u0006\u0002\u0010s\u001a\n\u0010w\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010w\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a0\u0010x\u001a\u0002Hy\"\b\b\u0000\u0010y*\u00020\u0002*\u0002Hy2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0087\b¢\u0006\u0002\u0010z\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020 0\u0010*\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\r\u0010|\u001a\u00020\u0005*\u00020\u0002H\u0087\b\u001a\u0014\u0010|\u001a\u00020\u0005*\u00020\u00022\u0006\u0010|\u001a\u00020}H\u0007\u001a6\u0010~\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aK\u0010\u007f\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a7\u0010\u0080\u0001\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aL\u0010\u0081\u0001\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a\u000b\u0010\u0082\u0001\u001a\u00020\u0002*\u00020\u0002\u001a\u000e\u0010\u0082\u0001\u001a\u00020 *\u00020 H\u0087\b\u001a\u000b\u0010\u0083\u0001\u001a\u00020\u0005*\u00020\u0002\u001a\"\u0010\u0083\u0001\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a)\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a\u001a\u0010\u0085\u0001\u001a\u00020\u0002*\u00020\u00022\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\b\u001a\u0015\u0010\u0085\u0001\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001\u001a\u001d\u0010\u0085\u0001\u001a\u00020 *\u00020 2\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\bH\u0087\b\u001a\u0015\u0010\u0085\u0001\u001a\u00020 *\u00020 2\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001\u001a\"\u0010\u0088\u0001\u001a\u00020\"*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\"0\u0004H\u0086\b\u001a$\u0010\u0089\u0001\u001a\u00030\u008a\u0001*\u00020\u00022\u0013\u0010n\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0012\u0005\u0012\u00030\u008a\u00010\u0004H\u0086\b\u001a\u0013\u0010\u008b\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008b\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008c\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008c\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\"\u0010\u008d\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008d\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008e\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008e\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a+\u0010\u008f\u0001\u001a\u0002H6\"\u0010\b\u0000\u00106*\n\u0012\u0006\b\u0000\u0012\u00020\u00050F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H6¢\u0006\u0003\u0010\u0090\u0001\u001a\u001d\u0010\u0091\u0001\u001a\u0014\u0012\u0004\u0012\u00020\u00050\u0092\u0001j\t\u0012\u0004\u0012\u00020\u0005`\u0093\u0001*\u00020\u0002\u001a\u0011\u0010\u0094\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001f*\u00020\u0002\u001a\u0011\u0010\u0095\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050Z*\u00020\u0002\u001a\u0012\u0010\u0096\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050\u0097\u0001*\u00020\u0002\u001a1\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a1\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u0018\u0010\u009c\u0001\u001a\u000f\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020\u00050\u009d\u00010\b*\u00020\u0002\u001a)\u0010\u009e\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u00022\u0007\u0010\u009f\u0001\u001a\u00020\u0002H\u0086\u0004\u001a]\u0010\u009e\u0001\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u001f\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0007\u0010\u009f\u0001\u001a\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b( \u0001\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(¡\u0001\u0012\u0004\u0012\u0002H\u000e02H\u0086\b\u001a\u001f\u0010¢\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u0002H\u0007\u001aT\u0010¢\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b( \u0001\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(¡\u0001\u0012\u0004\u0012\u0002H#02H\u0087\b¨\u0006£\u0001"}, d2 = {"all", "", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "associate", "", "K", "V", "transform", "Lkotlin/Pair;", "associateBy", "keySelector", "valueTransform", "associateByTo", "M", "", "destination", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "associateTo", "associateWith", "valueSelector", "associateWithTo", "chunked", "", "", "size", "", "R", "chunkedSequence", "count", "drop", "n", "dropLast", "dropLastWhile", "dropWhile", "elementAtOrElse", "index", "defaultValue", "elementAtOrNull", "(Ljava/lang/CharSequence;I)Ljava/lang/Character;", "filter", "filterIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "filterIndexedTo", "C", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function2;)Ljava/lang/Appendable;", "filterNot", "filterNotTo", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Appendable;", "filterTo", "find", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Character;", "findLast", "first", "firstOrNull", "(Ljava/lang/CharSequence;)Ljava/lang/Character;", "flatMap", "flatMapTo", "", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "fold", "initial", "operation", "acc", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "foldIndexed", "Lkotlin/Function3;", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "foldRight", "foldRightIndexed", "forEach", "", "action", "forEachIndexed", "getOrElse", "getOrNull", "groupBy", "groupByTo", "", "groupingBy", "Lkotlin/collections/Grouping;", "indexOfFirst", "indexOfLast", "last", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "", "mapIndexedNotNullTo", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;", "mapIndexedTo", "mapNotNull", "mapNotNullTo", "mapTo", "max", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/CharSequence;Ljava/util/Comparator;)Ljava/lang/Character;", "min", "minBy", "minWith", "none", "onEach", "S", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/CharSequence;", "partition", "random", "Lkotlin/random/Random;", "reduce", "reduceIndexed", "reduceRight", "reduceRightIndexed", "reversed", "single", "singleOrNull", "slice", "indices", "Lkotlin/ranges/IntRange;", "sumBy", "sumByDouble", "", "take", "takeLast", "takeLastWhile", "takeWhile", "toCollection", "(Ljava/lang/CharSequence;Ljava/util/Collection;)Ljava/util/Collection;", "toHashSet", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "toList", "toMutableList", "toSet", "", "windowed", "step", "partialWindows", "windowedSequence", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "zipWithNext", "kotlin-stdlib"}, k = 5, mv = {1, 1, 15}, xi = 1, xs = "kotlin/text/StringsKt")
/* renamed from: kotlin.text.StringsKt___StringsKt, reason: use source file name */
/* loaded from: classes.dex */
class _Strings extends _StringsJvm {
    private static final char elementAtOrElse(CharSequence $this$elementAtOrElse, int index, Function1<? super Integer, Character> function1) {
        return (index < 0 || index > StringsKt.getLastIndex($this$elementAtOrElse)) ? function1.invoke(Integer.valueOf(index)).charValue() : $this$elementAtOrElse.charAt(index);
    }

    private static final Character elementAtOrNull(CharSequence $this$elementAtOrNull, int index) {
        return StringsKt.getOrNull($this$elementAtOrNull, index);
    }

    private static final Character find(CharSequence $this$find, Function1<? super Character, Boolean> function1) {
        for (int i = 0; i < $this$find.length(); i++) {
            char element$iv = $this$find.charAt(i);
            if (function1.invoke(Character.valueOf(element$iv)).booleanValue()) {
                return Character.valueOf(element$iv);
            }
        }
        return null;
    }

    private static final Character findLast(CharSequence $this$findLast, Function1<? super Character, Boolean> function1) {
        char element$iv;
        int index$iv = $this$findLast.length();
        do {
            index$iv--;
            if (index$iv >= 0) {
                element$iv = $this$findLast.charAt(index$iv);
            } else {
                return null;
            }
        } while (!function1.invoke(Character.valueOf(element$iv)).booleanValue());
        return Character.valueOf(element$iv);
    }

    public static final char first(CharSequence first) {
        Intrinsics.checkParameterIsNotNull(first, "$this$first");
        if (first.length() == 0) {
            throw new NoSuchElementException("Char sequence is empty.");
        }
        return first.charAt(0);
    }

    public static final char first(CharSequence first, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(first, "$this$first");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < first.length(); i++) {
            char element = first.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                return element;
            }
        }
        throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
    }

    public static final Character firstOrNull(CharSequence firstOrNull) {
        Intrinsics.checkParameterIsNotNull(firstOrNull, "$this$firstOrNull");
        if (firstOrNull.length() == 0) {
            return null;
        }
        return Character.valueOf(firstOrNull.charAt(0));
    }

    public static final Character firstOrNull(CharSequence firstOrNull, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(firstOrNull, "$this$firstOrNull");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < firstOrNull.length(); i++) {
            char element = firstOrNull.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                return Character.valueOf(element);
            }
        }
        return null;
    }

    private static final char getOrElse(CharSequence $this$getOrElse, int index, Function1<? super Integer, Character> function1) {
        return (index < 0 || index > StringsKt.getLastIndex($this$getOrElse)) ? function1.invoke(Integer.valueOf(index)).charValue() : $this$getOrElse.charAt(index);
    }

    public static final Character getOrNull(CharSequence getOrNull, int index) {
        Intrinsics.checkParameterIsNotNull(getOrNull, "$this$getOrNull");
        if (index < 0 || index > StringsKt.getLastIndex(getOrNull)) {
            return null;
        }
        return Character.valueOf(getOrNull.charAt(index));
    }

    public static final int indexOfFirst(CharSequence indexOfFirst, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(indexOfFirst, "$this$indexOfFirst");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = indexOfFirst.length();
        for (int index = 0; index < length; index++) {
            if (predicate.invoke(Character.valueOf(indexOfFirst.charAt(index))).booleanValue()) {
                return index;
            }
        }
        return -1;
    }

    public static final int indexOfLast(CharSequence indexOfLast, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(indexOfLast, "$this$indexOfLast");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int index = indexOfLast.length() - 1; index >= 0; index--) {
            if (predicate.invoke(Character.valueOf(indexOfLast.charAt(index))).booleanValue()) {
                return index;
            }
        }
        return -1;
    }

    public static final char last(CharSequence last) {
        Intrinsics.checkParameterIsNotNull(last, "$this$last");
        if (last.length() == 0) {
            throw new NoSuchElementException("Char sequence is empty.");
        }
        return last.charAt(StringsKt.getLastIndex(last));
    }

    public static final char last(CharSequence last, Function1<? super Character, Boolean> predicate) {
        char element;
        Intrinsics.checkParameterIsNotNull(last, "$this$last");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int index = last.length();
        do {
            index--;
            if (index >= 0) {
                element = last.charAt(index);
            } else {
                throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
            }
        } while (!predicate.invoke(Character.valueOf(element)).booleanValue());
        return element;
    }

    public static final Character lastOrNull(CharSequence lastOrNull) {
        Intrinsics.checkParameterIsNotNull(lastOrNull, "$this$lastOrNull");
        if (lastOrNull.length() == 0) {
            return null;
        }
        return Character.valueOf(lastOrNull.charAt(lastOrNull.length() - 1));
    }

    public static final Character lastOrNull(CharSequence lastOrNull, Function1<? super Character, Boolean> predicate) {
        char element;
        Intrinsics.checkParameterIsNotNull(lastOrNull, "$this$lastOrNull");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int index = lastOrNull.length();
        do {
            index--;
            if (index >= 0) {
                element = lastOrNull.charAt(index);
            } else {
                return null;
            }
        } while (!predicate.invoke(Character.valueOf(element)).booleanValue());
        return Character.valueOf(element);
    }

    private static final char random(CharSequence $this$random) {
        return StringsKt.random($this$random, Random.INSTANCE);
    }

    public static final char random(CharSequence random, Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (random.length() == 0) {
            throw new NoSuchElementException("Char sequence is empty.");
        }
        return random.charAt(random2.nextInt(random.length()));
    }

    public static final char single(CharSequence single) {
        Intrinsics.checkParameterIsNotNull(single, "$this$single");
        switch (single.length()) {
            case 0:
                throw new NoSuchElementException("Char sequence is empty.");
            case 1:
                return single.charAt(0);
            default:
                throw new IllegalArgumentException("Char sequence has more than one element.");
        }
    }

    public static final char single(CharSequence single, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(single, "$this$single");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Character single2 = (Character) null;
        boolean found = false;
        for (int i = 0; i < single.length(); i++) {
            char element = single.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                if (found) {
                    throw new IllegalArgumentException("Char sequence contains more than one matching element.");
                }
                single2 = Character.valueOf(element);
                found = true;
            }
        }
        if (!found) {
            throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
        }
        if (single2 != null) {
            return single2.charValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Char");
    }

    public static final Character singleOrNull(CharSequence singleOrNull) {
        Intrinsics.checkParameterIsNotNull(singleOrNull, "$this$singleOrNull");
        if (singleOrNull.length() == 1) {
            return Character.valueOf(singleOrNull.charAt(0));
        }
        return null;
    }

    public static final Character singleOrNull(CharSequence singleOrNull, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(singleOrNull, "$this$singleOrNull");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Character single = (Character) null;
        boolean found = false;
        for (int i = 0; i < singleOrNull.length(); i++) {
            char element = singleOrNull.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                if (found) {
                    return null;
                }
                single = Character.valueOf(element);
                found = true;
            }
        }
        if (!found) {
            return null;
        }
        return single;
    }

    public static final CharSequence drop(CharSequence drop, int n) {
        Intrinsics.checkParameterIsNotNull(drop, "$this$drop");
        if (n >= 0) {
            return drop.subSequence(RangesKt.coerceAtMost(n, drop.length()), drop.length());
        }
        throw new IllegalArgumentException(("Requested character count " + n + " is less than zero.").toString());
    }

    public static final String drop(String drop, int n) {
        Intrinsics.checkParameterIsNotNull(drop, "$this$drop");
        if (!(n >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + n + " is less than zero.").toString());
        }
        String strSubstring = drop.substring(RangesKt.coerceAtMost(n, drop.length()));
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
        return strSubstring;
    }

    public static final CharSequence dropLast(CharSequence dropLast, int n) {
        Intrinsics.checkParameterIsNotNull(dropLast, "$this$dropLast");
        if (n >= 0) {
            return StringsKt.take(dropLast, RangesKt.coerceAtLeast(dropLast.length() - n, 0));
        }
        throw new IllegalArgumentException(("Requested character count " + n + " is less than zero.").toString());
    }

    public static final String dropLast(String dropLast, int n) {
        Intrinsics.checkParameterIsNotNull(dropLast, "$this$dropLast");
        if (n >= 0) {
            return StringsKt.take(dropLast, RangesKt.coerceAtLeast(dropLast.length() - n, 0));
        }
        throw new IllegalArgumentException(("Requested character count " + n + " is less than zero.").toString());
    }

    public static final CharSequence dropLastWhile(CharSequence dropLastWhile, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(dropLastWhile, "$this$dropLastWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int index = StringsKt.getLastIndex(dropLastWhile); index >= 0; index--) {
            if (!predicate.invoke(Character.valueOf(dropLastWhile.charAt(index))).booleanValue()) {
                return dropLastWhile.subSequence(0, index + 1);
            }
        }
        return "";
    }

    public static final String dropLastWhile(String dropLastWhile, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(dropLastWhile, "$this$dropLastWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int index = StringsKt.getLastIndex(dropLastWhile); index >= 0; index--) {
            if (!predicate.invoke(Character.valueOf(dropLastWhile.charAt(index))).booleanValue()) {
                String strSubstring = dropLastWhile.substring(0, index + 1);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                return strSubstring;
            }
        }
        return "";
    }

    public static final CharSequence dropWhile(CharSequence dropWhile, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(dropWhile, "$this$dropWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = dropWhile.length();
        for (int index = 0; index < length; index++) {
            if (!predicate.invoke(Character.valueOf(dropWhile.charAt(index))).booleanValue()) {
                return dropWhile.subSequence(index, dropWhile.length());
            }
        }
        return "";
    }

    public static final String dropWhile(String dropWhile, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(dropWhile, "$this$dropWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = dropWhile.length();
        for (int index = 0; index < length; index++) {
            if (!predicate.invoke(Character.valueOf(dropWhile.charAt(index))).booleanValue()) {
                String strSubstring = dropWhile.substring(index);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
                return strSubstring;
            }
        }
        return "";
    }

    public static final CharSequence filter(CharSequence filter, Function1<? super Character, Boolean> predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filter, "$this$filter");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Appendable destination$iv = new StringBuilder();
        int length = filter.length();
        for (int index$iv = 0; index$iv < length; index$iv++) {
            char element$iv = filter.charAt(index$iv);
            if (predicate.invoke(Character.valueOf(element$iv)).booleanValue()) {
                destination$iv.append(element$iv);
            }
        }
        return (CharSequence) destination$iv;
    }

    public static final String filter(String filter, Function1<? super Character, Boolean> predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filter, "$this$filter");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        String $this$filterTo$iv = filter;
        Appendable destination$iv = new StringBuilder();
        int length = $this$filterTo$iv.length();
        for (int index$iv = 0; index$iv < length; index$iv++) {
            char element$iv = $this$filterTo$iv.charAt(index$iv);
            if (predicate.invoke(Character.valueOf(element$iv)).booleanValue()) {
                destination$iv.append(element$iv);
            }
        }
        String string = ((StringBuilder) destination$iv).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "filterTo(StringBuilder(), predicate).toString()");
        return string;
    }

    public static final CharSequence filterIndexed(CharSequence filterIndexed, Function2<? super Integer, ? super Character, Boolean> predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterIndexed, "$this$filterIndexed");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Appendable destination$iv = new StringBuilder();
        int index$iv$iv = 0;
        int i = 0;
        while (i < filterIndexed.length()) {
            char item$iv$iv = filterIndexed.charAt(i);
            int index$iv$iv2 = index$iv$iv + 1;
            if (predicate.invoke(Integer.valueOf(index$iv$iv), Character.valueOf(item$iv$iv)).booleanValue()) {
                destination$iv.append(item$iv$iv);
            }
            i++;
            index$iv$iv = index$iv$iv2;
        }
        return (CharSequence) destination$iv;
    }

    public static final String filterIndexed(String filterIndexed, Function2<? super Integer, ? super Character, Boolean> predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterIndexed, "$this$filterIndexed");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        String $this$filterIndexedTo$iv = filterIndexed;
        Appendable destination$iv = new StringBuilder();
        int index$iv$iv = 0;
        int i = 0;
        while (i < $this$filterIndexedTo$iv.length()) {
            char item$iv$iv = $this$filterIndexedTo$iv.charAt(i);
            int index$iv$iv2 = index$iv$iv + 1;
            if (predicate.invoke(Integer.valueOf(index$iv$iv), Character.valueOf(item$iv$iv)).booleanValue()) {
                destination$iv.append(item$iv$iv);
            }
            i++;
            index$iv$iv = index$iv$iv2;
        }
        String string = ((StringBuilder) destination$iv).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "filterIndexedTo(StringBu…(), predicate).toString()");
        return string;
    }

    public static final <C extends Appendable> C filterIndexedTo(CharSequence filterIndexedTo, C destination, Function2<? super Integer, ? super Character, Boolean> predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterIndexedTo, "$this$filterIndexedTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int index$iv = 0;
        int i = 0;
        while (i < filterIndexedTo.length()) {
            char item$iv = filterIndexedTo.charAt(i);
            int index$iv2 = index$iv + 1;
            if (predicate.invoke(Integer.valueOf(index$iv), Character.valueOf(item$iv)).booleanValue()) {
                destination.append(item$iv);
            }
            i++;
            index$iv = index$iv2;
        }
        return destination;
    }

    public static final CharSequence filterNot(CharSequence filterNot, Function1<? super Character, Boolean> predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterNot, "$this$filterNot");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Appendable destination$iv = new StringBuilder();
        for (int i = 0; i < filterNot.length(); i++) {
            char element$iv = filterNot.charAt(i);
            if (!predicate.invoke(Character.valueOf(element$iv)).booleanValue()) {
                destination$iv.append(element$iv);
            }
        }
        return (CharSequence) destination$iv;
    }

    public static final String filterNot(String filterNot, Function1<? super Character, Boolean> predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterNot, "$this$filterNot");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        String $this$filterNotTo$iv = filterNot;
        Appendable destination$iv = new StringBuilder();
        for (int i = 0; i < $this$filterNotTo$iv.length(); i++) {
            char element$iv = $this$filterNotTo$iv.charAt(i);
            if (!predicate.invoke(Character.valueOf(element$iv)).booleanValue()) {
                destination$iv.append(element$iv);
            }
        }
        String string = ((StringBuilder) destination$iv).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "filterNotTo(StringBuilder(), predicate).toString()");
        return string;
    }

    public static final <C extends Appendable> C filterNotTo(CharSequence filterNotTo, C destination, Function1<? super Character, Boolean> predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterNotTo, "$this$filterNotTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < filterNotTo.length(); i++) {
            char element = filterNotTo.charAt(i);
            if (!predicate.invoke(Character.valueOf(element)).booleanValue()) {
                destination.append(element);
            }
        }
        return destination;
    }

    public static final <C extends Appendable> C filterTo(CharSequence filterTo, C destination, Function1<? super Character, Boolean> predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterTo, "$this$filterTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = filterTo.length();
        for (int index = 0; index < length; index++) {
            char element = filterTo.charAt(index);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                destination.append(element);
            }
        }
        return destination;
    }

    public static final CharSequence slice(CharSequence slice, Ranges5 indices) {
        Intrinsics.checkParameterIsNotNull(slice, "$this$slice");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        return indices.isEmpty() ? "" : StringsKt.subSequence(slice, indices);
    }

    public static final String slice(String slice, Ranges5 indices) {
        Intrinsics.checkParameterIsNotNull(slice, "$this$slice");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        return indices.isEmpty() ? "" : StringsKt.substring(slice, indices);
    }

    public static final CharSequence slice(CharSequence slice, Iterable<Integer> indices) {
        Intrinsics.checkParameterIsNotNull(slice, "$this$slice");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        int size = CollectionsKt.collectionSizeOrDefault(indices, 10);
        if (size == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder(size);
        Iterator<Integer> it = indices.iterator();
        while (it.hasNext()) {
            int i = it.next().intValue();
            result.append(slice.charAt(i));
        }
        return result;
    }

    private static final String slice(String $this$slice, Iterable<Integer> iterable) {
        if ($this$slice != null) {
            return StringsKt.slice((CharSequence) $this$slice, iterable).toString();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
    }

    public static final CharSequence take(CharSequence take, int n) {
        Intrinsics.checkParameterIsNotNull(take, "$this$take");
        if (n >= 0) {
            return take.subSequence(0, RangesKt.coerceAtMost(n, take.length()));
        }
        throw new IllegalArgumentException(("Requested character count " + n + " is less than zero.").toString());
    }

    public static final String take(String take, int n) {
        Intrinsics.checkParameterIsNotNull(take, "$this$take");
        if (!(n >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + n + " is less than zero.").toString());
        }
        String strSubstring = take.substring(0, RangesKt.coerceAtMost(n, take.length()));
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return strSubstring;
    }

    public static final CharSequence takeLast(CharSequence takeLast, int n) {
        Intrinsics.checkParameterIsNotNull(takeLast, "$this$takeLast");
        if (!(n >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + n + " is less than zero.").toString());
        }
        int length = takeLast.length();
        return takeLast.subSequence(length - RangesKt.coerceAtMost(n, length), length);
    }

    public static final String takeLast(String takeLast, int n) {
        Intrinsics.checkParameterIsNotNull(takeLast, "$this$takeLast");
        if (!(n >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + n + " is less than zero.").toString());
        }
        int length = takeLast.length();
        String strSubstring = takeLast.substring(length - RangesKt.coerceAtMost(n, length));
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
        return strSubstring;
    }

    public static final CharSequence takeLastWhile(CharSequence takeLastWhile, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(takeLastWhile, "$this$takeLastWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int index = StringsKt.getLastIndex(takeLastWhile); index >= 0; index--) {
            if (!predicate.invoke(Character.valueOf(takeLastWhile.charAt(index))).booleanValue()) {
                return takeLastWhile.subSequence(index + 1, takeLastWhile.length());
            }
        }
        return takeLastWhile.subSequence(0, takeLastWhile.length());
    }

    public static final String takeLastWhile(String takeLastWhile, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(takeLastWhile, "$this$takeLastWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int index = StringsKt.getLastIndex(takeLastWhile); index >= 0; index--) {
            if (!predicate.invoke(Character.valueOf(takeLastWhile.charAt(index))).booleanValue()) {
                String strSubstring = takeLastWhile.substring(index + 1);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
                return strSubstring;
            }
        }
        return takeLastWhile;
    }

    public static final CharSequence takeWhile(CharSequence takeWhile, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(takeWhile, "$this$takeWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = takeWhile.length();
        for (int index = 0; index < length; index++) {
            if (!predicate.invoke(Character.valueOf(takeWhile.charAt(index))).booleanValue()) {
                return takeWhile.subSequence(0, index);
            }
        }
        return takeWhile.subSequence(0, takeWhile.length());
    }

    public static final String takeWhile(String takeWhile, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(takeWhile, "$this$takeWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = takeWhile.length();
        for (int index = 0; index < length; index++) {
            if (!predicate.invoke(Character.valueOf(takeWhile.charAt(index))).booleanValue()) {
                String strSubstring = takeWhile.substring(0, index);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                return strSubstring;
            }
        }
        return takeWhile;
    }

    public static final CharSequence reversed(CharSequence reversed) {
        Intrinsics.checkParameterIsNotNull(reversed, "$this$reversed");
        StringBuilder sbReverse = new StringBuilder(reversed).reverse();
        Intrinsics.checkExpressionValueIsNotNull(sbReverse, "StringBuilder(this).reverse()");
        return sbReverse;
    }

    private static final String reversed(String $this$reversed) {
        if ($this$reversed != null) {
            return StringsKt.reversed((CharSequence) $this$reversed).toString();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
    }

    public static final <K, V> Map<K, V> associate(CharSequence associate, Function1<? super Character, ? extends Tuples<? extends K, ? extends V>> transform) {
        Intrinsics.checkParameterIsNotNull(associate, "$this$associate");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int capacity = RangesKt.coerceAtLeast(MapsKt.mapCapacity(associate.length()), 16);
        Map destination$iv = new LinkedHashMap(capacity);
        for (int i = 0; i < associate.length(); i++) {
            char element$iv = associate.charAt(i);
            Tuples<? extends K, ? extends V> tuplesInvoke = transform.invoke(Character.valueOf(element$iv));
            destination$iv.put(tuplesInvoke.getFirst(), tuplesInvoke.getSecond());
        }
        return destination$iv;
    }

    public static final <K> Map<K, Character> associateBy(CharSequence associateBy, Function1<? super Character, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull(associateBy, "$this$associateBy");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        int capacity = RangesKt.coerceAtLeast(MapsKt.mapCapacity(associateBy.length()), 16);
        Map destination$iv = new LinkedHashMap(capacity);
        for (int i = 0; i < associateBy.length(); i++) {
            char element$iv = associateBy.charAt(i);
            destination$iv.put(keySelector.invoke(Character.valueOf(element$iv)), Character.valueOf(element$iv));
        }
        return destination$iv;
    }

    public static final <K, V> Map<K, V> associateBy(CharSequence associateBy, Function1<? super Character, ? extends K> keySelector, Function1<? super Character, ? extends V> valueTransform) {
        Intrinsics.checkParameterIsNotNull(associateBy, "$this$associateBy");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        int capacity = RangesKt.coerceAtLeast(MapsKt.mapCapacity(associateBy.length()), 16);
        Map destination$iv = new LinkedHashMap(capacity);
        for (int i = 0; i < associateBy.length(); i++) {
            char element$iv = associateBy.charAt(i);
            destination$iv.put(keySelector.invoke(Character.valueOf(element$iv)), valueTransform.invoke(Character.valueOf(element$iv)));
        }
        return destination$iv;
    }

    public static final <K, M extends Map<? super K, ? super Character>> M associateByTo(CharSequence associateByTo, M destination, Function1<? super Character, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull(associateByTo, "$this$associateByTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        for (int i = 0; i < associateByTo.length(); i++) {
            char element = associateByTo.charAt(i);
            destination.put(keySelector.invoke(Character.valueOf(element)), Character.valueOf(element));
        }
        return destination;
    }

    public static final <K, V, M extends Map<? super K, ? super V>> M associateByTo(CharSequence associateByTo, M destination, Function1<? super Character, ? extends K> keySelector, Function1<? super Character, ? extends V> valueTransform) {
        Intrinsics.checkParameterIsNotNull(associateByTo, "$this$associateByTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        for (int i = 0; i < associateByTo.length(); i++) {
            char element = associateByTo.charAt(i);
            destination.put(keySelector.invoke(Character.valueOf(element)), valueTransform.invoke(Character.valueOf(element)));
        }
        return destination;
    }

    public static final <K, V, M extends Map<? super K, ? super V>> M associateTo(CharSequence associateTo, M destination, Function1<? super Character, ? extends Tuples<? extends K, ? extends V>> transform) {
        Intrinsics.checkParameterIsNotNull(associateTo, "$this$associateTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (int i = 0; i < associateTo.length(); i++) {
            char element = associateTo.charAt(i);
            Tuples<? extends K, ? extends V> tuplesInvoke = transform.invoke(Character.valueOf(element));
            destination.put(tuplesInvoke.getFirst(), tuplesInvoke.getSecond());
        }
        return destination;
    }

    public static final <V> Map<Character, V> associateWith(CharSequence associateWith, Function1<? super Character, ? extends V> valueSelector) {
        Intrinsics.checkParameterIsNotNull(associateWith, "$this$associateWith");
        Intrinsics.checkParameterIsNotNull(valueSelector, "valueSelector");
        LinkedHashMap result = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(associateWith.length()), 16));
        for (int i = 0; i < associateWith.length(); i++) {
            char element$iv = associateWith.charAt(i);
            result.put(Character.valueOf(element$iv), valueSelector.invoke(Character.valueOf(element$iv)));
        }
        return result;
    }

    public static final <V, M extends Map<? super Character, ? super V>> M associateWithTo(CharSequence associateWithTo, M destination, Function1<? super Character, ? extends V> valueSelector) {
        Intrinsics.checkParameterIsNotNull(associateWithTo, "$this$associateWithTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(valueSelector, "valueSelector");
        for (int i = 0; i < associateWithTo.length(); i++) {
            char element = associateWithTo.charAt(i);
            destination.put(Character.valueOf(element), valueSelector.invoke(Character.valueOf(element)));
        }
        return destination;
    }

    public static final <C extends Collection<? super Character>> C toCollection(CharSequence toCollection, C destination) {
        Intrinsics.checkParameterIsNotNull(toCollection, "$this$toCollection");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        for (int i = 0; i < toCollection.length(); i++) {
            char item = toCollection.charAt(i);
            destination.add(Character.valueOf(item));
        }
        return destination;
    }

    public static final HashSet<Character> toHashSet(CharSequence toHashSet) {
        Intrinsics.checkParameterIsNotNull(toHashSet, "$this$toHashSet");
        return (HashSet) StringsKt.toCollection(toHashSet, new HashSet(MapsKt.mapCapacity(toHashSet.length())));
    }

    public static final List<Character> toList(CharSequence toList) {
        Intrinsics.checkParameterIsNotNull(toList, "$this$toList");
        switch (toList.length()) {
            case 0:
                return CollectionsKt.emptyList();
            case 1:
                return CollectionsKt.listOf(Character.valueOf(toList.charAt(0)));
            default:
                return StringsKt.toMutableList(toList);
        }
    }

    public static final List<Character> toMutableList(CharSequence toMutableList) {
        Intrinsics.checkParameterIsNotNull(toMutableList, "$this$toMutableList");
        return (List) StringsKt.toCollection(toMutableList, new ArrayList(toMutableList.length()));
    }

    public static final Set<Character> toSet(CharSequence toSet) {
        Intrinsics.checkParameterIsNotNull(toSet, "$this$toSet");
        switch (toSet.length()) {
            case 0:
                return SetsKt.emptySet();
            case 1:
                return SetsKt.setOf(Character.valueOf(toSet.charAt(0)));
            default:
                return (Set) StringsKt.toCollection(toSet, new LinkedHashSet(MapsKt.mapCapacity(toSet.length())));
        }
    }

    public static final <R> List<R> flatMap(CharSequence flatMap, Function1<? super Character, ? extends Iterable<? extends R>> transform) {
        Intrinsics.checkParameterIsNotNull(flatMap, "$this$flatMap");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Collection destination$iv = new ArrayList();
        for (int i = 0; i < flatMap.length(); i++) {
            char element$iv = flatMap.charAt(i);
            Iterable list$iv = transform.invoke(Character.valueOf(element$iv));
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        return (List) destination$iv;
    }

    public static final <R, C extends Collection<? super R>> C flatMapTo(CharSequence flatMapTo, C destination, Function1<? super Character, ? extends Iterable<? extends R>> transform) {
        Intrinsics.checkParameterIsNotNull(flatMapTo, "$this$flatMapTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (int i = 0; i < flatMapTo.length(); i++) {
            char element = flatMapTo.charAt(i);
            Iterable list = transform.invoke(Character.valueOf(element));
            CollectionsKt.addAll(destination, list);
        }
        return destination;
    }

    public static final <K> Map<K, List<Character>> groupBy(CharSequence groupBy, Function1<? super Character, ? extends K> keySelector) {
        Object answer$iv$iv;
        Intrinsics.checkParameterIsNotNull(groupBy, "$this$groupBy");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Map destination$iv = new LinkedHashMap();
        for (int i = 0; i < groupBy.length(); i++) {
            char element$iv = groupBy.charAt(i);
            K kInvoke = keySelector.invoke(Character.valueOf(element$iv));
            Object value$iv$iv = destination$iv.get(kInvoke);
            if (value$iv$iv == null) {
                answer$iv$iv = new ArrayList();
                destination$iv.put(kInvoke, answer$iv$iv);
            } else {
                answer$iv$iv = value$iv$iv;
            }
            List list$iv = (List) answer$iv$iv;
            list$iv.add(Character.valueOf(element$iv));
        }
        return destination$iv;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.lang.Object] */
    public static final <K, V> Map<K, List<V>> groupBy(CharSequence groupBy, Function1<? super Character, ? extends K> keySelector, Function1<? super Character, ? extends V> valueTransform) {
        V arrayList;
        Intrinsics.checkParameterIsNotNull(groupBy, "$this$groupBy");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        Map destination$iv = new LinkedHashMap();
        for (int i = 0; i < groupBy.length(); i++) {
            char element$iv = groupBy.charAt(i);
            K kInvoke = keySelector.invoke(Character.valueOf(element$iv));
            ?? r9 = destination$iv.get(kInvoke);
            if (r9 == 0) {
                arrayList = new ArrayList();
                destination$iv.put(kInvoke, arrayList);
            } else {
                arrayList = r9;
            }
            List list$iv = (List) arrayList;
            list$iv.add(valueTransform.invoke(Character.valueOf(element$iv)));
        }
        return destination$iv;
    }

    public static final <K, M extends Map<? super K, List<Character>>> M groupByTo(CharSequence groupByTo, M destination, Function1<? super Character, ? extends K> keySelector) {
        Object answer$iv;
        Intrinsics.checkParameterIsNotNull(groupByTo, "$this$groupByTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        for (int i = 0; i < groupByTo.length(); i++) {
            char element = groupByTo.charAt(i);
            K kInvoke = keySelector.invoke(Character.valueOf(element));
            Object value$iv = destination.get(kInvoke);
            if (value$iv == null) {
                answer$iv = new ArrayList();
                destination.put(kInvoke, answer$iv);
            } else {
                answer$iv = value$iv;
            }
            List list = (List) answer$iv;
            list.add(Character.valueOf(element));
        }
        return destination;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.util.ArrayList] */
    public static final <K, V, M extends Map<? super K, List<V>>> M groupByTo(CharSequence groupByTo, M destination, Function1<? super Character, ? extends K> keySelector, Function1<? super Character, ? extends V> valueTransform) {
        V arrayList;
        Intrinsics.checkParameterIsNotNull(groupByTo, "$this$groupByTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        for (int i = 0; i < groupByTo.length(); i++) {
            char element = groupByTo.charAt(i);
            K kInvoke = keySelector.invoke(Character.valueOf(element));
            ?? r6 = destination.get(kInvoke);
            if (r6 == 0) {
                arrayList = new ArrayList();
                destination.put(kInvoke, arrayList);
            } else {
                arrayList = r6;
            }
            List list = (List) arrayList;
            list.add(valueTransform.invoke(Character.valueOf(element)));
        }
        return destination;
    }

    public static final <K> Grouping<Character, K> groupingBy(final CharSequence groupingBy, final Function1<? super Character, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull(groupingBy, "$this$groupingBy");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        return new Grouping<Character, K>() { // from class: kotlin.text.StringsKt___StringsKt.groupingBy.1
            @Override // kotlin.collections.Grouping
            public /* bridge */ /* synthetic */ Object keyOf(Character ch) {
                return keyOf(ch.charValue());
            }

            @Override // kotlin.collections.Grouping
            public Iterator<Character> sourceIterator() {
                return StringsKt.iterator(groupingBy);
            }

            public K keyOf(char element) {
                return (K) keySelector.invoke(Character.valueOf(element));
            }
        };
    }

    public static final <R> List<R> map(CharSequence map, Function1<? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(map, "$this$map");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Collection destination$iv = new ArrayList(map.length());
        for (int i = 0; i < map.length(); i++) {
            char item$iv = map.charAt(i);
            destination$iv.add(transform.invoke(Character.valueOf(item$iv)));
        }
        return (List) destination$iv;
    }

    public static final <R> List<R> mapIndexed(CharSequence mapIndexed, Function2<? super Integer, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(mapIndexed, "$this$mapIndexed");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Collection destination$iv = new ArrayList(mapIndexed.length());
        int index$iv = 0;
        for (int i = 0; i < mapIndexed.length(); i++) {
            char item$iv = mapIndexed.charAt(i);
            Integer numValueOf = Integer.valueOf(index$iv);
            index$iv++;
            destination$iv.add(transform.invoke(numValueOf, Character.valueOf(item$iv)));
        }
        return (List) destination$iv;
    }

    public static final <R> List<R> mapIndexedNotNull(CharSequence mapIndexedNotNull, Function2<? super Integer, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(mapIndexedNotNull, "$this$mapIndexedNotNull");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Collection destination$iv = new ArrayList();
        int index$iv$iv = 0;
        int i = 0;
        while (i < mapIndexedNotNull.length()) {
            char item$iv$iv = mapIndexedNotNull.charAt(i);
            int index$iv$iv2 = index$iv$iv + 1;
            R rInvoke = transform.invoke(Integer.valueOf(index$iv$iv), Character.valueOf(item$iv$iv));
            if (rInvoke != null) {
                destination$iv.add(rInvoke);
            }
            i++;
            index$iv$iv = index$iv$iv2;
        }
        return (List) destination$iv;
    }

    public static final <R, C extends Collection<? super R>> C mapIndexedNotNullTo(CharSequence mapIndexedNotNullTo, C destination, Function2<? super Integer, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(mapIndexedNotNullTo, "$this$mapIndexedNotNullTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int index$iv = 0;
        int i = 0;
        while (i < mapIndexedNotNullTo.length()) {
            char item$iv = mapIndexedNotNullTo.charAt(i);
            int index$iv2 = index$iv + 1;
            R rInvoke = transform.invoke(Integer.valueOf(index$iv), Character.valueOf(item$iv));
            if (rInvoke != null) {
                destination.add(rInvoke);
            }
            i++;
            index$iv = index$iv2;
        }
        return destination;
    }

    public static final <R, C extends Collection<? super R>> C mapIndexedTo(CharSequence mapIndexedTo, C destination, Function2<? super Integer, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(mapIndexedTo, "$this$mapIndexedTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int index = 0;
        for (int i = 0; i < mapIndexedTo.length(); i++) {
            char item = mapIndexedTo.charAt(i);
            Integer numValueOf = Integer.valueOf(index);
            index++;
            destination.add(transform.invoke(numValueOf, Character.valueOf(item)));
        }
        return destination;
    }

    public static final <R> List<R> mapNotNull(CharSequence mapNotNull, Function1<? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(mapNotNull, "$this$mapNotNull");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Collection destination$iv = new ArrayList();
        for (int i = 0; i < mapNotNull.length(); i++) {
            char element$iv$iv = mapNotNull.charAt(i);
            R rInvoke = transform.invoke(Character.valueOf(element$iv$iv));
            if (rInvoke != null) {
                destination$iv.add(rInvoke);
            }
        }
        return (List) destination$iv;
    }

    public static final <R, C extends Collection<? super R>> C mapNotNullTo(CharSequence mapNotNullTo, C destination, Function1<? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(mapNotNullTo, "$this$mapNotNullTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (int i = 0; i < mapNotNullTo.length(); i++) {
            char element$iv = mapNotNullTo.charAt(i);
            R rInvoke = transform.invoke(Character.valueOf(element$iv));
            if (rInvoke != null) {
                destination.add(rInvoke);
            }
        }
        return destination;
    }

    public static final <R, C extends Collection<? super R>> C mapTo(CharSequence mapTo, C destination, Function1<? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(mapTo, "$this$mapTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (int i = 0; i < mapTo.length(); i++) {
            char item = mapTo.charAt(i);
            destination.add(transform.invoke(Character.valueOf(item)));
        }
        return destination;
    }

    public static final Iterable<IndexedValue<Character>> withIndex(final CharSequence withIndex) {
        Intrinsics.checkParameterIsNotNull(withIndex, "$this$withIndex");
        return new IndexingIterable(new Function0<CharIterator>() { // from class: kotlin.text.StringsKt___StringsKt.withIndex.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final CharIterator invoke() {
                return StringsKt.iterator(withIndex);
            }
        });
    }

    public static final boolean all(CharSequence all, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(all, "$this$all");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < all.length(); i++) {
            char element = all.charAt(i);
            if (!predicate.invoke(Character.valueOf(element)).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public static final boolean any(CharSequence any) {
        Intrinsics.checkParameterIsNotNull(any, "$this$any");
        return !(any.length() == 0);
    }

    public static final boolean any(CharSequence any, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(any, "$this$any");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < any.length(); i++) {
            char element = any.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    private static final int count(CharSequence $this$count) {
        return $this$count.length();
    }

    public static final int count(CharSequence count, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(count, "$this$count");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int count2 = 0;
        for (int i = 0; i < count.length(); i++) {
            char element = count.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                count2++;
            }
        }
        return count2;
    }

    public static final <R> R fold(CharSequence fold, R r, Function2<? super R, ? super Character, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull(fold, "$this$fold");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        R rInvoke = r;
        for (int i = 0; i < fold.length(); i++) {
            rInvoke = operation.invoke(rInvoke, Character.valueOf(fold.charAt(i)));
        }
        return rInvoke;
    }

    public static final <R> R foldIndexed(CharSequence foldIndexed, R r, Function3<? super Integer, ? super R, ? super Character, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull(foldIndexed, "$this$foldIndexed");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int i = 0;
        R rInvoke = r;
        for (int i2 = 0; i2 < foldIndexed.length(); i2++) {
            char cCharAt = foldIndexed.charAt(i2);
            Integer numValueOf = Integer.valueOf(i);
            i++;
            rInvoke = operation.invoke(numValueOf, rInvoke, Character.valueOf(cCharAt));
        }
        return rInvoke;
    }

    public static final <R> R foldRight(CharSequence foldRight, R r, Function2<? super Character, ? super R, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull(foldRight, "$this$foldRight");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        R rInvoke = r;
        for (int lastIndex = StringsKt.getLastIndex(foldRight); lastIndex >= 0; lastIndex--) {
            rInvoke = operation.invoke(Character.valueOf(foldRight.charAt(lastIndex)), rInvoke);
        }
        return rInvoke;
    }

    public static final <R> R foldRightIndexed(CharSequence foldRightIndexed, R r, Function3<? super Integer, ? super Character, ? super R, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull(foldRightIndexed, "$this$foldRightIndexed");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        R rInvoke = r;
        for (int lastIndex = StringsKt.getLastIndex(foldRightIndexed); lastIndex >= 0; lastIndex--) {
            rInvoke = operation.invoke(Integer.valueOf(lastIndex), Character.valueOf(foldRightIndexed.charAt(lastIndex)), rInvoke);
        }
        return rInvoke;
    }

    public static final void forEach(CharSequence forEach, Function1<? super Character, Unit> action) {
        Intrinsics.checkParameterIsNotNull(forEach, "$this$forEach");
        Intrinsics.checkParameterIsNotNull(action, "action");
        for (int i = 0; i < forEach.length(); i++) {
            char element = forEach.charAt(i);
            action.invoke(Character.valueOf(element));
        }
    }

    public static final void forEachIndexed(CharSequence forEachIndexed, Function2<? super Integer, ? super Character, Unit> action) {
        Intrinsics.checkParameterIsNotNull(forEachIndexed, "$this$forEachIndexed");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int index = 0;
        for (int i = 0; i < forEachIndexed.length(); i++) {
            char item = forEachIndexed.charAt(i);
            Integer numValueOf = Integer.valueOf(index);
            index++;
            action.invoke(numValueOf, Character.valueOf(item));
        }
    }

    public static final Character max(CharSequence max) {
        Intrinsics.checkParameterIsNotNull(max, "$this$max");
        int i = 1;
        if (max.length() == 0) {
            return null;
        }
        char max2 = max.charAt(0);
        int lastIndex = StringsKt.getLastIndex(max);
        if (1 <= lastIndex) {
            while (true) {
                char e = max.charAt(i);
                if (max2 < e) {
                    max2 = e;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(max2);
    }

    public static final <R extends Comparable<? super R>> Character maxBy(CharSequence maxBy, Function1<? super Character, ? extends R> selector) {
        Intrinsics.checkParameterIsNotNull(maxBy, "$this$maxBy");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        int i = 1;
        if (maxBy.length() == 0) {
            return null;
        }
        char maxElem = maxBy.charAt(0);
        int lastIndex = StringsKt.getLastIndex(maxBy);
        if (lastIndex == 0) {
            return Character.valueOf(maxElem);
        }
        Comparable maxValue = selector.invoke(Character.valueOf(maxElem));
        if (1 <= lastIndex) {
            while (true) {
                char e = maxBy.charAt(i);
                R rInvoke = selector.invoke(Character.valueOf(e));
                if (maxValue.compareTo(rInvoke) < 0) {
                    maxElem = e;
                    maxValue = rInvoke;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(maxElem);
    }

    public static final Character maxWith(CharSequence maxWith, Comparator<? super Character> comparator) {
        Intrinsics.checkParameterIsNotNull(maxWith, "$this$maxWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        int i = 1;
        if (maxWith.length() == 0) {
            return null;
        }
        char max = maxWith.charAt(0);
        int lastIndex = StringsKt.getLastIndex(maxWith);
        if (1 <= lastIndex) {
            while (true) {
                char e = maxWith.charAt(i);
                if (comparator.compare(Character.valueOf(max), Character.valueOf(e)) < 0) {
                    max = e;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(max);
    }

    public static final Character min(CharSequence min) {
        Intrinsics.checkParameterIsNotNull(min, "$this$min");
        int i = 1;
        if (min.length() == 0) {
            return null;
        }
        char min2 = min.charAt(0);
        int lastIndex = StringsKt.getLastIndex(min);
        if (1 <= lastIndex) {
            while (true) {
                char e = min.charAt(i);
                if (min2 > e) {
                    min2 = e;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(min2);
    }

    public static final <R extends Comparable<? super R>> Character minBy(CharSequence minBy, Function1<? super Character, ? extends R> selector) {
        Intrinsics.checkParameterIsNotNull(minBy, "$this$minBy");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        int i = 1;
        if (minBy.length() == 0) {
            return null;
        }
        char minElem = minBy.charAt(0);
        int lastIndex = StringsKt.getLastIndex(minBy);
        if (lastIndex == 0) {
            return Character.valueOf(minElem);
        }
        Comparable minValue = selector.invoke(Character.valueOf(minElem));
        if (1 <= lastIndex) {
            while (true) {
                char e = minBy.charAt(i);
                R rInvoke = selector.invoke(Character.valueOf(e));
                if (minValue.compareTo(rInvoke) > 0) {
                    minElem = e;
                    minValue = rInvoke;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(minElem);
    }

    public static final Character minWith(CharSequence minWith, Comparator<? super Character> comparator) {
        Intrinsics.checkParameterIsNotNull(minWith, "$this$minWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        int i = 1;
        if (minWith.length() == 0) {
            return null;
        }
        char min = minWith.charAt(0);
        int lastIndex = StringsKt.getLastIndex(minWith);
        if (1 <= lastIndex) {
            while (true) {
                char e = minWith.charAt(i);
                if (comparator.compare(Character.valueOf(min), Character.valueOf(e)) > 0) {
                    min = e;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(min);
    }

    public static final boolean none(CharSequence none) {
        Intrinsics.checkParameterIsNotNull(none, "$this$none");
        return none.length() == 0;
    }

    public static final boolean none(CharSequence none, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(none, "$this$none");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < none.length(); i++) {
            char element = none.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public static final <S extends CharSequence> S onEach(S onEach, Function1<? super Character, Unit> action) {
        Intrinsics.checkParameterIsNotNull(onEach, "$this$onEach");
        Intrinsics.checkParameterIsNotNull(action, "action");
        for (int i = 0; i < onEach.length(); i++) {
            char element = onEach.charAt(i);
            action.invoke(Character.valueOf(element));
        }
        return onEach;
    }

    public static final char reduce(CharSequence reduce, Function2<? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull(reduce, "$this$reduce");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = 1;
        if (reduce.length() == 0) {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char accumulator = reduce.charAt(0);
        int lastIndex = StringsKt.getLastIndex(reduce);
        if (1 <= lastIndex) {
            while (true) {
                accumulator = operation.invoke(Character.valueOf(accumulator), Character.valueOf(reduce.charAt(index))).charValue();
                if (index == lastIndex) {
                    break;
                }
                index++;
            }
        }
        return accumulator;
    }

    public static final char reduceIndexed(CharSequence reduceIndexed, Function3<? super Integer, ? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull(reduceIndexed, "$this$reduceIndexed");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = 1;
        if (reduceIndexed.length() == 0) {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char accumulator = reduceIndexed.charAt(0);
        int lastIndex = StringsKt.getLastIndex(reduceIndexed);
        if (1 <= lastIndex) {
            while (true) {
                accumulator = operation.invoke(Integer.valueOf(index), Character.valueOf(accumulator), Character.valueOf(reduceIndexed.charAt(index))).charValue();
                if (index == lastIndex) {
                    break;
                }
                index++;
            }
        }
        return accumulator;
    }

    public static final char reduceRight(CharSequence reduceRight, Function2<? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull(reduceRight, "$this$reduceRight");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = StringsKt.getLastIndex(reduceRight);
        if (index < 0) {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char accumulator = reduceRight.charAt(index);
        for (int index2 = index - 1; index2 >= 0; index2--) {
            accumulator = operation.invoke(Character.valueOf(reduceRight.charAt(index2)), Character.valueOf(accumulator)).charValue();
        }
        return accumulator;
    }

    public static final char reduceRightIndexed(CharSequence reduceRightIndexed, Function3<? super Integer, ? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull(reduceRightIndexed, "$this$reduceRightIndexed");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = StringsKt.getLastIndex(reduceRightIndexed);
        if (index < 0) {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char accumulator = reduceRightIndexed.charAt(index);
        for (int index2 = index - 1; index2 >= 0; index2--) {
            accumulator = operation.invoke(Integer.valueOf(index2), Character.valueOf(reduceRightIndexed.charAt(index2)), Character.valueOf(accumulator)).charValue();
        }
        return accumulator;
    }

    public static final int sumBy(CharSequence sumBy, Function1<? super Character, Integer> selector) {
        Intrinsics.checkParameterIsNotNull(sumBy, "$this$sumBy");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        int sum = 0;
        for (int i = 0; i < sumBy.length(); i++) {
            char element = sumBy.charAt(i);
            sum += selector.invoke(Character.valueOf(element)).intValue();
        }
        return sum;
    }

    public static final double sumByDouble(CharSequence sumByDouble, Function1<? super Character, Double> selector) {
        Intrinsics.checkParameterIsNotNull(sumByDouble, "$this$sumByDouble");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        double sum = 0.0d;
        for (int i = 0; i < sumByDouble.length(); i++) {
            char element = sumByDouble.charAt(i);
            sum += selector.invoke(Character.valueOf(element)).doubleValue();
        }
        return sum;
    }

    public static final List<String> chunked(CharSequence chunked, int size) {
        Intrinsics.checkParameterIsNotNull(chunked, "$this$chunked");
        return StringsKt.windowed(chunked, size, size, true);
    }

    public static final <R> List<R> chunked(CharSequence chunked, int size, Function1<? super CharSequence, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(chunked, "$this$chunked");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return StringsKt.windowed(chunked, size, size, true, transform);
    }

    public static final Sequence<String> chunkedSequence(CharSequence chunkedSequence, int size) {
        Intrinsics.checkParameterIsNotNull(chunkedSequence, "$this$chunkedSequence");
        return StringsKt.chunkedSequence(chunkedSequence, size, new Function1<CharSequence, String>() { // from class: kotlin.text.StringsKt___StringsKt.chunkedSequence.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(CharSequence it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.toString();
            }
        });
    }

    public static final <R> Sequence<R> chunkedSequence(CharSequence chunkedSequence, int size, Function1<? super CharSequence, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(chunkedSequence, "$this$chunkedSequence");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return StringsKt.windowedSequence(chunkedSequence, size, size, true, transform);
    }

    public static final Tuples<CharSequence, CharSequence> partition(CharSequence partition, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(partition, "$this$partition");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        for (int i = 0; i < partition.length(); i++) {
            char element = partition.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                first.append(element);
            } else {
                second.append(element);
            }
        }
        return new Tuples<>(first, second);
    }

    public static final Tuples<String, String> partition(String partition, Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(partition, "$this$partition");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        int length = partition.length();
        for (int i = 0; i < length; i++) {
            char element = partition.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                first.append(element);
            } else {
                second.append(element);
            }
        }
        return new Tuples<>(first.toString(), second.toString());
    }

    public static /* synthetic */ List windowed$default(CharSequence charSequence, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return StringsKt.windowed(charSequence, i, i2, z);
    }

    public static final List<String> windowed(CharSequence windowed, int size, int step, boolean partialWindows) {
        Intrinsics.checkParameterIsNotNull(windowed, "$this$windowed");
        return StringsKt.windowed(windowed, size, step, partialWindows, new Function1<CharSequence, String>() { // from class: kotlin.text.StringsKt___StringsKt.windowed.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(CharSequence it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.toString();
            }
        });
    }

    public static /* synthetic */ List windowed$default(CharSequence charSequence, int i, int i2, boolean z, Function1 function1, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return StringsKt.windowed(charSequence, i, i2, z, function1);
    }

    public static final <R> List<R> windowed(CharSequence windowed, int size, int step, boolean partialWindows, Function1<? super CharSequence, ? extends R> transform) {
        int coercedEnd;
        Intrinsics.checkParameterIsNotNull(windowed, "$this$windowed");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        SlidingWindow3.checkWindowSizeStep(size, step);
        int thisSize = windowed.length();
        ArrayList result = new ArrayList(((thisSize + step) - 1) / step);
        int index = 0;
        while (index < thisSize) {
            int end = index + size;
            if (end <= thisSize) {
                coercedEnd = end;
            } else {
                if (!partialWindows) {
                    break;
                }
                coercedEnd = thisSize;
            }
            result.add(transform.invoke(windowed.subSequence(index, coercedEnd)));
            index += step;
        }
        return result;
    }

    public static /* synthetic */ Sequence windowedSequence$default(CharSequence charSequence, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return StringsKt.windowedSequence(charSequence, i, i2, z);
    }

    public static final Sequence<String> windowedSequence(CharSequence windowedSequence, int size, int step, boolean partialWindows) {
        Intrinsics.checkParameterIsNotNull(windowedSequence, "$this$windowedSequence");
        return StringsKt.windowedSequence(windowedSequence, size, step, partialWindows, new Function1<CharSequence, String>() { // from class: kotlin.text.StringsKt___StringsKt.windowedSequence.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(CharSequence it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.toString();
            }
        });
    }

    public static /* synthetic */ Sequence windowedSequence$default(CharSequence charSequence, int i, int i2, boolean z, Function1 function1, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return StringsKt.windowedSequence(charSequence, i, i2, z, function1);
    }

    public static final <R> Sequence<R> windowedSequence(final CharSequence windowedSequence, final int size, int step, boolean partialWindows, final Function1<? super CharSequence, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(windowedSequence, "$this$windowedSequence");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        SlidingWindow3.checkWindowSizeStep(size, step);
        Progressions2 windows = RangesKt.step(partialWindows ? StringsKt.getIndices(windowedSequence) : RangesKt.until(0, (windowedSequence.length() - size) + 1), step);
        return SequencesKt.map(CollectionsKt.asSequence(windows), new Function1<Integer, R>() { // from class: kotlin.text.StringsKt___StringsKt.windowedSequence.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final R invoke(int i) {
                return (R) transform.invoke(windowedSequence.subSequence(i, RangesKt.coerceAtMost(size + i, windowedSequence.length())));
            }
        });
    }

    public static final List<Tuples<Character, Character>> zip(CharSequence zip, CharSequence other) {
        Intrinsics.checkParameterIsNotNull(zip, "$this$zip");
        Intrinsics.checkParameterIsNotNull(other, "other");
        int length$iv = Math.min(zip.length(), other.length());
        ArrayList list$iv = new ArrayList(length$iv);
        for (int i$iv = 0; i$iv < length$iv; i$iv++) {
            char c1 = zip.charAt(i$iv);
            char c2 = other.charAt(i$iv);
            list$iv.add(Tuples3.to(Character.valueOf(c1), Character.valueOf(c2)));
        }
        return list$iv;
    }

    public static final <V> List<V> zip(CharSequence zip, CharSequence other, Function2<? super Character, ? super Character, ? extends V> transform) {
        Intrinsics.checkParameterIsNotNull(zip, "$this$zip");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int length = Math.min(zip.length(), other.length());
        ArrayList list = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            list.add(transform.invoke(Character.valueOf(zip.charAt(i)), Character.valueOf(other.charAt(i))));
        }
        return list;
    }

    public static final List<Tuples<Character, Character>> zipWithNext(CharSequence zipWithNext) {
        Intrinsics.checkParameterIsNotNull(zipWithNext, "$this$zipWithNext");
        int size$iv = zipWithNext.length() - 1;
        if (size$iv < 1) {
            return CollectionsKt.emptyList();
        }
        ArrayList result$iv = new ArrayList(size$iv);
        for (int index$iv = 0; index$iv < size$iv; index$iv++) {
            char a = zipWithNext.charAt(index$iv);
            char b = zipWithNext.charAt(index$iv + 1);
            result$iv.add(Tuples3.to(Character.valueOf(a), Character.valueOf(b)));
        }
        return result$iv;
    }

    public static final <R> List<R> zipWithNext(CharSequence zipWithNext, Function2<? super Character, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(zipWithNext, "$this$zipWithNext");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int size = zipWithNext.length() - 1;
        if (size < 1) {
            return CollectionsKt.emptyList();
        }
        ArrayList result = new ArrayList(size);
        for (int index = 0; index < size; index++) {
            result.add(transform.invoke(Character.valueOf(zipWithNext.charAt(index)), Character.valueOf(zipWithNext.charAt(index + 1))));
        }
        return result;
    }

    public static final Iterable<Character> asIterable(CharSequence asIterable) {
        Intrinsics.checkParameterIsNotNull(asIterable, "$this$asIterable");
        if (asIterable instanceof String) {
            if (asIterable.length() == 0) {
                return CollectionsKt.emptyList();
            }
        }
        return new StringsKt___StringsKt$asIterable$$inlined$Iterable$1(asIterable);
    }

    public static final Sequence<Character> asSequence(final CharSequence asSequence) {
        Intrinsics.checkParameterIsNotNull(asSequence, "$this$asSequence");
        if (asSequence instanceof String) {
            if (asSequence.length() == 0) {
                return SequencesKt.emptySequence();
            }
        }
        return new Sequence<Character>() { // from class: kotlin.text.StringsKt___StringsKt$asSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public Iterator<Character> iterator() {
                return StringsKt.iterator(asSequence);
            }
        };
    }
}
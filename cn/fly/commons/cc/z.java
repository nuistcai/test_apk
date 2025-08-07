package cn.fly.commons.cc;

import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.jvm.internal.IntCompanionObject;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.jvm.internal.ShortCompanionObject;

/* loaded from: classes.dex */
public class z {
    private a a;

    public z(Number number, Number number2, Number number3) {
        this.a = new a(number, number2, number3);
    }

    public a a() {
        return this.a;
    }

    public boolean a(Number number) {
        return ((Comparable) this.a.a).compareTo(number) <= 0 && ((Comparable) this.a.b).compareTo(number) >= 0;
    }

    public boolean b(Number number) {
        return a(number);
    }

    public Number[] b() {
        return new Number[]{this.a.a, this.a.b};
    }

    public static class a {
        private Number a;
        private Number b;
        private Number c;
        private Number d;
        private boolean e;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r13v22, types: [java.lang.Number] */
        /* JADX WARN: Type inference failed for: r13v30 */
        /* JADX WARN: Type inference failed for: r13v31 */
        /* JADX WARN: Type inference failed for: r13v32 */
        /* JADX WARN: Type inference failed for: r13v33 */
        /* JADX WARN: Type inference failed for: r13v34 */
        /* JADX WARN: Type inference failed for: r13v35 */
        /* JADX WARN: Type inference failed for: r13v36 */
        /* JADX WARN: Type inference failed for: r13v37 */
        public a(Number number, Number number2, Number number3) throws NumberFormatException {
            Number[] numberArr = {number, number2, number3};
            int[] iArr = new int[3];
            iArr[0] = 0;
            iArr[1] = 0;
            iArr[2] = 0;
            for (int i = 0; i < 3; i++) {
                Number number4 = numberArr[i];
                if (number4 != null) {
                    if (number4 instanceof Byte) {
                        iArr[i] = 1;
                    } else if (number4 instanceof Short) {
                        iArr[i] = 2;
                    } else if (number4 instanceof Integer) {
                        iArr[i] = 3;
                    } else if (number4 instanceof Long) {
                        iArr[i] = 4;
                    } else if (number4 instanceof Float) {
                        iArr[i] = 5;
                    } else if (number4 instanceof Double) {
                        iArr[i] = 6;
                    }
                }
            }
            int i2 = 0;
            for (int i3 = 0; i3 < 3; i3++) {
                if (i2 < iArr[i3]) {
                    i2 = iArr[i3];
                }
            }
            ?? ValueOf = number;
            if (number == null) {
                ValueOf = new Number[]{Integer.MIN_VALUE, Byte.valueOf(ByteCompanionObject.MIN_VALUE), Short.valueOf(ShortCompanionObject.MIN_VALUE), Integer.MIN_VALUE, Long.MIN_VALUE, Float.valueOf(Float.MIN_VALUE), Double.valueOf(Double.MIN_VALUE)}[i2];
            } else {
                switch (i2) {
                    case 1:
                        ValueOf = Byte.valueOf(Double.valueOf(String.valueOf(number)).byteValue());
                        break;
                    case 2:
                        ValueOf = Short.valueOf(Double.valueOf(String.valueOf(number)).shortValue());
                        break;
                    case 3:
                        ValueOf = Integer.valueOf(Double.valueOf(String.valueOf(number)).intValue());
                        break;
                    case 4:
                        ValueOf = Long.valueOf(Double.valueOf(String.valueOf(number)).longValue());
                        break;
                    case 5:
                        ValueOf = Float.valueOf(Double.valueOf(String.valueOf(number)).floatValue());
                        break;
                    case 6:
                        ValueOf = Double.valueOf(String.valueOf(number));
                        break;
                }
            }
            if (number2 == null) {
                number2 = new Number[]{Integer.valueOf(IntCompanionObject.MAX_VALUE), Byte.valueOf(ByteCompanionObject.MAX_VALUE), Short.valueOf(ShortCompanionObject.MAX_VALUE), Integer.valueOf(IntCompanionObject.MAX_VALUE), Long.valueOf(LongCompanionObject.MAX_VALUE), Float.valueOf(Float.MAX_VALUE), Double.valueOf(Double.MAX_VALUE)}[i2];
            } else {
                switch (i2) {
                    case 1:
                        number2 = Byte.valueOf(Double.valueOf(String.valueOf(number2)).byteValue());
                        break;
                    case 2:
                        number2 = Short.valueOf(Double.valueOf(String.valueOf(number2)).shortValue());
                        break;
                    case 3:
                        number2 = Integer.valueOf(Double.valueOf(String.valueOf(number2)).intValue());
                        break;
                    case 4:
                        number2 = Long.valueOf(Double.valueOf(String.valueOf(number2)).longValue());
                        break;
                    case 5:
                        number2 = Float.valueOf(Double.valueOf(String.valueOf(number2)).floatValue());
                        break;
                    case 6:
                        number2 = Double.valueOf(String.valueOf(number2));
                        break;
                }
            }
            this.a = ValueOf;
            this.b = number2;
            this.c = number3;
            this.e = ((Comparable) ValueOf).compareTo(number2) > 0;
            if (this.c == null) {
                this.c = Integer.valueOf(this.e ? -1 : 1);
            }
        }

        public boolean a() {
            Object obj = this.d == null ? this.a : this.d;
            return this.e ? ((Comparable) obj).compareTo(this.b) >= 0 : ((Comparable) obj).compareTo(this.b) <= 0;
        }

        public Number b() {
            if (this.d == null) {
                this.d = this.a;
            }
            Number number = this.d;
            if (this.c instanceof Double) {
                this.d = Double.valueOf(this.d.doubleValue() + this.c.doubleValue());
            } else if (this.c instanceof Float) {
                this.d = Float.valueOf(this.d.floatValue() + this.c.floatValue());
            } else if (this.c instanceof Long) {
                this.d = Long.valueOf(this.d.longValue() + this.c.longValue());
            } else if (this.c instanceof Integer) {
                this.d = Integer.valueOf(this.d.intValue() + this.c.intValue());
            } else if (this.c instanceof Short) {
                this.d = Integer.valueOf(this.d.shortValue() + this.c.shortValue());
            } else {
                this.d = Integer.valueOf(this.d.byteValue() + this.c.byteValue());
            }
            return number;
        }
    }
}
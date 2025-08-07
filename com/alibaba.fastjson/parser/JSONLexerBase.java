package com.alibaba.fastjson.parser;

import androidx.core.view.MotionEventCompat;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.tencent.bugly.Bugly;
import java.io.Closeable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public abstract class JSONLexerBase implements JSONLexer, Closeable {
    protected static final int INT_MULTMIN_RADIX_TEN = -214748364;
    protected static final long MULTMIN_RADIX_TEN = -922337203685477580L;
    protected int bp;
    protected char ch;
    protected int eofPos;
    protected int features;
    protected boolean hasSpecial;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected String stringDefaultValue;
    protected int token;
    private static final ThreadLocal<char[]> SBUF_LOCAL = new ThreadLocal<>();
    protected static final char[] typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
    protected static final int[] digits = new int[103];
    protected Calendar calendar = null;
    protected TimeZone timeZone = JSON.defaultTimeZone;
    protected Locale locale = JSON.defaultLocale;
    public int matchStat = 0;
    protected int nanos = 0;

    public abstract String addSymbol(int i, int i2, int i3, SymbolTable symbolTable);

    protected abstract void arrayCopy(int i, char[] cArr, int i2, int i3);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract byte[] bytesValue();

    protected abstract boolean charArrayCompare(char[] cArr);

    public abstract char charAt(int i);

    protected abstract void copyTo(int i, int i2, char[] cArr);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract BigDecimal decimalValue();

    public abstract int indexOf(char c, int i);

    public abstract boolean isEOF();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract char next();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String numberString();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String stringVal();

    public abstract String subString(int i, int i2);

    protected abstract char[] sub_chars(int i, int i2);

    protected void lexError(String key, Object... args) {
        this.token = 1;
    }

    static {
        for (int i = 48; i <= 57; i++) {
            digits[i] = i - 48;
        }
        for (int i2 = 97; i2 <= 102; i2++) {
            digits[i2] = (i2 - 97) + 10;
        }
        for (int i3 = 65; i3 <= 70; i3++) {
            digits[i3] = (i3 - 65) + 10;
        }
    }

    public JSONLexerBase(int features) {
        this.stringDefaultValue = null;
        this.features = features;
        if ((Feature.InitStringFieldAsEmpty.mask & features) != 0) {
            this.stringDefaultValue = "";
        }
        this.sbuf = SBUF_LOCAL.get();
        if (this.sbuf == null) {
            this.sbuf = new char[512];
        }
    }

    public final int matchStat() {
        return this.matchStat;
    }

    public void setToken(int token) {
        this.token = token;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextToken() throws NumberFormatException {
        this.sp = 0;
        while (true) {
            this.pos = this.bp;
            if (this.ch == '/') {
                skipComment();
            } else {
                if (this.ch == '\"') {
                    scanString();
                    return;
                }
                if (this.ch == ',') {
                    next();
                    this.token = 16;
                    return;
                }
                if (this.ch >= '0' && this.ch <= '9') {
                    scanNumber();
                    return;
                }
                if (this.ch == '-') {
                    scanNumber();
                    return;
                }
                switch (this.ch) {
                    case '\b':
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                        next();
                        break;
                    case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                        if (!isEnabled(Feature.AllowSingleQuotes)) {
                            throw new JSONException("Feature.AllowSingleQuotes is false");
                        }
                        scanStringSingleQuote();
                        return;
                    case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                        next();
                        this.token = 10;
                        return;
                    case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                        next();
                        this.token = 11;
                        return;
                    case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                        next();
                        scanNumber();
                        return;
                    case MotionEventCompat.AXIS_GENERIC_15 /* 46 */:
                        next();
                        this.token = 25;
                        return;
                    case Opcodes.ASTORE /* 58 */:
                        next();
                        this.token = 17;
                        return;
                    case ';':
                        next();
                        this.token = 24;
                        return;
                    case 'N':
                    case 'S':
                    case 'T':
                    case 'u':
                        scanIdent();
                        return;
                    case '[':
                        next();
                        this.token = 14;
                        return;
                    case ']':
                        next();
                        this.token = 15;
                        return;
                    case 'f':
                        scanFalse();
                        return;
                    case 'n':
                        scanNullOrNew();
                        return;
                    case 't':
                        scanTrue();
                        return;
                    case 'x':
                        scanHex();
                        return;
                    case '{':
                        next();
                        this.token = 12;
                        return;
                    case '}':
                        next();
                        this.token = 13;
                        return;
                    default:
                        if (isEOF()) {
                            if (this.token == 20) {
                                throw new JSONException("EOF error");
                            }
                            this.token = 20;
                            int i = this.bp;
                            this.pos = i;
                            this.eofPos = i;
                            return;
                        }
                        if (this.ch <= 31 || this.ch == 127) {
                            next();
                            break;
                        } else {
                            lexError("illegal.char", String.valueOf((int) this.ch));
                            next();
                            return;
                        }
                        break;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0067 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0118 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00f8  */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void nextToken(int expect) throws NumberFormatException {
        this.sp = 0;
        while (true) {
            switch (expect) {
                case 2:
                    if (this.ch >= '0' && this.ch <= '9') {
                        this.pos = this.bp;
                        scanNumber();
                        break;
                    } else if (this.ch == '\"') {
                        this.pos = this.bp;
                        scanString();
                        break;
                    } else if (this.ch == '[') {
                        this.token = 14;
                        next();
                        break;
                    } else {
                        if (this.ch == '{') {
                            this.token = 12;
                            next();
                            break;
                        }
                        if (this.ch != ' ' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == '\f' || this.ch == '\b') {
                            next();
                        } else {
                            nextToken();
                            break;
                        }
                    }
                    break;
                case 4:
                    if (this.ch == '\"') {
                        this.pos = this.bp;
                        scanString();
                        break;
                    } else if (this.ch >= '0' && this.ch <= '9') {
                        this.pos = this.bp;
                        scanNumber();
                        break;
                    } else if (this.ch == '[') {
                        this.token = 14;
                        next();
                        break;
                    } else {
                        if (this.ch == '{') {
                            this.token = 12;
                            next();
                            break;
                        }
                        if (this.ch != ' ') {
                            break;
                        }
                        next();
                    }
                    break;
                case 12:
                    if (this.ch == '{') {
                        this.token = 12;
                        next();
                        break;
                    } else {
                        if (this.ch == '[') {
                            this.token = 14;
                            next();
                            break;
                        }
                        if (this.ch != ' ') {
                        }
                        next();
                    }
                    break;
                case 14:
                    if (this.ch == '[') {
                        this.token = 14;
                        next();
                        break;
                    } else {
                        if (this.ch == '{') {
                            this.token = 12;
                            next();
                            break;
                        }
                        if (this.ch != ' ') {
                        }
                        next();
                    }
                    break;
                case 15:
                    if (this.ch == ']') {
                        this.token = 15;
                        next();
                        break;
                    }
                    if (this.ch == 26) {
                        this.token = 20;
                        break;
                    }
                    if (this.ch != ' ') {
                    }
                    next();
                    break;
                case 16:
                    if (this.ch == ',') {
                        this.token = 16;
                        next();
                        break;
                    } else if (this.ch == '}') {
                        this.token = 13;
                        next();
                        break;
                    } else if (this.ch == ']') {
                        this.token = 15;
                        next();
                        break;
                    } else if (this.ch == 26) {
                        this.token = 20;
                        break;
                    } else {
                        if (this.ch == 'n') {
                            scanNullOrNew(false);
                            break;
                        }
                        if (this.ch != ' ') {
                        }
                        next();
                    }
                    break;
                case 18:
                    nextIdent();
                    break;
                case 20:
                    if (this.ch == 26) {
                    }
                    if (this.ch != ' ') {
                    }
                    next();
                    break;
                default:
                    if (this.ch != ' ') {
                    }
                    next();
                    break;
            }
            return;
        }
    }

    public final void nextIdent() throws NumberFormatException {
        while (isWhitespace(this.ch)) {
            next();
        }
        if (this.ch == '_' || this.ch == '$' || Character.isLetter(this.ch)) {
            scanIdent();
        } else {
            nextToken();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon() throws NumberFormatException {
        nextTokenWithChar(':');
    }

    public final void nextTokenWithChar(char expect) throws NumberFormatException {
        this.sp = 0;
        while (this.ch != expect) {
            if (this.ch == ' ' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == '\f' || this.ch == '\b') {
                next();
            } else {
                throw new JSONException("not match " + expect + " - " + this.ch + ", info : " + info());
            }
        }
        next();
        nextToken();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int token() {
        return this.token;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String tokenName() {
        return JSONToken.name(this.token);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int pos() {
        return this.pos;
    }

    public final String stringDefaultValue() {
        return this.stringDefaultValue;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number integerValue() throws NumberFormatException {
        long limit;
        long result = 0;
        boolean negative = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int i = this.np;
        int max = this.np + this.sp;
        char type = ' ';
        switch (charAt(max - 1)) {
            case 'B':
                max--;
                type = 'B';
                break;
            case 'L':
                max--;
                type = 'L';
                break;
            case 'S':
                max--;
                type = 'S';
                break;
        }
        if (charAt(this.np) == '-') {
            negative = true;
            limit = Long.MIN_VALUE;
            i++;
        } else {
            limit = -9223372036854775807L;
        }
        if (i < max) {
            result = -(charAt(i) - 48);
            i++;
        }
        while (i < max) {
            int i2 = i + 1;
            int digit = charAt(i) - 48;
            if (result < MULTMIN_RADIX_TEN) {
                return new BigInteger(numberString(), 10);
            }
            long result2 = result * 10;
            if (result2 < digit + limit) {
                return new BigInteger(numberString(), 10);
            }
            result = result2 - digit;
            i = i2;
        }
        if (negative) {
            if (i <= this.np + 1) {
                throw new JSONException("illegal number format : " + numberString());
            }
            if (result >= -2147483648L && type != 'L') {
                if (type == 'S') {
                    return Short.valueOf((short) result);
                }
                if (type == 'B') {
                    return Byte.valueOf((byte) result);
                }
                return Integer.valueOf((int) result);
            }
            return Long.valueOf(result);
        }
        long result3 = -result;
        if (result3 <= 2147483647L && type != 'L') {
            if (type == 'S') {
                return Short.valueOf((short) result3);
            }
            if (type == 'B') {
                return Byte.valueOf((byte) result3);
            }
            return Integer.valueOf((int) result3);
        }
        return Long.valueOf(result3);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon(int expect) throws NumberFormatException {
        nextTokenWithChar(':');
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public float floatValue() throws NumberFormatException {
        char c0;
        String strVal = numberString();
        float floatValue = Float.parseFloat(strVal);
        if ((floatValue == 0.0f || floatValue == Float.POSITIVE_INFINITY) && (c0 = strVal.charAt(0)) > '0' && c0 <= '9') {
            throw new JSONException("float overflow : " + strVal);
        }
        return floatValue;
    }

    public double doubleValue() {
        return Double.parseDouble(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void config(Feature feature, boolean state) {
        this.features = Feature.config(this.features, feature, state);
        if ((this.features & Feature.InitStringFieldAsEmpty.mask) != 0) {
            this.stringDefaultValue = "";
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(Feature feature) {
        return isEnabled(feature.mask);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(int feature) {
        return (this.features & feature) != 0;
    }

    public final boolean isEnabled(int features, int feature) {
        return ((this.features & feature) == 0 && (features & feature) == 0) ? false : true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final char getCurrent() {
        return this.ch;
    }

    protected void skipComment() {
        next();
        if (this.ch == '/') {
            do {
                next();
                if (this.ch == '\n') {
                    next();
                    return;
                }
            } while (this.ch != 26);
            return;
        }
        if (this.ch == '*') {
            next();
            while (this.ch != 26) {
                if (this.ch == '*') {
                    next();
                    if (this.ch == '/') {
                        next();
                        return;
                    }
                } else {
                    next();
                }
            }
            return;
        }
        throw new JSONException("invalid comment");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable) {
        skipWhitespace();
        if (this.ch == '\"') {
            return scanSymbol(symbolTable, Typography.quote);
        }
        if (this.ch == '\'') {
            if (!isEnabled(Feature.AllowSingleQuotes)) {
                throw new JSONException("syntax error");
            }
            return scanSymbol(symbolTable, '\'');
        }
        if (this.ch == '}') {
            next();
            this.token = 13;
            return null;
        }
        if (this.ch == ',') {
            next();
            this.token = 16;
            return null;
        }
        if (this.ch == 26) {
            this.token = 20;
            return null;
        }
        if (!isEnabled(Feature.AllowUnQuotedFieldNames)) {
            throw new JSONException("syntax error");
        }
        return scanSymbolUnQuoted(symbolTable);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable, char quote) throws NumberFormatException {
        String value;
        int offset;
        int hash = 0;
        this.np = this.bp;
        this.sp = 0;
        boolean hasSpecial = false;
        while (true) {
            char chLocal = next();
            if (chLocal != quote) {
                if (chLocal == 26) {
                    throw new JSONException("unclosed.str");
                }
                if (chLocal == '\\') {
                    if (!hasSpecial) {
                        hasSpecial = true;
                        if (this.sp >= this.sbuf.length) {
                            int newCapcity = this.sbuf.length * 2;
                            if (this.sp > newCapcity) {
                                newCapcity = this.sp;
                            }
                            char[] newsbuf = new char[newCapcity];
                            System.arraycopy(this.sbuf, 0, newsbuf, 0, this.sbuf.length);
                            this.sbuf = newsbuf;
                        }
                        arrayCopy(this.np + 1, this.sbuf, 0, this.sp);
                    }
                    char chLocal2 = next();
                    switch (chLocal2) {
                        case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                            hash = (hash * 31) + 34;
                            putChar(Typography.quote);
                            break;
                        case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                            hash = (hash * 31) + 39;
                            putChar('\'');
                            break;
                        case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                            hash = (hash * 31) + 47;
                            putChar('/');
                            break;
                        case '0':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 0);
                            break;
                        case Opcodes.V1_5 /* 49 */:
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 1);
                            break;
                        case '2':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 2);
                            break;
                        case '3':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 3);
                            break;
                        case '4':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 4);
                            break;
                        case '5':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 5);
                            break;
                        case Opcodes.ISTORE /* 54 */:
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 6);
                            break;
                        case Opcodes.LSTORE /* 55 */:
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 7);
                            break;
                        case 'F':
                        case 'f':
                            hash = (hash * 31) + 12;
                            putChar('\f');
                            break;
                        case '\\':
                            hash = (hash * 31) + 92;
                            putChar('\\');
                            break;
                        case 'b':
                            hash = (hash * 31) + 8;
                            putChar('\b');
                            break;
                        case 'n':
                            hash = (hash * 31) + 10;
                            putChar('\n');
                            break;
                        case 'r':
                            hash = (hash * 31) + 13;
                            putChar('\r');
                            break;
                        case 't':
                            hash = (hash * 31) + 9;
                            putChar('\t');
                            break;
                        case 'u':
                            char c1 = next();
                            char c2 = next();
                            char c3 = next();
                            char c4 = next();
                            int val = Integer.parseInt(new String(new char[]{c1, c2, c3, c4}), 16);
                            hash = (hash * 31) + val;
                            putChar((char) val);
                            break;
                        case 'v':
                            hash = (hash * 31) + 11;
                            putChar((char) 11);
                            break;
                        case 'x':
                            char x1 = next();
                            this.ch = x1;
                            char x2 = next();
                            this.ch = x2;
                            int x_val = (digits[x1] * 16) + digits[x2];
                            char x_char = (char) x_val;
                            hash = (hash * 31) + x_char;
                            putChar(x_char);
                            break;
                        default:
                            this.ch = chLocal2;
                            throw new JSONException("unclosed.str.lit");
                    }
                } else {
                    hash = (hash * 31) + chLocal;
                    if (!hasSpecial) {
                        this.sp++;
                    } else if (this.sp == this.sbuf.length) {
                        putChar(chLocal);
                    } else {
                        char[] cArr = this.sbuf;
                        int i = this.sp;
                        this.sp = i + 1;
                        cArr[i] = chLocal;
                    }
                }
            } else {
                this.token = 4;
                if (hasSpecial) {
                    value = symbolTable.addSymbol(this.sbuf, 0, this.sp, hash);
                } else {
                    if (this.np == -1) {
                        offset = 0;
                    } else {
                        int offset2 = this.np;
                        offset = offset2 + 1;
                    }
                    value = addSymbol(offset, this.sp, hash, symbolTable);
                }
                this.sp = 0;
                next();
                return value;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void resetStringPosition() {
        this.sp = 0;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        return "";
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        if (this.token == 1 && this.pos == 0 && this.bp == 1) {
            this.bp = 0;
        }
        boolean[] firstIdentifierFlags = IOUtils.firstIdentifierFlags;
        char first = this.ch;
        boolean firstFlag = this.ch >= firstIdentifierFlags.length || firstIdentifierFlags[first];
        if (!firstFlag) {
            throw new JSONException("illegal identifier : " + this.ch + info());
        }
        boolean[] identifierFlags = IOUtils.identifierFlags;
        int hash = first;
        this.np = this.bp;
        this.sp = 1;
        while (true) {
            char chLocal = next();
            if (chLocal < identifierFlags.length && !identifierFlags[chLocal]) {
                break;
            }
            int NULL_HASH = hash * 31;
            hash = NULL_HASH + chLocal;
            this.sp++;
        }
        this.ch = charAt(this.bp);
        this.token = 18;
        if (this.sp == 4 && hash == 3392903 && charAt(this.np) == 'n' && charAt(this.np + 1) == 'u' && charAt(this.np + 2) == 'l' && charAt(this.np + 3) == 'l') {
            return null;
        }
        if (symbolTable == null) {
            return subString(this.np, this.sp);
        }
        return addSymbol(this.np, this.sp, hash, symbolTable);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void scanString() throws NumberFormatException {
        char x1;
        char x2;
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char ch = next();
            if (ch == '\"') {
                this.token = 4;
                this.ch = next();
                return;
            }
            if (ch == 26) {
                if (!isEOF()) {
                    putChar(JSONLexer.EOI);
                } else {
                    throw new JSONException("unclosed string : " + ch);
                }
            } else {
                boolean z = true;
                if (ch == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        if (this.sp >= this.sbuf.length) {
                            int newCapcity = this.sbuf.length * 2;
                            if (this.sp > newCapcity) {
                                newCapcity = this.sp;
                            }
                            char[] newsbuf = new char[newCapcity];
                            System.arraycopy(this.sbuf, 0, newsbuf, 0, this.sbuf.length);
                            this.sbuf = newsbuf;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char ch2 = next();
                    switch (ch2) {
                        case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                            putChar(Typography.quote);
                            break;
                        case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                            putChar('\'');
                            break;
                        case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                            putChar('/');
                            break;
                        case '0':
                            putChar((char) 0);
                            break;
                        case Opcodes.V1_5 /* 49 */:
                            putChar((char) 1);
                            break;
                        case '2':
                            putChar((char) 2);
                            break;
                        case '3':
                            putChar((char) 3);
                            break;
                        case '4':
                            putChar((char) 4);
                            break;
                        case '5':
                            putChar((char) 5);
                            break;
                        case Opcodes.ISTORE /* 54 */:
                            putChar((char) 6);
                            break;
                        case Opcodes.LSTORE /* 55 */:
                            putChar((char) 7);
                            break;
                        case 'F':
                        case 'f':
                            putChar('\f');
                            break;
                        case '\\':
                            putChar('\\');
                            break;
                        case 'b':
                            putChar('\b');
                            break;
                        case 'n':
                            putChar('\n');
                            break;
                        case 'r':
                            putChar('\r');
                            break;
                        case 't':
                            putChar('\t');
                            break;
                        case 'u':
                            char u1 = next();
                            char u2 = next();
                            char u3 = next();
                            char u4 = next();
                            int val = Integer.parseInt(new String(new char[]{u1, u2, u3, u4}), 16);
                            putChar((char) val);
                            break;
                        case 'v':
                            putChar((char) 11);
                            break;
                        case 'x':
                            x1 = next();
                            x2 = next();
                            boolean hex1 = (x1 >= '0' && x1 <= '9') || (x1 >= 'a' && x1 <= 'f') || (x1 >= 'A' && x1 <= 'F');
                            if ((x2 < '0' || x2 > '9') && ((x2 < 'a' || x2 > 'f') && (x2 < 'A' || x2 > 'F'))) {
                                z = false;
                            }
                            boolean hex2 = z;
                            if (!hex1 || !hex2) {
                                break;
                            } else {
                                char x_char = (char) ((digits[x1] * 16) + digits[x2]);
                                putChar(x_char);
                                break;
                            }
                        default:
                            this.ch = ch2;
                            throw new JSONException("unclosed string : " + ch2);
                    }
                } else if (!this.hasSpecial) {
                    this.sp++;
                } else if (this.sp == this.sbuf.length) {
                    putChar(ch);
                } else {
                    char[] cArr = this.sbuf;
                    int i = this.sp;
                    this.sp = i + 1;
                    cArr[i] = ch;
                }
            }
        }
        throw new JSONException("invalid escape character \\x" + x1 + x2);
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Locale getLocale() {
        return this.locale;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int intValue() {
        int limit;
        if (this.np == -1) {
            this.np = 0;
        }
        int result = 0;
        boolean negative = false;
        int i = this.np;
        int max = this.np + this.sp;
        if (charAt(this.np) == '-') {
            negative = true;
            limit = Integer.MIN_VALUE;
            i++;
        } else {
            limit = -2147483647;
        }
        if (i < max) {
            result = -(charAt(i) - 48);
            i++;
        }
        while (i < max) {
            int i2 = i + 1;
            char chLocal = charAt(i);
            if (chLocal != 'L' && chLocal != 'S' && chLocal != 'B') {
                int digit = chLocal - '0';
                if (result < -214748364) {
                    throw new NumberFormatException(numberString());
                }
                int result2 = result * 10;
                if (result2 < limit + digit) {
                    throw new NumberFormatException(numberString());
                }
                result = result2 - digit;
                i = i2;
            } else {
                i = i2;
                break;
            }
        }
        if (negative) {
            if (i > this.np + 1) {
                return result;
            }
            throw new NumberFormatException(numberString());
        }
        return -result;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.sbuf.length <= 8192) {
            SBUF_LOCAL.set(this.sbuf);
        }
        this.sbuf = null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isRef() {
        return this.sp == 4 && charAt(this.np + 1) == '$' && charAt(this.np + 2) == 'r' && charAt(this.np + 3) == 'e' && charAt(this.np + 4) == 'f';
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanTypeName(SymbolTable symbolTable) {
        return null;
    }

    public final int scanType(String type) {
        this.matchStat = 0;
        if (!charArrayCompare(typeFieldName)) {
            return -2;
        }
        int bpLocal = this.bp + typeFieldName.length;
        int typeLength = type.length();
        for (int i = 0; i < typeLength; i++) {
            if (type.charAt(i) != charAt(bpLocal + i)) {
                return -1;
            }
        }
        int bpLocal2 = bpLocal + typeLength;
        if (charAt(bpLocal2) != '\"') {
            return -1;
        }
        int bpLocal3 = bpLocal2 + 1;
        this.ch = charAt(bpLocal3);
        if (this.ch == ',') {
            int bpLocal4 = bpLocal3 + 1;
            this.ch = charAt(bpLocal4);
            this.bp = bpLocal4;
            this.token = 16;
            return 3;
        }
        if (this.ch == '}') {
            bpLocal3++;
            this.ch = charAt(bpLocal3);
            if (this.ch == ',') {
                this.token = 16;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else if (this.ch == ']') {
                this.token = 15;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else if (this.ch == '}') {
                this.token = 13;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else {
                if (this.ch != 26) {
                    return -1;
                }
                this.token = 20;
            }
            this.matchStat = 4;
        }
        this.bp = bpLocal3;
        return this.matchStat;
    }

    public final boolean matchField(char[] fieldName) throws NumberFormatException {
        while (!charArrayCompare(fieldName)) {
            if (isWhitespace(this.ch)) {
                next();
            } else {
                return false;
            }
        }
        this.bp += fieldName.length;
        this.ch = charAt(this.bp);
        if (this.ch == '{') {
            next();
            this.token = 12;
        } else if (this.ch == '[') {
            next();
            this.token = 14;
        } else if (this.ch == 'S' && charAt(this.bp + 1) == 'e' && charAt(this.bp + 2) == 't' && charAt(this.bp + 3) == '[') {
            this.bp += 3;
            this.ch = charAt(this.bp);
            this.token = 21;
        } else {
            nextToken();
        }
        return true;
    }

    public int matchField(long fieldNameHash) {
        throw new UnsupportedOperationException();
    }

    public boolean seekArrayToItem(int index) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToField(long fieldNameHash, boolean deepScan) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToField(long[] fieldNameHash) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToFieldDeepScan(long fieldNameHash) {
        throw new UnsupportedOperationException();
    }

    public void skipObject() {
        throw new UnsupportedOperationException();
    }

    public void skipObject(boolean valid) {
        throw new UnsupportedOperationException();
    }

    public void skipArray() {
        throw new UnsupportedOperationException();
    }

    public String scanFieldString(char[] fieldName) {
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return stringDefaultValue();
        }
        int offset = fieldName.length;
        int offset2 = offset + 1;
        if (charAt(this.bp + offset) != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        int startIndex = this.bp + fieldName.length + 1;
        int endIndex = indexOf(Typography.quote, startIndex);
        if (endIndex == -1) {
            throw new JSONException("unclosed str");
        }
        int startIndex2 = this.bp + fieldName.length + 1;
        String stringVal = subString(startIndex2, endIndex - startIndex2);
        if (stringVal.indexOf(92) != -1) {
            while (true) {
                int slashCount = 0;
                for (int i = endIndex - 1; i >= 0 && charAt(i) == '\\'; i--) {
                    slashCount++;
                }
                int i2 = slashCount % 2;
                if (i2 == 0) {
                    break;
                }
                endIndex = indexOf(Typography.quote, endIndex + 1);
            }
            int chars_len = endIndex - ((this.bp + fieldName.length) + 1);
            char[] chars = sub_chars(this.bp + fieldName.length + 1, chars_len);
            stringVal = readString(chars, chars_len);
        }
        int offset3 = offset2 + (endIndex - ((this.bp + fieldName.length) + 1)) + 1;
        int offset4 = offset3 + 1;
        char chLocal = charAt(this.bp + offset3);
        String strVal = stringVal;
        if (chLocal == ',') {
            this.bp += offset4;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return strVal;
        }
        if (chLocal == '}') {
            int offset5 = offset4 + 1;
            char chLocal2 = charAt(this.bp + offset4);
            if (chLocal2 == ',') {
                this.token = 16;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == ']') {
                this.token = 15;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == '}') {
                this.token = 13;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == 26) {
                this.token = 20;
                this.bp += offset5 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return stringDefaultValue();
            }
            this.matchStat = 4;
            return strVal;
        }
        this.matchStat = -1;
        return stringDefaultValue();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanString(char expectNextChar) {
        this.matchStat = 0;
        int offset = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        if (chLocal == 'n') {
            if (charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
                int offset2 = offset + 3;
                int offset3 = offset2 + 1;
                if (charAt(this.bp + offset2) == expectNextChar) {
                    this.bp += offset3;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return null;
                }
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        }
        while (chLocal != '\"') {
            if (isWhitespace(chLocal)) {
                chLocal = charAt(this.bp + offset);
                offset++;
            } else {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        }
        int startIndex = this.bp + offset;
        int endIndex = indexOf(Typography.quote, startIndex);
        if (endIndex == -1) {
            throw new JSONException("unclosed str");
        }
        String stringVal = subString(this.bp + offset, endIndex - startIndex);
        if (stringVal.indexOf(92) != -1) {
            while (true) {
                int slashCount = 0;
                for (int i = endIndex - 1; i >= 0 && charAt(i) == '\\'; i--) {
                    slashCount++;
                }
                int i2 = slashCount % 2;
                if (i2 == 0) {
                    break;
                }
                endIndex = indexOf(Typography.quote, endIndex + 1);
            }
            int chars_len = endIndex - startIndex;
            char[] chars = sub_chars(this.bp + 1, chars_len);
            stringVal = readString(chars, chars_len);
        }
        int offset4 = offset + (endIndex - startIndex) + 1;
        int offset5 = offset4 + 1;
        char chLocal2 = charAt(this.bp + offset4);
        String strVal = stringVal;
        while (chLocal2 != expectNextChar) {
            if (isWhitespace(chLocal2)) {
                chLocal2 = charAt(this.bp + offset5);
                offset5++;
            } else {
                if (chLocal2 == ']') {
                    this.bp += offset5;
                    this.ch = charAt(this.bp);
                    this.matchStat = -1;
                }
                return strVal;
            }
        }
        this.bp += offset5;
        this.ch = charAt(this.bp);
        this.matchStat = 3;
        this.token = 16;
        return strVal;
    }

    public long scanFieldSymbol(char[] fieldName) {
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0L;
        }
        int offset = fieldName.length;
        int offset2 = offset + 1;
        if (charAt(this.bp + offset) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long hash = TypeUtils.fnv1a_64_magic_hashcode;
        while (true) {
            int offset3 = offset2 + 1;
            char chLocal = charAt(this.bp + offset2);
            if (chLocal == '\"') {
                int offset4 = offset3 + 1;
                char chLocal2 = charAt(this.bp + offset3);
                if (chLocal2 == ',') {
                    this.bp += offset4;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return hash;
                }
                if (chLocal2 == '}') {
                    int offset5 = offset4 + 1;
                    char chLocal3 = charAt(this.bp + offset4);
                    if (chLocal3 == ',') {
                        this.token = 16;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == ']') {
                        this.token = 15;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == '}') {
                        this.token = 13;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == 26) {
                        this.token = 20;
                        this.bp += offset5 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.matchStat = 4;
                    return hash;
                }
                this.matchStat = -1;
                return 0L;
            }
            hash = (hash ^ chLocal) * TypeUtils.fnv1a_64_magic_prime;
            if (chLocal != '\\') {
                offset2 = offset3;
            } else {
                this.matchStat = -1;
                return 0L;
            }
        }
    }

    public long scanEnumSymbol(char[] fieldName) {
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0L;
        }
        int offset = fieldName.length;
        int offset2 = offset + 1;
        if (charAt(this.bp + offset) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long hash = TypeUtils.fnv1a_64_magic_hashcode;
        while (true) {
            int offset3 = offset2 + 1;
            char chLocal = charAt(this.bp + offset2);
            if (chLocal == '\"') {
                int offset4 = offset3 + 1;
                char chLocal2 = charAt(this.bp + offset3);
                if (chLocal2 == ',') {
                    this.bp += offset4;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return hash;
                }
                if (chLocal2 == '}') {
                    int offset5 = offset4 + 1;
                    char chLocal3 = charAt(this.bp + offset4);
                    if (chLocal3 == ',') {
                        this.token = 16;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == ']') {
                        this.token = 15;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == '}') {
                        this.token = 13;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == 26) {
                        this.token = 20;
                        this.bp += offset5 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.matchStat = 4;
                    return hash;
                }
                this.matchStat = -1;
                return 0L;
            }
            hash = (hash ^ ((chLocal < 'A' || chLocal > 'Z') ? chLocal : chLocal + ' ')) * TypeUtils.fnv1a_64_magic_prime;
            if (chLocal != '\\') {
                offset2 = offset3;
            } else {
                this.matchStat = -1;
                return 0L;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Enum<?> scanEnum(Class<?> enumClass, SymbolTable symbolTable, char serperator) {
        String name = scanSymbolWithSeperator(symbolTable, serperator);
        if (name == null) {
            return null;
        }
        return Enum.valueOf(enumClass, name);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanSymbolWithSeperator(SymbolTable symbolTable, char serperator) {
        this.matchStat = 0;
        int offset = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        if (chLocal == 'n') {
            if (charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
                int offset2 = offset + 3;
                int offset3 = offset2 + 1;
                if (charAt(this.bp + offset2) == serperator) {
                    this.bp += offset3;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return null;
                }
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        }
        if (chLocal != '\"') {
            this.matchStat = -1;
            return null;
        }
        int hash = 0;
        while (true) {
            int offset4 = offset + 1;
            char chLocal2 = charAt(this.bp + offset);
            if (chLocal2 == '\"') {
                int start = this.bp + 0 + 1;
                int len = ((this.bp + offset4) - start) - 1;
                String strVal = addSymbol(start, len, hash, symbolTable);
                int offset5 = offset4 + 1;
                char chLocal3 = charAt(this.bp + offset4);
                while (chLocal3 != serperator) {
                    if (isWhitespace(chLocal3)) {
                        chLocal3 = charAt(this.bp + offset5);
                        offset5++;
                    } else {
                        this.matchStat = -1;
                        return strVal;
                    }
                }
                this.bp += offset5;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                return strVal;
            }
            hash = (hash * 31) + chLocal2;
            if (chLocal2 != '\\') {
                offset = offset4;
            } else {
                this.matchStat = -1;
                return null;
            }
        }
    }

    public Collection<String> newCollectionByType(Class<?> type) {
        if (type.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        }
        if (type.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        if (type.isAssignableFrom(LinkedList.class)) {
            return new LinkedList();
        }
        try {
            return (Collection) type.newInstance();
        } catch (Exception e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f7, code lost:
    
        if (r5 != ']') goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00fd, code lost:
    
        if (r3.size() != 0) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ff, code lost:
    
        r8 = r9 + 1;
        r5 = charAt(r17.bp + r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0190, code lost:
    
        throw new com.alibaba.fastjson.JSONException("illega str");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Collection<String> scanFieldStringArray(char[] fieldName, Class<?> type) {
        int offset;
        char chLocal;
        int offset2;
        char chLocal2;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        Collection<String> list = newCollectionByType(type);
        int offset3 = fieldName.length;
        int offset4 = offset3 + 1;
        char chLocal3 = charAt(this.bp + offset3);
        int i = -1;
        if (chLocal3 == '[') {
            int offset5 = offset4 + 1;
            char chLocal4 = charAt(this.bp + offset4);
            while (true) {
                if (chLocal4 == '\"') {
                    int startIndex = this.bp + offset5;
                    int endIndex = indexOf(Typography.quote, startIndex);
                    if (endIndex == i) {
                        throw new JSONException("unclosed str");
                    }
                    int startIndex2 = this.bp + offset5;
                    String stringVal = subString(startIndex2, endIndex - startIndex2);
                    if (stringVal.indexOf(92) != i) {
                        while (true) {
                            int slashCount = 0;
                            for (int i2 = endIndex - 1; i2 >= 0 && charAt(i2) == '\\'; i2--) {
                                slashCount++;
                            }
                            if (slashCount % 2 == 0) {
                                break;
                            }
                            endIndex = indexOf(Typography.quote, endIndex + 1);
                        }
                        int chars_len = endIndex - (this.bp + offset5);
                        char[] chars = sub_chars(this.bp + offset5, chars_len);
                        stringVal = readString(chars, chars_len);
                    }
                    int offset6 = offset5 + (endIndex - (this.bp + offset5)) + 1;
                    offset2 = offset6 + 1;
                    chLocal2 = charAt(this.bp + offset6);
                    list.add(stringVal);
                } else {
                    if (chLocal4 != 'n' || charAt(this.bp + offset5) != 'u' || charAt(this.bp + offset5 + 1) != 'l' || charAt(this.bp + offset5 + 2) != 'l') {
                        break;
                    }
                    int offset7 = offset5 + 3;
                    offset2 = offset7 + 1;
                    chLocal2 = charAt(this.bp + offset7);
                    list.add(null);
                }
                if (chLocal2 == ',') {
                    offset5 = offset2 + 1;
                    chLocal4 = charAt(this.bp + offset2);
                    i = -1;
                } else if (chLocal2 == ']') {
                    offset = offset2 + 1;
                    chLocal = charAt(this.bp + offset2);
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            if (chLocal == ',') {
                this.bp += offset;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                return list;
            }
            if (chLocal == '}') {
                int offset8 = offset + 1;
                char chLocal5 = charAt(this.bp + offset);
                if (chLocal5 == ',') {
                    this.token = 16;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal5 == ']') {
                    this.token = 15;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal5 == '}') {
                    this.token = 13;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal5 == 26) {
                    this.bp += offset8 - 1;
                    this.token = 20;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return list;
            }
            this.matchStat = -1;
            return null;
        }
        this.matchStat = -1;
        return null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void scanStringArray(Collection<String> list, char seperator) {
        char chLocal;
        int startIndex;
        int offset;
        char chLocal2;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal3 = charAt(this.bp + 0);
        char c = 'u';
        char c2 = 'l';
        char c3 = 'n';
        if (chLocal3 == 'n' && charAt(this.bp + offset2) == 'u' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 'l' && charAt(this.bp + offset2 + 3) == seperator) {
            this.bp += 5;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            return;
        }
        if (chLocal3 == '[') {
            int offset3 = offset2 + 1;
            char chLocal4 = charAt(this.bp + offset2);
            while (true) {
                if (chLocal4 == c3 && charAt(this.bp + offset3) == c && charAt(this.bp + offset3 + 1) == c2 && charAt(this.bp + offset3 + 2) == c2) {
                    int offset4 = offset3 + 3;
                    startIndex = offset4 + 1;
                    chLocal = charAt(this.bp + offset4);
                    list.add(null);
                } else {
                    if (chLocal4 == ']' && list.size() == 0) {
                        offset = offset3 + 1;
                        chLocal2 = charAt(this.bp + offset3);
                        break;
                    }
                    if (chLocal4 != '\"') {
                        this.matchStat = -1;
                        return;
                    }
                    int startIndex2 = this.bp + offset3;
                    int endIndex = indexOf(Typography.quote, startIndex2);
                    if (endIndex != -1) {
                        String stringVal = subString(this.bp + offset3, endIndex - startIndex2);
                        if (stringVal.indexOf(92) != -1) {
                            while (true) {
                                int slashCount = 0;
                                for (int i = endIndex - 1; i >= 0 && charAt(i) == '\\'; i--) {
                                    slashCount++;
                                }
                                int i2 = slashCount % 2;
                                if (i2 == 0) {
                                    break;
                                } else {
                                    endIndex = indexOf(Typography.quote, endIndex + 1);
                                }
                            }
                            int slashCount2 = endIndex - startIndex2;
                            char[] chars = sub_chars(this.bp + offset3, slashCount2);
                            stringVal = readString(chars, slashCount2);
                        }
                        int offset5 = offset3 + (endIndex - (this.bp + offset3)) + 1;
                        chLocal = charAt(this.bp + offset5);
                        list.add(stringVal);
                        startIndex = offset5 + 1;
                    } else {
                        throw new JSONException("unclosed str");
                    }
                }
                if (chLocal == ',') {
                    offset3 = startIndex + 1;
                    chLocal4 = charAt(this.bp + startIndex);
                    c = 'u';
                    c2 = 'l';
                    c3 = 'n';
                } else if (chLocal == ']') {
                    offset = startIndex + 1;
                    chLocal2 = charAt(this.bp + startIndex);
                } else {
                    this.matchStat = -1;
                    return;
                }
            }
            if (chLocal2 == seperator) {
                this.bp += offset;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                return;
            }
            this.matchStat = -1;
            return;
        }
        this.matchStat = -1;
    }

    public int scanFieldInt(char[] fieldName) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(this.bp + offset3);
            offset3++;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            int value = chLocal2 - '0';
            while (true) {
                offset = offset3 + 1;
                chLocal = charAt(this.bp + offset3);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                value = (value * 10) + (chLocal - '0');
                offset3 = offset;
            }
            if (chLocal == '.') {
                this.matchStat = -1;
                return 0;
            }
            if ((value < 0 || offset > fieldName.length + 14) && !(value == Integer.MIN_VALUE && offset == 17 && negative)) {
                this.matchStat = -1;
                return 0;
            }
            if (chLocal == ',') {
                this.bp += offset;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return negative ? -value : value;
            }
            if (chLocal == '}') {
                int offset4 = offset + 1;
                char chLocal3 = charAt(this.bp + offset);
                if (chLocal3 == ',') {
                    this.token = 16;
                    this.bp += offset4;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == ']') {
                    this.token = 15;
                    this.bp += offset4;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == '}') {
                    this.token = 13;
                    this.bp += offset4;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == 26) {
                    this.token = 20;
                    this.bp += offset4 - 1;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
                this.matchStat = 4;
                return negative ? -value : value;
            }
            this.matchStat = -1;
            return 0;
        }
        this.matchStat = -1;
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x0127, code lost:
    
        r18.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x012b, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int[] scanFieldIntArray(char[] fieldName) {
        int offset;
        int offset2;
        char chLocal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset3 = fieldName.length;
        int offset4 = offset3 + 1;
        if (charAt(this.bp + offset3) == '[') {
            int offset5 = offset4 + 1;
            char chLocal2 = charAt(this.bp + offset4);
            int[] array = new int[16];
            int arrayIndex = 0;
            if (chLocal2 == ']') {
                offset2 = offset5 + 1;
                chLocal = charAt(this.bp + offset5);
            } else {
                while (true) {
                    boolean nagative = false;
                    if (chLocal2 == '-') {
                        chLocal2 = charAt(this.bp + offset5);
                        nagative = true;
                        offset5++;
                    }
                    if (chLocal2 < '0' || chLocal2 > '9') {
                        break;
                    }
                    int value = chLocal2 - '0';
                    while (true) {
                        offset = offset5 + 1;
                        chLocal2 = charAt(this.bp + offset5);
                        if (chLocal2 < '0' || chLocal2 > '9') {
                            break;
                        }
                        value = (value * 10) + (chLocal2 - '0');
                        offset5 = offset;
                    }
                    if (arrayIndex >= array.length) {
                        int[] tmp = new int[(array.length * 3) / 2];
                        System.arraycopy(array, 0, tmp, 0, arrayIndex);
                        array = tmp;
                    }
                    int arrayIndex2 = arrayIndex + 1;
                    array[arrayIndex] = nagative ? -value : value;
                    if (chLocal2 == ',') {
                        chLocal2 = charAt(this.bp + offset);
                        offset++;
                    } else if (chLocal2 == ']') {
                        offset2 = offset + 1;
                        chLocal = charAt(this.bp + offset);
                        arrayIndex = arrayIndex2;
                        break;
                    }
                    arrayIndex = arrayIndex2;
                    offset5 = offset;
                }
            }
            int value2 = array.length;
            if (arrayIndex != value2) {
                int[] tmp2 = new int[arrayIndex];
                System.arraycopy(array, 0, tmp2, 0, arrayIndex);
                array = tmp2;
            }
            if (chLocal == ',') {
                this.bp += offset2 - 1;
                next();
                this.matchStat = 3;
                this.token = 16;
                return array;
            }
            if (chLocal == '}') {
                int offset6 = offset2 + 1;
                char chLocal3 = charAt(this.bp + offset2);
                if (chLocal3 == ',') {
                    this.token = 16;
                    this.bp += offset6 - 1;
                    next();
                } else if (chLocal3 == ']') {
                    this.token = 15;
                    this.bp += offset6 - 1;
                    next();
                } else if (chLocal3 == '}') {
                    this.token = 13;
                    this.bp += offset6 - 1;
                    next();
                } else if (chLocal3 == 26) {
                    this.bp += offset6 - 1;
                    this.token = 20;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return array;
            }
            this.matchStat = -1;
            return null;
        }
        this.matchStat = -2;
        return null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public boolean scanBoolean(char expectNext) {
        this.matchStat = 0;
        int offset = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        boolean value = false;
        if (chLocal == 't') {
            if (charAt(this.bp + offset) == 'r' && charAt(this.bp + offset + 1) == 'u' && charAt(this.bp + offset + 2) == 'e') {
                int offset2 = offset + 3;
                chLocal = charAt(this.bp + offset2);
                value = true;
                offset = offset2 + 1;
            } else {
                this.matchStat = -1;
                return false;
            }
        } else if (chLocal == 'f') {
            if (charAt(this.bp + offset) == 'a' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 's' && charAt(this.bp + offset + 3) == 'e') {
                int offset3 = offset + 4;
                chLocal = charAt(this.bp + offset3);
                value = false;
                offset = offset3 + 1;
            } else {
                this.matchStat = -1;
                return false;
            }
        } else if (chLocal == '1') {
            chLocal = charAt(this.bp + offset);
            value = true;
            offset++;
        } else if (chLocal == '0') {
            chLocal = charAt(this.bp + offset);
            value = false;
            offset++;
        }
        while (chLocal != expectNext) {
            if (isWhitespace(chLocal)) {
                chLocal = charAt(this.bp + offset);
                offset++;
            } else {
                this.matchStat = -1;
                return value;
            }
        }
        this.bp += offset;
        this.ch = charAt(this.bp);
        this.matchStat = 3;
        return value;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public int scanInt(char expectNext) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            int value = chLocal2 - '0';
            while (true) {
                offset = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                value = (value * 10) + (chLocal - '0');
                offset2 = offset;
            }
            if (chLocal == '.') {
                this.matchStat = -1;
                return 0;
            }
            if (value < 0) {
                this.matchStat = -1;
                return 0;
            }
            while (chLocal != expectNext) {
                if (isWhitespace(chLocal)) {
                    chLocal = charAt(this.bp + offset);
                    offset++;
                } else {
                    this.matchStat = -1;
                    return negative ? -value : value;
                }
            }
            this.bp += offset;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return negative ? -value : value;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset2) == 'u' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 'l') {
            this.matchStat = 5;
            int offset3 = offset2 + 3;
            int offset4 = offset3 + 1;
            char chLocal3 = charAt(this.bp + offset3);
            if (quote && chLocal3 == '\"') {
                chLocal3 = charAt(this.bp + offset4);
                offset4++;
            }
            while (chLocal3 != ',') {
                if (chLocal3 == ']') {
                    this.bp += offset4;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0;
                }
                if (isWhitespace(chLocal3)) {
                    chLocal3 = charAt(this.bp + offset4);
                    offset4++;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            }
            this.bp += offset4;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0;
        }
        this.matchStat = -1;
        return 0;
    }

    public boolean scanFieldBoolean(char[] fieldName) {
        boolean value;
        int offset;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return false;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal = charAt(this.bp + offset2);
        if (chLocal == 't') {
            int offset4 = offset3 + 1;
            if (charAt(this.bp + offset3) != 'r') {
                this.matchStat = -1;
                return false;
            }
            int offset5 = offset4 + 1;
            if (charAt(this.bp + offset4) != 'u') {
                this.matchStat = -1;
                return false;
            }
            offset = offset5 + 1;
            if (charAt(this.bp + offset5) != 'e') {
                this.matchStat = -1;
                return false;
            }
            value = true;
        } else if (chLocal == 'f') {
            int offset6 = offset3 + 1;
            if (charAt(this.bp + offset3) != 'a') {
                this.matchStat = -1;
                return false;
            }
            int offset7 = offset6 + 1;
            if (charAt(this.bp + offset6) != 'l') {
                this.matchStat = -1;
                return false;
            }
            int offset8 = offset7 + 1;
            if (charAt(this.bp + offset7) != 's') {
                this.matchStat = -1;
                return false;
            }
            int offset9 = offset8 + 1;
            if (charAt(this.bp + offset8) != 'e') {
                this.matchStat = -1;
                return false;
            }
            value = false;
            offset = offset9;
        } else {
            this.matchStat = -1;
            return false;
        }
        int offset10 = offset + 1;
        char chLocal2 = charAt(this.bp + offset);
        if (chLocal2 == ',') {
            this.bp += offset10;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return value;
        }
        if (chLocal2 == '}') {
            int offset11 = offset10 + 1;
            char chLocal3 = charAt(this.bp + offset10);
            if (chLocal3 == ',') {
                this.token = 16;
                this.bp += offset11;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == ']') {
                this.token = 15;
                this.bp += offset11;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == '}') {
                this.token = 13;
                this.bp += offset11;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == 26) {
                this.token = 20;
                this.bp += offset11 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return false;
            }
            this.matchStat = 4;
            return value;
        }
        this.matchStat = -1;
        return false;
    }

    public long scanFieldLong(char[] fieldName) {
        int offset;
        char chLocal;
        boolean valid = false;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0L;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        boolean negative = false;
        if (chLocal2 == '-') {
            chLocal2 = charAt(this.bp + offset3);
            negative = true;
            offset3++;
        }
        if (chLocal2 >= '0') {
            char c = '9';
            if (chLocal2 <= '9') {
                long value = chLocal2 - '0';
                while (true) {
                    offset = offset3 + 1;
                    chLocal = charAt(this.bp + offset3);
                    if (chLocal < '0' || chLocal > c) {
                        break;
                    }
                    value = (chLocal - '0') + (10 * value);
                    offset3 = offset;
                    c = '9';
                }
                long value2 = value;
                if (chLocal == '.') {
                    this.matchStat = -1;
                    return 0L;
                }
                if (offset - fieldName.length < 21 && (value2 >= 0 || (value2 == Long.MIN_VALUE && negative))) {
                    valid = true;
                }
                if (!valid) {
                    this.matchStat = -1;
                    return 0L;
                }
                if (chLocal == ',') {
                    this.bp += offset;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return negative ? -value2 : value2;
                }
                if (chLocal == '}') {
                    int offset4 = offset + 1;
                    char chLocal3 = charAt(this.bp + offset);
                    if (chLocal3 == ',') {
                        this.token = 16;
                        this.bp += offset4;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == ']') {
                        this.token = 15;
                        this.bp += offset4;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == '}') {
                        this.token = 13;
                        this.bp += offset4;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == 26) {
                        this.token = 20;
                        this.bp += offset4 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.matchStat = 4;
                    return negative ? -value2 : value2;
                }
                this.matchStat = -1;
                return 0L;
            }
        }
        this.matchStat = -1;
        return 0L;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char expectNextChar) {
        int offset;
        char chLocal;
        boolean valid = false;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        if (chLocal2 >= '0') {
            char c = '9';
            if (chLocal2 <= '9') {
                int offset3 = offset2;
                long value = chLocal2 - '0';
                while (true) {
                    offset = offset3 + 1;
                    chLocal = charAt(this.bp + offset3);
                    if (chLocal < '0' || chLocal > c) {
                        break;
                    }
                    value = (10 * value) + (chLocal - '0');
                    offset3 = offset;
                    c = '9';
                }
                if (chLocal == '.') {
                    this.matchStat = -1;
                    return 0L;
                }
                if (value >= 0 || (value == Long.MIN_VALUE && negative)) {
                    valid = true;
                }
                if (!valid) {
                    String val = subString(this.bp, offset - 1);
                    throw new NumberFormatException(val);
                }
                if (quote) {
                    if (chLocal == '\"') {
                        chLocal = charAt(this.bp + offset);
                        offset++;
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                }
                while (chLocal != expectNextChar) {
                    if (isWhitespace(chLocal)) {
                        chLocal = charAt(this.bp + offset);
                        offset++;
                    } else {
                        this.matchStat = -1;
                        return value;
                    }
                }
                this.bp += offset;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return negative ? -value : value;
            }
        }
        int offset4 = offset2;
        if (chLocal2 == 'n' && charAt(this.bp + offset4) == 'u' && charAt(this.bp + offset4 + 1) == 'l' && charAt(this.bp + offset4 + 2) == 'l') {
            this.matchStat = 5;
            int offset5 = offset4 + 3;
            int offset6 = offset5 + 1;
            char chLocal3 = charAt(this.bp + offset5);
            if (quote && chLocal3 == '\"') {
                chLocal3 = charAt(this.bp + offset6);
                offset6++;
            }
            while (chLocal3 != ',') {
                if (chLocal3 == ']') {
                    this.bp += offset6;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0L;
                }
                if (isWhitespace(chLocal3)) {
                    chLocal3 = charAt(this.bp + offset6);
                    offset6++;
                } else {
                    this.matchStat = -1;
                    return 0L;
                }
            }
            this.bp += offset6;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0L;
        }
        this.matchStat = -1;
        return 0L;
    }

    public final float scanFieldFloat(char[] fieldName) throws NumberFormatException {
        int offset;
        char chLocal;
        boolean negative;
        int start;
        int count;
        char chLocal2;
        float value;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0.0f;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal3 = charAt(this.bp + offset2);
        boolean quote = chLocal3 == '\"';
        if (quote) {
            chLocal3 = charAt(this.bp + offset3);
            offset3++;
        }
        boolean negative2 = chLocal3 == '-';
        if (negative2) {
            chLocal3 = charAt(this.bp + offset3);
            offset3++;
        }
        if (chLocal3 >= '0') {
            char c = '9';
            if (chLocal3 <= '9') {
                long intVal = chLocal3 - '0';
                while (true) {
                    offset = offset3 + 1;
                    chLocal = charAt(this.bp + offset3);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    intVal = (10 * intVal) + (chLocal - '0');
                    offset3 = offset;
                }
                long power = 1;
                boolean small = chLocal == '.';
                if (small) {
                    int offset4 = offset + 1;
                    char chLocal4 = charAt(this.bp + offset);
                    if (chLocal4 >= '0' && chLocal4 <= '9') {
                        negative = negative2;
                        power = 10;
                        intVal = (intVal * 10) + (chLocal4 - '0');
                        while (true) {
                            offset = offset4 + 1;
                            chLocal = charAt(this.bp + offset4);
                            if (chLocal < '0' || chLocal > c) {
                                break;
                            }
                            intVal = (intVal * 10) + (chLocal - '0');
                            power *= 10;
                            offset4 = offset;
                            small = small;
                            c = '9';
                        }
                    } else {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                } else {
                    negative = negative2;
                }
                boolean exp = chLocal == 'e' || chLocal == 'E';
                if (exp) {
                    int offset5 = offset + 1;
                    chLocal = charAt(this.bp + offset);
                    if (chLocal == '+' || chLocal == '-') {
                        chLocal = charAt(this.bp + offset5);
                        offset = offset5 + 1;
                    } else {
                        offset = offset5;
                    }
                    while (chLocal >= '0' && chLocal <= '9') {
                        chLocal = charAt(this.bp + offset);
                        offset++;
                    }
                }
                if (quote) {
                    if (chLocal == '\"') {
                        int offset6 = offset + 1;
                        chLocal = charAt(this.bp + offset);
                        start = this.bp + fieldName.length + 1;
                        count = ((this.bp + offset6) - start) - 2;
                        offset = offset6;
                    } else {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                } else {
                    start = this.bp + fieldName.length;
                    count = ((this.bp + offset) - start) - 1;
                }
                if (exp || count >= 17) {
                    chLocal2 = chLocal;
                    String text = subString(start, count);
                    value = Float.parseFloat(text);
                } else {
                    double d = intVal;
                    chLocal2 = chLocal;
                    double d2 = power;
                    Double.isNaN(d);
                    Double.isNaN(d2);
                    value = (float) (d / d2);
                    if (negative) {
                        value = -value;
                    }
                }
                char chLocal5 = chLocal2;
                if (chLocal5 == ',') {
                    this.bp += offset;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                }
                if (chLocal5 == '}') {
                    int offset7 = offset + 1;
                    char chLocal6 = charAt(this.bp + offset);
                    if (chLocal6 == ',') {
                        this.token = 16;
                        this.bp += offset7;
                        this.ch = charAt(this.bp);
                    } else if (chLocal6 == ']') {
                        this.token = 15;
                        this.bp += offset7;
                        this.ch = charAt(this.bp);
                    } else if (chLocal6 == '}') {
                        this.token = 13;
                        this.bp += offset7;
                        this.ch = charAt(this.bp);
                    } else if (chLocal6 == 26) {
                        this.bp += offset7 - 1;
                        this.token = 20;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                    this.matchStat = 4;
                    return value;
                }
                this.matchStat = -1;
                return 0.0f;
            }
        }
        if (chLocal3 != 'n' || charAt(this.bp + offset3) != 'u' || charAt(this.bp + offset3 + 1) != 'l' || charAt(this.bp + offset3 + 2) != 'l') {
            this.matchStat = -1;
            return 0.0f;
        }
        this.matchStat = 5;
        int offset8 = offset3 + 3;
        int offset9 = offset8 + 1;
        char chLocal7 = charAt(this.bp + offset8);
        if (quote && chLocal7 == '\"') {
            chLocal7 = charAt(this.bp + offset9);
            offset9++;
        }
        while (chLocal7 != ',') {
            if (chLocal7 == '}') {
                this.bp += offset9;
                this.ch = charAt(this.bp);
                this.matchStat = 5;
                this.token = 13;
                return 0.0f;
            }
            if (isWhitespace(chLocal7)) {
                chLocal7 = charAt(this.bp + offset9);
                offset9++;
            } else {
                this.matchStat = -1;
                return 0.0f;
            }
        }
        this.bp += offset9;
        this.ch = charAt(this.bp);
        this.matchStat = 5;
        this.token = 16;
        return 0.0f;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final float scanFloat(char seperator) throws NumberFormatException {
        int offset;
        char chLocal;
        boolean quote;
        int start;
        int count;
        float value;
        char chLocal2;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal3 = charAt(this.bp + 0);
        boolean quote2 = chLocal3 == '\"';
        if (quote2) {
            chLocal3 = charAt(this.bp + offset2);
            offset2++;
        }
        boolean negative = chLocal3 == '-';
        if (negative) {
            chLocal3 = charAt(this.bp + offset2);
            offset2++;
        }
        if (chLocal3 >= '0') {
            char c = '9';
            if (chLocal3 <= '9') {
                long intVal = chLocal3 - '0';
                while (true) {
                    offset = offset2 + 1;
                    chLocal = charAt(this.bp + offset2);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    intVal = (10 * intVal) + (chLocal - '0');
                    offset2 = offset;
                }
                long power = 1;
                boolean small = chLocal == '.';
                if (small) {
                    int offset3 = offset + 1;
                    char chLocal4 = charAt(this.bp + offset);
                    if (chLocal4 >= '0' && chLocal4 <= '9') {
                        quote = quote2;
                        power = 10;
                        intVal = (intVal * 10) + (chLocal4 - '0');
                        while (true) {
                            offset = offset3 + 1;
                            chLocal2 = charAt(this.bp + offset3);
                            if (chLocal2 < '0' || chLocal2 > c) {
                                break;
                            }
                            intVal = (intVal * 10) + (chLocal2 - '0');
                            power *= 10;
                            offset3 = offset;
                            small = small;
                            c = '9';
                        }
                        chLocal = chLocal2;
                    } else {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                } else {
                    quote = quote2;
                }
                boolean exp = chLocal == 'e' || chLocal == 'E';
                if (exp) {
                    int offset4 = offset + 1;
                    chLocal = charAt(this.bp + offset);
                    if (chLocal == '+' || chLocal == '-') {
                        chLocal = charAt(this.bp + offset4);
                        offset = offset4 + 1;
                    } else {
                        offset = offset4;
                    }
                    while (chLocal >= '0' && chLocal <= '9') {
                        chLocal = charAt(this.bp + offset);
                        offset++;
                    }
                }
                if (quote) {
                    if (chLocal == '\"') {
                        int offset5 = offset + 1;
                        chLocal = charAt(this.bp + offset);
                        start = this.bp + 1;
                        count = ((this.bp + offset5) - start) - 2;
                        offset = offset5;
                    } else {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                } else {
                    start = this.bp;
                    count = ((this.bp + offset) - start) - 1;
                }
                if (!exp && count < 17) {
                    double d = intVal;
                    double d2 = power;
                    Double.isNaN(d);
                    Double.isNaN(d2);
                    value = (float) (d / d2);
                    if (negative) {
                        value = -value;
                    }
                } else {
                    String text = subString(start, count);
                    value = Float.parseFloat(text);
                }
                if (chLocal == seperator) {
                    this.bp += offset;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                }
                this.matchStat = -1;
                return value;
            }
        }
        boolean quote3 = quote2;
        if (chLocal3 == 'n' && charAt(this.bp + offset2) == 'u' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 'l') {
            this.matchStat = 5;
            int offset6 = offset2 + 3;
            int offset7 = offset6 + 1;
            char chLocal5 = charAt(this.bp + offset6);
            if (quote3 && chLocal5 == '\"') {
                chLocal5 = charAt(this.bp + offset7);
                offset7++;
            }
            while (chLocal5 != ',') {
                if (chLocal5 == ']') {
                    this.bp += offset7;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0.0f;
                }
                if (isWhitespace(chLocal5)) {
                    chLocal5 = charAt(this.bp + offset7);
                    offset7++;
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            this.bp += offset7;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0f;
        }
        this.matchStat = -1;
        return 0.0f;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public double scanDouble(char seperator) throws NumberFormatException {
        int offset;
        char chLocal;
        boolean negative;
        int start;
        int count;
        double value;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        boolean negative2 = chLocal2 == '-';
        if (negative2) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        char c = '0';
        if (chLocal2 >= '0') {
            char c2 = '9';
            if (chLocal2 <= '9') {
                long intVal = chLocal2 - '0';
                while (true) {
                    offset = offset2 + 1;
                    chLocal = charAt(this.bp + offset2);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    intVal = (10 * intVal) + (chLocal - '0');
                    offset2 = offset;
                }
                long power = 1;
                boolean small = chLocal == '.';
                if (small) {
                    int offset3 = offset + 1;
                    char chLocal3 = charAt(this.bp + offset);
                    if (chLocal3 >= '0' && chLocal3 <= '9') {
                        negative = negative2;
                        power = 10;
                        intVal = (intVal * 10) + (chLocal3 - '0');
                        while (true) {
                            offset = offset3 + 1;
                            chLocal = charAt(this.bp + offset3);
                            if (chLocal < c || chLocal > c2) {
                                break;
                            }
                            intVal = (intVal * 10) + (chLocal - '0');
                            power *= 10;
                            offset3 = offset;
                            c = '0';
                            c2 = '9';
                        }
                    } else {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                } else {
                    negative = negative2;
                }
                boolean exp = chLocal == 'e' || chLocal == 'E';
                if (exp) {
                    int offset4 = offset + 1;
                    chLocal = charAt(this.bp + offset);
                    if (chLocal == '+' || chLocal == '-') {
                        chLocal = charAt(this.bp + offset4);
                        offset = offset4 + 1;
                    } else {
                        offset = offset4;
                    }
                    while (chLocal >= '0' && chLocal <= '9') {
                        chLocal = charAt(this.bp + offset);
                        offset++;
                    }
                }
                if (quote) {
                    if (chLocal == '\"') {
                        int offset5 = offset + 1;
                        chLocal = charAt(this.bp + offset);
                        start = this.bp + 1;
                        count = ((this.bp + offset5) - start) - 2;
                        offset = offset5;
                    } else {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                } else {
                    start = this.bp;
                    count = ((this.bp + offset) - start) - 1;
                }
                if (!exp && count < 17) {
                    double d = intVal;
                    double d2 = power;
                    Double.isNaN(d);
                    Double.isNaN(d2);
                    value = d / d2;
                    if (negative) {
                        value = -value;
                    }
                } else {
                    String text = subString(start, count);
                    value = Double.parseDouble(text);
                }
                if (chLocal == seperator) {
                    this.bp += offset;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                }
                this.matchStat = -1;
                return value;
            }
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset2) == 'u' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 'l') {
            this.matchStat = 5;
            int offset6 = offset2 + 3;
            int offset7 = offset6 + 1;
            char chLocal4 = charAt(this.bp + offset6);
            if (quote && chLocal4 == '\"') {
                chLocal4 = charAt(this.bp + offset7);
                offset7++;
            }
            while (chLocal4 != ',') {
                if (chLocal4 == ']') {
                    this.bp += offset7;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0.0d;
                }
                if (isWhitespace(chLocal4)) {
                    chLocal4 = charAt(this.bp + offset7);
                    offset7++;
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
            this.bp += offset7;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0d;
        }
        this.matchStat = -1;
        return 0.0d;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public BigDecimal scanDecimal(char seperator) {
        int offset;
        char chLocal;
        int start;
        int count;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            while (true) {
                offset = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                offset2 = offset;
            }
            boolean small = chLocal == '.';
            if (small) {
                int offset3 = offset + 1;
                char chLocal3 = charAt(this.bp + offset);
                if (chLocal3 < '0' || chLocal3 > '9') {
                    this.matchStat = -1;
                    return null;
                }
                while (true) {
                    offset = offset3 + 1;
                    chLocal = charAt(this.bp + offset3);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    offset3 = offset;
                }
            }
            boolean exp = chLocal == 'e' || chLocal == 'E';
            if (exp) {
                int offset4 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal == '+' || chLocal == '-') {
                    chLocal = charAt(this.bp + offset4);
                    offset = offset4 + 1;
                } else {
                    offset = offset4;
                }
                while (chLocal >= '0' && chLocal <= '9') {
                    chLocal = charAt(this.bp + offset);
                    offset++;
                }
            }
            if (quote) {
                if (chLocal == '\"') {
                    int offset5 = offset + 1;
                    chLocal = charAt(this.bp + offset);
                    start = this.bp + 1;
                    count = ((this.bp + offset5) - start) - 2;
                    offset = offset5;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            } else {
                start = this.bp;
                count = ((this.bp + offset) - start) - 1;
            }
            if (count > 65535) {
                throw new JSONException("decimal overflow");
            }
            char[] chars = sub_chars(start, count);
            BigDecimal value = new BigDecimal(chars, 0, chars.length, MathContext.UNLIMITED);
            if (chLocal == ',') {
                this.bp += offset;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return value;
            }
            if (chLocal == ']') {
                int offset6 = offset + 1;
                char chLocal4 = charAt(this.bp + offset);
                if (chLocal4 == ',') {
                    this.token = 16;
                    this.bp += offset6;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == ']') {
                    this.token = 15;
                    this.bp += offset6;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == '}') {
                    this.token = 13;
                    this.bp += offset6;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == 26) {
                    this.token = 20;
                    this.bp += offset6 - 1;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return value;
            }
            this.matchStat = -1;
            return null;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset2) == 'u' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 'l') {
            this.matchStat = 5;
            int offset7 = offset2 + 3;
            int offset8 = offset7 + 1;
            char chLocal5 = charAt(this.bp + offset7);
            if (quote && chLocal5 == '\"') {
                chLocal5 = charAt(this.bp + offset8);
                offset8++;
            }
            while (chLocal5 != ',') {
                if (chLocal5 == '}') {
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                }
                if (isWhitespace(chLocal5)) {
                    chLocal5 = charAt(this.bp + offset8);
                    offset8++;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            this.bp += offset8;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        }
        this.matchStat = -1;
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x01cf, code lost:
    
        r21.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01d3, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ac, code lost:
    
        r21.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ae, code lost:
    
        return r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float[] scanFieldFloatArray(char[] fieldName) throws NumberFormatException {
        int offset;
        int intVal;
        float value;
        char chLocal;
        this.matchStat = 0;
        float[] fArr = null;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        if (chLocal2 == '[') {
            int offset4 = offset3 + 1;
            char chLocal3 = charAt(this.bp + offset3);
            float[] array = new float[16];
            int arrayIndex = 0;
            while (true) {
                int start = (this.bp + offset4) - 1;
                boolean negative = chLocal3 == '-';
                if (negative) {
                    chLocal3 = charAt(this.bp + offset4);
                    offset4++;
                }
                if (chLocal3 < '0' || chLocal3 > '9') {
                    break;
                }
                int intVal2 = chLocal3 - '0';
                while (true) {
                    offset = offset4 + 1;
                    chLocal3 = charAt(this.bp + offset4);
                    if (chLocal3 < '0' || chLocal3 > '9') {
                        break;
                    }
                    intVal2 = (intVal2 * 10) + (chLocal3 - '0');
                    offset4 = offset;
                }
                int power = 1;
                boolean small = chLocal3 == '.';
                if (small) {
                    int offset5 = offset + 1;
                    char chLocal4 = charAt(this.bp + offset);
                    if (chLocal4 < '0' || chLocal4 > '9') {
                        break;
                    }
                    int intVal3 = (intVal2 * 10) + (chLocal4 - '0');
                    power = 10;
                    while (true) {
                        int power2 = this.bp;
                        offset = offset5 + 1;
                        chLocal = charAt(power2 + offset5);
                        if (chLocal < '0' || chLocal > '9') {
                            break;
                        }
                        intVal3 = (intVal3 * 10) + (chLocal - '0');
                        power *= 10;
                        offset5 = offset;
                    }
                    chLocal3 = chLocal;
                    intVal = intVal3;
                } else {
                    intVal = intVal2;
                }
                boolean exp = chLocal3 == 'e' || chLocal3 == 'E';
                if (exp) {
                    int offset6 = offset + 1;
                    chLocal3 = charAt(this.bp + offset);
                    if (chLocal3 == '+' || chLocal3 == '-') {
                        chLocal3 = charAt(this.bp + offset6);
                        offset = offset6 + 1;
                    } else {
                        offset = offset6;
                    }
                    while (chLocal3 >= '0' && chLocal3 <= '9') {
                        chLocal3 = charAt(this.bp + offset);
                        offset++;
                    }
                }
                int count = ((this.bp + offset) - start) - 1;
                if (!exp && count < 10) {
                    value = intVal / power;
                    if (negative) {
                        value = -value;
                    }
                } else {
                    String text = subString(start, count);
                    value = Float.parseFloat(text);
                }
                if (arrayIndex >= array.length) {
                    float[] tmp = new float[(array.length * 3) / 2];
                    System.arraycopy(array, 0, tmp, 0, arrayIndex);
                    array = tmp;
                }
                int arrayIndex2 = arrayIndex + 1;
                array[arrayIndex] = value;
                if (chLocal3 == ',') {
                    chLocal3 = charAt(this.bp + offset);
                    offset4 = offset + 1;
                } else {
                    if (chLocal3 == ']') {
                        int offset7 = offset + 1;
                        char chLocal5 = charAt(this.bp + offset);
                        int intVal4 = array.length;
                        if (arrayIndex2 != intVal4) {
                            float[] tmp2 = new float[arrayIndex2];
                            System.arraycopy(array, 0, tmp2, 0, arrayIndex2);
                            array = tmp2;
                        }
                        if (chLocal5 == ',') {
                            this.bp += offset7 - 1;
                            next();
                            this.matchStat = 3;
                            this.token = 16;
                            return array;
                        }
                        if (chLocal5 == '}') {
                            int offset8 = offset7 + 1;
                            char chLocal6 = charAt(this.bp + offset7);
                            if (chLocal6 == ',') {
                                this.token = 16;
                                this.bp += offset8 - 1;
                                next();
                            } else if (chLocal6 == ']') {
                                this.token = 15;
                                this.bp += offset8 - 1;
                                next();
                            } else if (chLocal6 == '}') {
                                this.token = 13;
                                this.bp += offset8 - 1;
                                next();
                            } else if (chLocal6 == 26) {
                                this.bp += offset8 - 1;
                                this.token = 20;
                                this.ch = JSONLexer.EOI;
                            } else {
                                this.matchStat = -1;
                                return null;
                            }
                            this.matchStat = 4;
                            return array;
                        }
                        this.matchStat = -1;
                        return null;
                    }
                    offset4 = offset;
                }
                arrayIndex = arrayIndex2;
                fArr = null;
            }
        } else {
            this.matchStat = -2;
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00be, code lost:
    
        r23.matchStat = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00c3, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0149, code lost:
    
        r14 = r20 + 1;
        r3 = charAt(r23.bp + r20);
        r1 = r8.length;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0155, code lost:
    
        if (r12 == r1) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0157, code lost:
    
        r1 = new float[r12];
        java.lang.System.arraycopy(r8, 0, r1, 0, r12);
        r8 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x015f, code lost:
    
        if (r9 < r7.length) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0161, code lost:
    
        r1 = new float[(r7.length * 3) / 2][];
        java.lang.System.arraycopy(r8, 0, r1, 0, r12);
        r7 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x016e, code lost:
    
        r1 = r9 + 1;
        r7[r9] = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0174, code lost:
    
        if (r3 != ',') goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0176, code lost:
    
        r3 = charAt(r23.bp + r14);
        r8 = r14 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0183, code lost:
    
        if (r3 != ']') goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0185, code lost:
    
        r3 = charAt(r23.bp + r14);
        r9 = r1;
        r8 = r14 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0191, code lost:
    
        r8 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01a8, code lost:
    
        r23.matchStat = -1;
        r4 = (float[][]) null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01af, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float[][] scanFieldFloatArray2(char[] fieldName) throws NumberFormatException {
        int offset;
        int intVal;
        float value;
        int arrayarrayIndex;
        this.matchStat = 0;
        float[][] fArr = null;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal = charAt(this.bp + offset2);
        char c = '[';
        if (chLocal == '[') {
            int offset4 = offset3 + 1;
            char chLocal2 = charAt(this.bp + offset3);
            int i = 16;
            float[][] arrayarray = new float[16][];
            int arrayarrayIndex2 = 0;
            loop0: while (true) {
                int arrayIndex = -1;
                if (chLocal2 != c) {
                    break;
                }
                int offset5 = offset4 + 1;
                char chLocal3 = charAt(this.bp + offset4);
                float[] array = new float[i];
                int arrayIndex2 = 0;
                while (true) {
                    int start = (this.bp + offset5) - 1;
                    boolean negative = chLocal3 == '-';
                    if (negative) {
                        chLocal3 = charAt(this.bp + offset5);
                        offset5++;
                    }
                    if (chLocal3 < '0' || chLocal3 > '9') {
                        break loop0;
                    }
                    int intVal2 = chLocal3 - '0';
                    while (true) {
                        offset = offset5 + 1;
                        chLocal3 = charAt(this.bp + offset5);
                        if (chLocal3 < '0' || chLocal3 > '9') {
                            break;
                        }
                        intVal2 = (intVal2 * 10) + (chLocal3 - '0');
                        offset5 = offset;
                    }
                    int power = 1;
                    if (chLocal3 == '.') {
                        int offset6 = offset + 1;
                        char chLocal4 = charAt(this.bp + offset);
                        if (chLocal4 < '0' || chLocal4 > '9') {
                            break loop0;
                        }
                        power = 10;
                        int intVal3 = (intVal2 * 10) + (chLocal4 - '0');
                        while (true) {
                            int intVal4 = this.bp;
                            offset = offset6 + 1;
                            chLocal3 = charAt(intVal4 + offset6);
                            if (chLocal3 < '0' || chLocal3 > '9') {
                                break;
                            }
                            intVal3 = (intVal3 * 10) + (chLocal3 - '0');
                            power *= 10;
                            offset6 = offset;
                        }
                        intVal = intVal3;
                    } else {
                        intVal = intVal2;
                    }
                    boolean exp = chLocal3 == 'e' || chLocal3 == 'E';
                    if (exp) {
                        int offset7 = offset + 1;
                        chLocal3 = charAt(this.bp + offset);
                        if (chLocal3 == '+' || chLocal3 == '-') {
                            chLocal3 = charAt(this.bp + offset7);
                            offset = offset7 + 1;
                        } else {
                            offset = offset7;
                        }
                        while (chLocal3 >= '0' && chLocal3 <= '9') {
                            chLocal3 = charAt(this.bp + offset);
                            offset++;
                        }
                    }
                    int count = ((this.bp + offset) - start) - 1;
                    if (!exp && count < 10) {
                        value = intVal / power;
                        if (negative) {
                            value = -value;
                        }
                    } else {
                        String text = subString(start, count);
                        value = Float.parseFloat(text);
                    }
                    if (arrayIndex2 >= array.length) {
                        float[] tmp = new float[(array.length * 3) / 2];
                        System.arraycopy(array, 0, tmp, 0, arrayIndex2);
                        array = tmp;
                    }
                    int arrayIndex3 = arrayIndex2 + 1;
                    array[arrayIndex2] = value;
                    if (chLocal3 == ',') {
                        chLocal3 = charAt(this.bp + offset);
                        offset5 = offset + 1;
                    } else {
                        if (chLocal3 == ']') {
                            break;
                        }
                        offset5 = offset;
                    }
                    arrayIndex2 = arrayIndex3;
                    fArr = null;
                    arrayIndex = -1;
                }
                arrayarrayIndex2 = arrayarrayIndex;
                fArr = null;
                i = 16;
                c = '[';
            }
            if (arrayarrayIndex2 != arrayarray.length) {
                float[][] tmp2 = new float[arrayarrayIndex2][];
                System.arraycopy(arrayarray, 0, tmp2, 0, arrayarrayIndex2);
                arrayarray = tmp2;
            }
            if (chLocal2 == ',') {
                this.bp += offset4 - 1;
                next();
                this.matchStat = 3;
                this.token = 16;
                return arrayarray;
            }
            if (chLocal2 == '}') {
                int offset8 = offset4 + 1;
                char chLocal5 = charAt(this.bp + offset4);
                if (chLocal5 == ',') {
                    this.token = 16;
                    this.bp += offset8 - 1;
                    next();
                } else if (chLocal5 == ']') {
                    this.token = 15;
                    this.bp += offset8 - 1;
                    next();
                } else if (chLocal5 == '}') {
                    this.token = 13;
                    this.bp += offset8 - 1;
                    next();
                } else if (chLocal5 == 26) {
                    this.bp += offset8 - 1;
                    this.token = 20;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return arrayarray;
            }
            this.matchStat = -1;
            return null;
        }
        this.matchStat = -2;
        return null;
    }

    public final double scanFieldDouble(char[] fieldName) throws NumberFormatException {
        int offset;
        char chLocal;
        boolean negative;
        int start;
        int count;
        double value;
        char chLocal2;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0.0d;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal3 = charAt(this.bp + offset2);
        boolean quote = chLocal3 == '\"';
        if (quote) {
            chLocal3 = charAt(this.bp + offset3);
            offset3++;
        }
        boolean negative2 = chLocal3 == '-';
        if (negative2) {
            chLocal3 = charAt(this.bp + offset3);
            offset3++;
        }
        char c = '0';
        if (chLocal3 >= '0') {
            char c2 = '9';
            if (chLocal3 <= '9') {
                long intVal = chLocal3 - '0';
                while (true) {
                    offset = offset3 + 1;
                    chLocal = charAt(this.bp + offset3);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    intVal = (10 * intVal) + (chLocal - '0');
                    offset3 = offset;
                    quote = quote;
                }
                boolean quote2 = quote;
                long power = 1;
                boolean small = chLocal == '.';
                if (small) {
                    int offset4 = offset + 1;
                    char chLocal4 = charAt(this.bp + offset);
                    if (chLocal4 >= '0' && chLocal4 <= '9') {
                        negative = negative2;
                        power = 10;
                        intVal = (intVal * 10) + (chLocal4 - '0');
                        while (true) {
                            offset = offset4 + 1;
                            chLocal2 = charAt(this.bp + offset4);
                            if (chLocal2 < c || chLocal2 > c2) {
                                break;
                            }
                            intVal = (intVal * 10) + (chLocal2 - '0');
                            power *= 10;
                            offset4 = offset;
                            c2 = '9';
                            c = '0';
                        }
                        chLocal = chLocal2;
                    } else {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                } else {
                    negative = negative2;
                }
                boolean exp = chLocal == 'e' || chLocal == 'E';
                if (exp) {
                    int offset5 = offset + 1;
                    chLocal = charAt(this.bp + offset);
                    if (chLocal == '+' || chLocal == '-') {
                        chLocal = charAt(this.bp + offset5);
                        offset = offset5 + 1;
                    } else {
                        offset = offset5;
                    }
                    while (chLocal >= '0' && chLocal <= '9') {
                        chLocal = charAt(this.bp + offset);
                        offset++;
                    }
                }
                if (quote2) {
                    if (chLocal == '\"') {
                        int offset6 = offset + 1;
                        chLocal = charAt(this.bp + offset);
                        start = this.bp + fieldName.length + 1;
                        count = ((this.bp + offset6) - start) - 2;
                        offset = offset6;
                    } else {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                } else {
                    start = this.bp + fieldName.length;
                    count = ((this.bp + offset) - start) - 1;
                }
                if (!exp && count < 17) {
                    double d = intVal;
                    double d2 = power;
                    Double.isNaN(d);
                    Double.isNaN(d2);
                    value = d / d2;
                    if (negative) {
                        value = -value;
                    }
                } else {
                    String text = subString(start, count);
                    value = Double.parseDouble(text);
                }
                if (chLocal == ',') {
                    this.bp += offset;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                }
                if (chLocal == '}') {
                    int offset7 = offset + 1;
                    char chLocal5 = charAt(this.bp + offset);
                    if (chLocal5 == ',') {
                        this.token = 16;
                        this.bp += offset7;
                        this.ch = charAt(this.bp);
                    } else if (chLocal5 == ']') {
                        this.token = 15;
                        this.bp += offset7;
                        this.ch = charAt(this.bp);
                    } else if (chLocal5 == '}') {
                        this.token = 13;
                        this.bp += offset7;
                        this.ch = charAt(this.bp);
                    } else if (chLocal5 == 26) {
                        this.token = 20;
                        this.bp += offset7 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                    this.matchStat = 4;
                    return value;
                }
                this.matchStat = -1;
                return 0.0d;
            }
        }
        boolean quote3 = quote;
        if (chLocal3 != 'n' || charAt(this.bp + offset3) != 'u' || charAt(this.bp + offset3 + 1) != 'l' || charAt(this.bp + offset3 + 2) != 'l') {
            this.matchStat = -1;
            return 0.0d;
        }
        this.matchStat = 5;
        int offset8 = offset3 + 3;
        int offset9 = offset8 + 1;
        char chLocal6 = charAt(this.bp + offset8);
        if (quote3 && chLocal6 == '\"') {
            chLocal6 = charAt(this.bp + offset9);
            offset9++;
        }
        while (chLocal6 != ',') {
            if (chLocal6 == '}') {
                this.bp += offset9;
                this.ch = charAt(this.bp);
                this.matchStat = 5;
                this.token = 13;
                return 0.0d;
            }
            if (isWhitespace(chLocal6)) {
                chLocal6 = charAt(this.bp + offset9);
                offset9++;
            } else {
                this.matchStat = -1;
                return 0.0d;
            }
        }
        this.bp += offset9;
        this.ch = charAt(this.bp);
        this.matchStat = 5;
        this.token = 16;
        return 0.0d;
    }

    public BigDecimal scanFieldDecimal(char[] fieldName) {
        int offset;
        char chLocal;
        int start;
        int count;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset3);
            offset3++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(this.bp + offset3);
            offset3++;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            while (true) {
                offset = offset3 + 1;
                chLocal = charAt(this.bp + offset3);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                offset3 = offset;
            }
            boolean small = chLocal == '.';
            if (small) {
                int offset4 = offset + 1;
                char chLocal3 = charAt(this.bp + offset);
                if (chLocal3 < '0' || chLocal3 > '9') {
                    this.matchStat = -1;
                    return null;
                }
                while (true) {
                    offset = offset4 + 1;
                    chLocal = charAt(this.bp + offset4);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    offset4 = offset;
                }
            }
            boolean exp = chLocal == 'e' || chLocal == 'E';
            if (exp) {
                int offset5 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal == '+' || chLocal == '-') {
                    chLocal = charAt(this.bp + offset5);
                    offset = offset5 + 1;
                } else {
                    offset = offset5;
                }
                while (chLocal >= '0' && chLocal <= '9') {
                    chLocal = charAt(this.bp + offset);
                    offset++;
                }
            }
            if (quote) {
                if (chLocal == '\"') {
                    int offset6 = offset + 1;
                    chLocal = charAt(this.bp + offset);
                    start = this.bp + fieldName.length + 1;
                    count = ((this.bp + offset6) - start) - 2;
                    offset = offset6;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            } else {
                start = this.bp + fieldName.length;
                count = ((this.bp + offset) - start) - 1;
            }
            if (count > 65535) {
                throw new JSONException("scan decimal overflow");
            }
            char[] chars = sub_chars(start, count);
            BigDecimal value = new BigDecimal(chars, 0, chars.length, MathContext.UNLIMITED);
            if (chLocal == ',') {
                this.bp += offset;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return value;
            }
            if (chLocal == '}') {
                int offset7 = offset + 1;
                char chLocal4 = charAt(this.bp + offset);
                if (chLocal4 == ',') {
                    this.token = 16;
                    this.bp += offset7;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == ']') {
                    this.token = 15;
                    this.bp += offset7;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == '}') {
                    this.token = 13;
                    this.bp += offset7;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == 26) {
                    this.token = 20;
                    this.bp += offset7 - 1;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return value;
            }
            this.matchStat = -1;
            return null;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset3) == 'u' && charAt(this.bp + offset3 + 1) == 'l' && charAt(this.bp + offset3 + 2) == 'l') {
            this.matchStat = 5;
            int offset8 = offset3 + 3;
            int offset9 = offset8 + 1;
            char chLocal5 = charAt(this.bp + offset8);
            if (quote && chLocal5 == '\"') {
                chLocal5 = charAt(this.bp + offset9);
                offset9++;
            }
            while (chLocal5 != ',') {
                if (chLocal5 == '}') {
                    this.bp += offset9;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                }
                if (isWhitespace(chLocal5)) {
                    chLocal5 = charAt(this.bp + offset9);
                    offset9++;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            this.bp += offset9;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        }
        this.matchStat = -1;
        return null;
    }

    public BigInteger scanFieldBigInteger(char[] fieldName) {
        int offset;
        char chLocal;
        int start;
        int count;
        BigInteger value;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset3);
            offset3++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(this.bp + offset3);
            offset3++;
        }
        if (chLocal2 >= '0') {
            char c = '9';
            if (chLocal2 <= '9') {
                long intVal = chLocal2 - '0';
                boolean overflow = false;
                while (true) {
                    offset = offset3 + 1;
                    chLocal = charAt(this.bp + offset3);
                    if (chLocal < '0' || chLocal > c) {
                        break;
                    }
                    long temp = (10 * intVal) + (chLocal - '0');
                    if (temp < intVal) {
                        overflow = true;
                        break;
                    }
                    intVal = temp;
                    offset3 = offset;
                    c = '9';
                }
                if (quote) {
                    if (chLocal == '\"') {
                        int offset4 = offset + 1;
                        chLocal = charAt(this.bp + offset);
                        start = this.bp + fieldName.length + 1;
                        count = ((this.bp + offset4) - start) - 2;
                        offset = offset4;
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                } else {
                    start = this.bp + fieldName.length;
                    count = ((this.bp + offset) - start) - 1;
                }
                if (!overflow && (count < 20 || (negative && count < 21))) {
                    value = BigInteger.valueOf(negative ? -intVal : intVal);
                } else {
                    if (count > 65535) {
                        throw new JSONException("scanInteger overflow");
                    }
                    String strVal = subString(start, count);
                    value = new BigInteger(strVal, 10);
                }
                if (chLocal == ',') {
                    this.bp += offset;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                }
                if (chLocal == '}') {
                    int offset5 = offset + 1;
                    char chLocal3 = charAt(this.bp + offset);
                    if (chLocal3 == ',') {
                        this.token = 16;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == ']') {
                        this.token = 15;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == '}') {
                        this.token = 13;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == 26) {
                        this.token = 20;
                        this.bp += offset5 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                    this.matchStat = 4;
                    return value;
                }
                this.matchStat = -1;
                return null;
            }
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset3) == 'u' && charAt(this.bp + offset3 + 1) == 'l' && charAt(this.bp + offset3 + 2) == 'l') {
            this.matchStat = 5;
            int offset6 = offset3 + 3;
            int offset7 = offset6 + 1;
            char chLocal4 = charAt(this.bp + offset6);
            if (quote && chLocal4 == '\"') {
                chLocal4 = charAt(this.bp + offset7);
                offset7++;
            }
            while (chLocal4 != ',') {
                if (chLocal4 == '}') {
                    this.bp += offset7;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                }
                if (isWhitespace(chLocal4)) {
                    chLocal4 = charAt(this.bp + offset7);
                    offset7++;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            this.bp += offset7;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        }
        this.matchStat = -1;
        return null;
    }

    public Date scanFieldDate(char[] fieldName) {
        int offset;
        Date dateVal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal = charAt(this.bp + offset2);
        if (chLocal == '\"') {
            int startIndex = this.bp + fieldName.length + 1;
            int endIndex = indexOf(Typography.quote, startIndex);
            if (endIndex == -1) {
                throw new JSONException("unclosed str");
            }
            int startIndex2 = this.bp + fieldName.length + 1;
            String stringVal = subString(startIndex2, endIndex - startIndex2);
            if (stringVal.indexOf(92) != -1) {
                while (true) {
                    int slashCount = 0;
                    for (int i = endIndex - 1; i >= 0 && charAt(i) == '\\'; i--) {
                        slashCount++;
                    }
                    int i2 = slashCount % 2;
                    if (i2 == 0) {
                        break;
                    }
                    endIndex = indexOf(Typography.quote, endIndex + 1);
                }
                int chars_len = endIndex - ((this.bp + fieldName.length) + 1);
                char[] chars = sub_chars(this.bp + fieldName.length + 1, chars_len);
                stringVal = readString(chars, chars_len);
            }
            int offset4 = offset3 + (endIndex - ((this.bp + fieldName.length) + 1)) + 1;
            offset = offset4 + 1;
            chLocal = charAt(this.bp + offset4);
            JSONScanner dateLexer = new JSONScanner(stringVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    Calendar calendar = dateLexer.getCalendar();
                    dateVal = calendar.getTime();
                } else {
                    this.matchStat = -1;
                    return null;
                }
            } finally {
                dateLexer.close();
            }
        } else {
            char c = '9';
            if (chLocal == '-' || (chLocal >= '0' && chLocal <= '9')) {
                long millis = 0;
                boolean negative = false;
                if (chLocal == '-') {
                    chLocal = charAt(this.bp + offset3);
                    negative = true;
                    offset3++;
                }
                if (chLocal >= '0' && chLocal <= '9') {
                    millis = chLocal - '0';
                    while (true) {
                        offset = offset3 + 1;
                        chLocal = charAt(this.bp + offset3);
                        if (chLocal < '0' || chLocal > c) {
                            break;
                        }
                        millis = (10 * millis) + (chLocal - '0');
                        offset3 = offset;
                        c = '9';
                    }
                } else {
                    offset = offset3;
                }
                if (millis < 0) {
                    this.matchStat = -1;
                    return null;
                }
                if (negative) {
                    millis = -millis;
                }
                dateVal = new Date(millis);
            } else {
                this.matchStat = -1;
                return null;
            }
        }
        if (chLocal == ',') {
            this.bp += offset;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return dateVal;
        }
        if (chLocal == '}') {
            int offset5 = offset + 1;
            char chLocal2 = charAt(this.bp + offset);
            if (chLocal2 == ',') {
                this.token = 16;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == ']') {
                this.token = 15;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == '}') {
                this.token = 13;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == 26) {
                this.token = 20;
                this.bp += offset5 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return dateVal;
        }
        this.matchStat = -1;
        return null;
    }

    public Date scanDate(char seperator) {
        int offset;
        Date dateVal;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        if (chLocal == '\"') {
            int startIndex = this.bp + 1;
            int endIndex = indexOf(Typography.quote, startIndex);
            if (endIndex == -1) {
                throw new JSONException("unclosed str");
            }
            int startIndex2 = this.bp + 1;
            String stringVal = subString(startIndex2, endIndex - startIndex2);
            if (stringVal.indexOf(92) != -1) {
                while (true) {
                    int slashCount = 0;
                    for (int i = endIndex - 1; i >= 0 && charAt(i) == '\\'; i--) {
                        slashCount++;
                    }
                    int i2 = slashCount % 2;
                    if (i2 == 0) {
                        break;
                    }
                    endIndex = indexOf(Typography.quote, endIndex + 1);
                }
                int chars_len = endIndex - (this.bp + 1);
                char[] chars = sub_chars(this.bp + 1, chars_len);
                stringVal = readString(chars, chars_len);
            }
            int offset3 = offset2 + (endIndex - (this.bp + 1)) + 1;
            offset = offset3 + 1;
            chLocal = charAt(this.bp + offset3);
            JSONScanner dateLexer = new JSONScanner(stringVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    Calendar calendar = dateLexer.getCalendar();
                    dateVal = calendar.getTime();
                } else {
                    this.matchStat = -1;
                    return null;
                }
            } finally {
                dateLexer.close();
            }
        } else {
            char c = '9';
            char c2 = '0';
            if (chLocal == '-' || (chLocal >= '0' && chLocal <= '9')) {
                long millis = 0;
                boolean negative = false;
                if (chLocal == '-') {
                    chLocal = charAt(this.bp + offset2);
                    negative = true;
                    offset2++;
                }
                if (chLocal >= '0' && chLocal <= '9') {
                    millis = chLocal - '0';
                    while (true) {
                        offset = offset2 + 1;
                        chLocal = charAt(this.bp + offset2);
                        if (chLocal < c2 || chLocal > c) {
                            break;
                        }
                        millis = (10 * millis) + (chLocal - '0');
                        offset2 = offset;
                        c = '9';
                        c2 = '0';
                    }
                } else {
                    offset = offset2;
                }
                if (millis < 0) {
                    this.matchStat = -1;
                    return null;
                }
                if (negative) {
                    millis = -millis;
                }
                dateVal = new Date(millis);
            } else if (chLocal == 'n' && charAt(this.bp + offset2) == 'u' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 'l') {
                this.matchStat = 5;
                dateVal = null;
                int offset4 = offset2 + 3;
                offset = offset4 + 1;
                chLocal = charAt(this.bp + offset4);
            } else {
                this.matchStat = -1;
                return null;
            }
        }
        if (chLocal == ',') {
            this.bp += offset;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return dateVal;
        }
        if (chLocal == ']') {
            int offset5 = offset + 1;
            char chLocal2 = charAt(this.bp + offset);
            if (chLocal2 == ',') {
                this.token = 16;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == ']') {
                this.token = 15;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == '}') {
                this.token = 13;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == 26) {
                this.token = 20;
                this.bp += offset5 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return dateVal;
        }
        this.matchStat = -1;
        return null;
    }

    public UUID scanFieldUUID(char[] fieldName) {
        UUID uuid;
        int offset;
        char chLocal;
        int num;
        int num2;
        int num3;
        int num4;
        int num5;
        int num6;
        int i;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        if (chLocal2 != '\"') {
            if (chLocal2 == 'n') {
                int offset4 = offset3 + 1;
                if (charAt(this.bp + offset3) == 'u') {
                    int offset5 = offset4 + 1;
                    if (charAt(this.bp + offset4) == 'l') {
                        int offset6 = offset5 + 1;
                        if (charAt(this.bp + offset5) == 'l') {
                            uuid = null;
                            offset = offset6 + 1;
                            chLocal = charAt(this.bp + offset6);
                        }
                    }
                }
            }
            this.matchStat = -1;
            return null;
        }
        int startIndex = this.bp + fieldName.length + 1;
        int endIndex = indexOf(Typography.quote, startIndex);
        if (endIndex == -1) {
            throw new JSONException("unclosed str");
        }
        int startIndex2 = this.bp + fieldName.length + 1;
        int len = endIndex - startIndex2;
        char c = 'F';
        char c2 = 'f';
        char c3 = '9';
        char c4 = 'A';
        char c5 = 'a';
        char c6 = '0';
        if (len == 36) {
            long mostSigBits = 0;
            long leastSigBits = 0;
            int i2 = 0;
            while (i2 < 8) {
                char ch = charAt(startIndex2 + i2);
                if (ch >= '0' && ch <= '9') {
                    i = ch - '0';
                } else if (ch >= 'a' && ch <= 'f') {
                    i = (ch - 'a') + 10;
                } else {
                    if (ch < c4 || ch > c) {
                        this.matchStat = -2;
                        return null;
                    }
                    i = (ch - 'A') + 10;
                }
                int num7 = i;
                mostSigBits = (mostSigBits << 4) | num7;
                i2++;
                offset3 = offset3;
                c4 = 'A';
                c = 'F';
            }
            int offset7 = offset3;
            for (int i3 = 9; i3 < 13; i3++) {
                char ch2 = charAt(startIndex2 + i3);
                if (ch2 >= '0' && ch2 <= '9') {
                    num6 = ch2 - '0';
                } else if (ch2 >= 'a' && ch2 <= 'f') {
                    num6 = (ch2 - 'a') + 10;
                } else {
                    if (ch2 < 'A' || ch2 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num6 = (ch2 - 'A') + 10;
                }
                mostSigBits = (mostSigBits << 4) | num6;
            }
            int i4 = 14;
            long mostSigBits2 = mostSigBits;
            while (i4 < 18) {
                char ch3 = charAt(startIndex2 + i4);
                if (ch3 >= '0' && ch3 <= c3) {
                    num5 = ch3 - '0';
                } else if (ch3 >= 'a' && ch3 <= 'f') {
                    num5 = (ch3 - 'a') + 10;
                } else {
                    if (ch3 < 'A' || ch3 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num5 = (ch3 - 'A') + 10;
                }
                mostSigBits2 = (mostSigBits2 << 4) | num5;
                i4++;
                endIndex = endIndex;
                c3 = '9';
            }
            int endIndex2 = endIndex;
            int i5 = 19;
            while (i5 < 23) {
                char ch4 = charAt(startIndex2 + i5);
                if (ch4 >= '0' && ch4 <= '9') {
                    num4 = ch4 - '0';
                } else if (ch4 >= 'a' && ch4 <= c2) {
                    num4 = (ch4 - 'a') + 10;
                } else {
                    if (ch4 < 'A' || ch4 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num4 = (ch4 - 'A') + 10;
                }
                leastSigBits = (leastSigBits << 4) | num4;
                i5++;
                c2 = 'f';
            }
            long leastSigBits2 = leastSigBits;
            for (int i6 = 24; i6 < 36; i6++) {
                char ch5 = charAt(startIndex2 + i6);
                if (ch5 >= '0' && ch5 <= '9') {
                    num3 = ch5 - '0';
                } else if (ch5 >= 'a' && ch5 <= 'f') {
                    num3 = (ch5 - 'a') + 10;
                } else {
                    if (ch5 < 'A' || ch5 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num3 = (ch5 - 'A') + 10;
                }
                leastSigBits2 = (leastSigBits2 << 4) | num3;
            }
            uuid = new UUID(mostSigBits2, leastSigBits2);
            int offset8 = offset7 + (endIndex2 - ((this.bp + fieldName.length) + 1)) + 1;
            offset = offset8 + 1;
            chLocal = charAt(this.bp + offset8);
        } else {
            if (len != 32) {
                this.matchStat = -1;
                return null;
            }
            long mostSigBits3 = 0;
            long leastSigBits3 = 0;
            int i7 = 0;
            while (i7 < 16) {
                char ch6 = charAt(startIndex2 + i7);
                if (ch6 >= c6 && ch6 <= '9') {
                    num2 = ch6 - '0';
                } else if (ch6 >= c5 && ch6 <= 'f') {
                    num2 = (ch6 - 'a') + 10;
                } else {
                    if (ch6 < 'A' || ch6 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num2 = (ch6 - 'A') + 10;
                }
                mostSigBits3 = (mostSigBits3 << 4) | num2;
                i7++;
                c6 = '0';
                c5 = 'a';
            }
            int i8 = 16;
            for (int i9 = 32; i8 < i9; i9 = 32) {
                char ch7 = charAt(startIndex2 + i8);
                if (ch7 >= '0' && ch7 <= '9') {
                    num = ch7 - '0';
                } else if (ch7 >= 'a' && ch7 <= 'f') {
                    num = (ch7 - 'a') + 10;
                } else {
                    if (ch7 < 'A' || ch7 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num = (ch7 - 'A') + 10;
                }
                leastSigBits3 = (leastSigBits3 << 4) | num;
                i8++;
            }
            uuid = new UUID(mostSigBits3, leastSigBits3);
            int offset9 = offset3 + (endIndex - ((this.bp + fieldName.length) + 1)) + 1;
            offset = offset9 + 1;
            chLocal = charAt(this.bp + offset9);
        }
        if (chLocal == ',') {
            this.bp += offset;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return uuid;
        }
        if (chLocal == '}') {
            int offset10 = offset + 1;
            char chLocal3 = charAt(this.bp + offset);
            if (chLocal3 == ',') {
                this.token = 16;
                this.bp += offset10;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == ']') {
                this.token = 15;
                this.bp += offset10;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == '}') {
                this.token = 13;
                this.bp += offset10;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == 26) {
                this.token = 20;
                this.bp += offset10 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return uuid;
        }
        this.matchStat = -1;
        return null;
    }

    public UUID scanUUID(char seperator) {
        UUID uuid;
        int offset;
        char chLocal;
        int num;
        int num2;
        int num3;
        int num4;
        int num5;
        int num6;
        int i;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        char c = 4;
        if (chLocal2 != '\"') {
            if (chLocal2 == 'n') {
                int offset3 = offset2 + 1;
                if (charAt(this.bp + offset2) == 'u') {
                    int offset4 = offset3 + 1;
                    if (charAt(this.bp + offset3) == 'l') {
                        int offset5 = offset4 + 1;
                        if (charAt(this.bp + offset4) == 'l') {
                            uuid = null;
                            offset = offset5 + 1;
                            chLocal = charAt(this.bp + offset5);
                        }
                    }
                }
            }
            this.matchStat = -1;
            return null;
        }
        int startIndex = this.bp + 1;
        int endIndex = indexOf(Typography.quote, startIndex);
        if (endIndex == -1) {
            throw new JSONException("unclosed str");
        }
        int startIndex2 = this.bp + 1;
        int len = endIndex - startIndex2;
        char c2 = 'F';
        char c3 = '9';
        char c4 = 'A';
        char c5 = 'a';
        char c6 = '0';
        if (len == 36) {
            long mostSigBits = 0;
            long leastSigBits = 0;
            int i2 = 0;
            while (i2 < 8) {
                char ch = charAt(startIndex2 + i2);
                if (ch >= '0' && ch <= '9') {
                    i = ch - '0';
                } else if (ch >= c5 && ch <= 'f') {
                    i = (ch - 'a') + 10;
                } else {
                    if (ch < 'A' || ch > c2) {
                        this.matchStat = -2;
                        return null;
                    }
                    i = (ch - 'A') + 10;
                }
                int num7 = i;
                mostSigBits = (mostSigBits << 4) | num7;
                i2++;
                offset2 = offset2;
                c5 = 'a';
                c2 = 'F';
            }
            int offset6 = offset2;
            int i3 = 9;
            while (i3 < 13) {
                char ch2 = charAt(startIndex2 + i3);
                if (ch2 >= '0' && ch2 <= c3) {
                    num6 = ch2 - '0';
                } else if (ch2 >= 'a' && ch2 <= 'f') {
                    num6 = (ch2 - 'a') + 10;
                } else {
                    if (ch2 < c4 || ch2 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num6 = (ch2 - 'A') + 10;
                }
                mostSigBits = (mostSigBits << 4) | num6;
                i3++;
                c4 = 'A';
                c3 = '9';
            }
            long mostSigBits2 = mostSigBits;
            for (int i4 = 14; i4 < 18; i4++) {
                char ch3 = charAt(startIndex2 + i4);
                if (ch3 >= '0' && ch3 <= '9') {
                    num5 = ch3 - '0';
                } else if (ch3 >= 'a' && ch3 <= 'f') {
                    num5 = (ch3 - 'a') + 10;
                } else {
                    if (ch3 < 'A' || ch3 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num5 = (ch3 - 'A') + 10;
                }
                mostSigBits2 = (mostSigBits2 << 4) | num5;
            }
            int i5 = 19;
            while (i5 < 23) {
                char ch4 = charAt(startIndex2 + i5);
                if (ch4 >= c6 && ch4 <= '9') {
                    num4 = ch4 - '0';
                } else if (ch4 >= 'a' && ch4 <= 'f') {
                    num4 = (ch4 - 'a') + 10;
                } else {
                    if (ch4 < 'A' || ch4 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num4 = (ch4 - 'A') + 10;
                }
                leastSigBits = (leastSigBits << c) | num4;
                i5++;
                c = 4;
                c6 = '0';
            }
            int i6 = 24;
            long leastSigBits2 = leastSigBits;
            while (i6 < 36) {
                char ch5 = charAt(startIndex2 + i6);
                if (ch5 >= '0' && ch5 <= '9') {
                    num3 = ch5 - '0';
                } else if (ch5 >= 'a' && ch5 <= 'f') {
                    num3 = (ch5 - 'a') + 10;
                } else {
                    if (ch5 < 'A' || ch5 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num3 = (ch5 - 'A') + 10;
                }
                leastSigBits2 = (leastSigBits2 << 4) | num3;
                i6++;
                endIndex = endIndex;
            }
            uuid = new UUID(mostSigBits2, leastSigBits2);
            int offset7 = offset6 + (endIndex - (this.bp + 1)) + 1;
            offset = offset7 + 1;
            chLocal = charAt(this.bp + offset7);
        } else {
            if (len != 32) {
                this.matchStat = -1;
                return null;
            }
            long mostSigBits3 = 0;
            long leastSigBits3 = 0;
            int i7 = 0;
            while (i7 < 16) {
                char ch6 = charAt(startIndex2 + i7);
                if (ch6 >= '0' && ch6 <= '9') {
                    num2 = ch6 - '0';
                } else if (ch6 >= 'a' && ch6 <= 'f') {
                    num2 = (ch6 - 'a') + 10;
                } else {
                    if (ch6 < 'A' || ch6 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num2 = (ch6 - 'A') + 10;
                }
                long leastSigBits4 = leastSigBits3;
                long leastSigBits5 = num2;
                mostSigBits3 = (mostSigBits3 << 4) | leastSigBits5;
                i7++;
                leastSigBits3 = leastSigBits4;
            }
            long leastSigBits6 = leastSigBits3;
            int i8 = 16;
            long leastSigBits7 = leastSigBits6;
            for (int i9 = 32; i8 < i9; i9 = 32) {
                char ch7 = charAt(startIndex2 + i8);
                if (ch7 >= '0' && ch7 <= '9') {
                    num = ch7 - '0';
                } else if (ch7 >= 'a' && ch7 <= 'f') {
                    num = (ch7 - 'a') + 10;
                } else {
                    if (ch7 < 'A' || ch7 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    num = (ch7 - 'A') + 10;
                }
                leastSigBits7 = (leastSigBits7 << 4) | num;
                i8++;
            }
            uuid = new UUID(mostSigBits3, leastSigBits7);
            int offset8 = offset2 + (endIndex - (this.bp + 1)) + 1;
            offset = offset8 + 1;
            chLocal = charAt(this.bp + offset8);
        }
        if (chLocal == ',') {
            this.bp += offset;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return uuid;
        }
        if (chLocal == ']') {
            int offset9 = offset + 1;
            char chLocal3 = charAt(this.bp + offset);
            if (chLocal3 == ',') {
                this.token = 16;
                this.bp += offset9;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == ']') {
                this.token = 15;
                this.bp += offset9;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == '}') {
                this.token = 13;
                this.bp += offset9;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == 26) {
                this.token = 20;
                this.bp += offset9 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return uuid;
        }
        this.matchStat = -1;
        return null;
    }

    public final void scanTrue() {
        if (this.ch != 't') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'r') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'u') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'e') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == 26 || this.ch == '\f' || this.ch == '\b' || this.ch == ':' || this.ch == '/') {
            this.token = 6;
            return;
        }
        throw new JSONException("scan true error");
    }

    public final void scanNullOrNew() {
        scanNullOrNew(true);
    }

    public final void scanNullOrNew(boolean acceptColon) {
        if (this.ch != 'n') {
            throw new JSONException("error parse null or new");
        }
        next();
        if (this.ch == 'u') {
            next();
            if (this.ch != 'l') {
                throw new JSONException("error parse null");
            }
            next();
            if (this.ch != 'l') {
                throw new JSONException("error parse null");
            }
            next();
            if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == 26 || ((this.ch == ':' && acceptColon) || this.ch == '\f' || this.ch == '\b')) {
                this.token = 8;
                return;
            }
            throw new JSONException("scan null error");
        }
        if (this.ch != 'e') {
            throw new JSONException("error parse new");
        }
        next();
        if (this.ch != 'w') {
            throw new JSONException("error parse new");
        }
        next();
        if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == 26 || this.ch == '\f' || this.ch == '\b') {
            this.token = 9;
            return;
        }
        throw new JSONException("scan new error");
    }

    public final void scanFalse() {
        if (this.ch != 'f') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'a') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'l') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 's') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'e') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == 26 || this.ch == '\f' || this.ch == '\b' || this.ch == ':' || this.ch == '/') {
            this.token = 7;
            return;
        }
        throw new JSONException("scan false error");
    }

    public final void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            this.sp++;
            next();
        } while (Character.isLetterOrDigit(this.ch));
        String ident = stringVal();
        if ("null".equalsIgnoreCase(ident)) {
            this.token = 8;
            return;
        }
        if ("new".equals(ident)) {
            this.token = 9;
            return;
        }
        if ("true".equals(ident)) {
            this.token = 6;
            return;
        }
        if (Bugly.SDK_IS_DEV.equals(ident)) {
            this.token = 7;
            return;
        }
        if ("undefined".equals(ident)) {
            this.token = 23;
            return;
        }
        if ("Set".equals(ident)) {
            this.token = 21;
        } else if ("TreeSet".equals(ident)) {
            this.token = 22;
        } else {
            this.token = 18;
        }
    }

    public static String readString(char[] chars, int chars_len) {
        char[] sbuf = new char[chars_len];
        int len = 0;
        int i = 0;
        while (i < chars_len) {
            char ch = chars[i];
            if (ch != '\\') {
                sbuf[len] = ch;
                len++;
            } else {
                i++;
                switch (chars[i]) {
                    case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                        sbuf[len] = Typography.quote;
                        len++;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                        sbuf[len] = '\'';
                        len++;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                        sbuf[len] = '/';
                        len++;
                        break;
                    case '0':
                        sbuf[len] = 0;
                        len++;
                        break;
                    case Opcodes.V1_5 /* 49 */:
                        sbuf[len] = 1;
                        len++;
                        break;
                    case '2':
                        sbuf[len] = 2;
                        len++;
                        break;
                    case '3':
                        sbuf[len] = 3;
                        len++;
                        break;
                    case '4':
                        sbuf[len] = 4;
                        len++;
                        break;
                    case '5':
                        sbuf[len] = 5;
                        len++;
                        break;
                    case Opcodes.ISTORE /* 54 */:
                        sbuf[len] = 6;
                        len++;
                        break;
                    case Opcodes.LSTORE /* 55 */:
                        sbuf[len] = 7;
                        len++;
                        break;
                    case 'F':
                    case 'f':
                        sbuf[len] = '\f';
                        len++;
                        break;
                    case '\\':
                        sbuf[len] = '\\';
                        len++;
                        break;
                    case 'b':
                        sbuf[len] = '\b';
                        len++;
                        break;
                    case 'n':
                        sbuf[len] = '\n';
                        len++;
                        break;
                    case 'r':
                        sbuf[len] = '\r';
                        len++;
                        break;
                    case 't':
                        sbuf[len] = '\t';
                        len++;
                        break;
                    case 'u':
                        int i2 = i + 1;
                        char c = chars[i2];
                        int i3 = i2 + 1;
                        char c2 = chars[i3];
                        int i4 = i3 + 1;
                        char c3 = chars[i4];
                        i = i4 + 1;
                        sbuf[len] = (char) Integer.parseInt(new String(new char[]{c, c2, c3, chars[i]}), 16);
                        len++;
                        break;
                    case 'v':
                        sbuf[len] = 11;
                        len++;
                        break;
                    case 'x':
                        int i5 = i + 1;
                        int i6 = digits[chars[i5]] * 16;
                        i = i5 + 1;
                        sbuf[len] = (char) (i6 + digits[chars[i]]);
                        len++;
                        break;
                    default:
                        throw new JSONException("unclosed.str.lit");
                }
            }
            i++;
        }
        return new String(sbuf, 0, len);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public boolean isBlankInput() {
        int i = 0;
        while (true) {
            char chLocal = charAt(i);
            if (chLocal == 26) {
                this.token = 20;
                return true;
            }
            if (isWhitespace(chLocal)) {
                i++;
            } else {
                return false;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void skipWhitespace() {
        while (this.ch <= '/') {
            if (this.ch == ' ' || this.ch == '\r' || this.ch == '\n' || this.ch == '\t' || this.ch == '\f' || this.ch == '\b') {
                next();
            } else if (this.ch == '/') {
                skipComment();
            } else {
                return;
            }
        }
    }

    private void scanStringSingleQuote() {
        char x1;
        char x2;
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char chLocal = next();
            if (chLocal == '\'') {
                this.token = 4;
                next();
                return;
            }
            if (chLocal == 26) {
                if (!isEOF()) {
                    putChar(JSONLexer.EOI);
                } else {
                    throw new JSONException("unclosed single-quote string");
                }
            } else {
                boolean z = true;
                if (chLocal == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        if (this.sp > this.sbuf.length) {
                            char[] newsbuf = new char[this.sp * 2];
                            System.arraycopy(this.sbuf, 0, newsbuf, 0, this.sbuf.length);
                            this.sbuf = newsbuf;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char chLocal2 = next();
                    switch (chLocal2) {
                        case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                            putChar(Typography.quote);
                            break;
                        case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                            putChar('\'');
                            break;
                        case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                            putChar('/');
                            break;
                        case '0':
                            putChar((char) 0);
                            break;
                        case Opcodes.V1_5 /* 49 */:
                            putChar((char) 1);
                            break;
                        case '2':
                            putChar((char) 2);
                            break;
                        case '3':
                            putChar((char) 3);
                            break;
                        case '4':
                            putChar((char) 4);
                            break;
                        case '5':
                            putChar((char) 5);
                            break;
                        case Opcodes.ISTORE /* 54 */:
                            putChar((char) 6);
                            break;
                        case Opcodes.LSTORE /* 55 */:
                            putChar((char) 7);
                            break;
                        case 'F':
                        case 'f':
                            putChar('\f');
                            break;
                        case '\\':
                            putChar('\\');
                            break;
                        case 'b':
                            putChar('\b');
                            break;
                        case 'n':
                            putChar('\n');
                            break;
                        case 'r':
                            putChar('\r');
                            break;
                        case 't':
                            putChar('\t');
                            break;
                        case 'u':
                            putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                            break;
                        case 'v':
                            putChar((char) 11);
                            break;
                        case 'x':
                            x1 = next();
                            x2 = next();
                            boolean hex1 = (x1 >= '0' && x1 <= '9') || (x1 >= 'a' && x1 <= 'f') || (x1 >= 'A' && x1 <= 'F');
                            if ((x2 < '0' || x2 > '9') && ((x2 < 'a' || x2 > 'f') && (x2 < 'A' || x2 > 'F'))) {
                                z = false;
                            }
                            boolean hex2 = z;
                            if (!hex1 || !hex2) {
                                break;
                            } else {
                                putChar((char) ((digits[x1] * 16) + digits[x2]));
                                break;
                            }
                        default:
                            this.ch = chLocal2;
                            throw new JSONException("unclosed single-quote string");
                    }
                } else if (!this.hasSpecial) {
                    this.sp++;
                } else if (this.sp == this.sbuf.length) {
                    putChar(chLocal);
                } else {
                    char[] cArr = this.sbuf;
                    int i = this.sp;
                    this.sp = i + 1;
                    cArr[i] = chLocal;
                }
            }
        }
        throw new JSONException("invalid escape character \\x" + x1 + x2);
    }

    protected final void putChar(char ch) {
        if (this.sp >= this.sbuf.length) {
            int len = this.sbuf.length * 2;
            if (len < this.sp) {
                len = this.sp + 1;
            }
            char[] newsbuf = new char[len];
            System.arraycopy(this.sbuf, 0, newsbuf, 0, this.sbuf.length);
            this.sbuf = newsbuf;
        }
        char[] cArr = this.sbuf;
        int i = this.sp;
        this.sp = i + 1;
        cArr[i] = ch;
    }

    public final void scanHex() {
        char ch;
        if (this.ch != 'x') {
            throw new JSONException("illegal state. " + this.ch);
        }
        next();
        if (this.ch != '\'') {
            throw new JSONException("illegal state. " + this.ch);
        }
        this.np = this.bp;
        next();
        if (this.ch == '\'') {
            next();
            this.token = 26;
            return;
        }
        int i = 0;
        while (true) {
            ch = next();
            if ((ch < '0' || ch > '9') && (ch < 'A' || ch > 'F')) {
                break;
            }
            this.sp++;
            i++;
        }
        if (ch == '\'') {
            this.sp++;
            next();
            this.token = 26;
            return;
        }
        throw new JSONException("illegal state. " + ch);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void scanNumber() {
        this.np = this.bp;
        if (this.ch == '-') {
            this.sp++;
            next();
        }
        while (this.ch >= '0' && this.ch <= '9') {
            this.sp++;
            next();
        }
        boolean isDouble = false;
        if (this.ch == '.') {
            this.sp++;
            next();
            isDouble = true;
            while (this.ch >= '0' && this.ch <= '9') {
                this.sp++;
                next();
            }
        }
        if (this.sp > 65535) {
            throw new JSONException("scanNumber overflow");
        }
        if (this.ch == 'L' || this.ch == 'S' || this.ch == 'B') {
            this.sp++;
            next();
        } else if (this.ch == 'F' || this.ch == 'D') {
            this.sp++;
            next();
            isDouble = true;
        } else if (this.ch == 'e' || this.ch == 'E') {
            this.sp++;
            next();
            if (this.ch == '+' || this.ch == '-') {
                this.sp++;
                next();
            }
            while (this.ch >= '0' && this.ch <= '9') {
                this.sp++;
                next();
            }
            if (this.ch == 'D' || this.ch == 'F') {
                this.sp++;
                next();
            }
            isDouble = true;
        }
        if (isDouble) {
            this.token = 3;
        } else {
            this.token = 2;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final long longValue() throws NumberFormatException {
        long limit;
        long result = 0;
        boolean negative = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int i = this.np;
        int max = this.np + this.sp;
        if (charAt(this.np) == '-') {
            negative = true;
            limit = Long.MIN_VALUE;
            i++;
        } else {
            limit = -9223372036854775807L;
        }
        if (i < max) {
            result = -(charAt(i) - 48);
            i++;
        }
        while (i < max) {
            int i2 = i + 1;
            char chLocal = charAt(i);
            if (chLocal != 'L' && chLocal != 'S' && chLocal != 'B') {
                int digit = chLocal - '0';
                if (result < MULTMIN_RADIX_TEN) {
                    throw new NumberFormatException(numberString());
                }
                long result2 = result * 10;
                if (result2 < digit + limit) {
                    throw new NumberFormatException(numberString());
                }
                result = result2 - digit;
                i = i2;
            } else {
                i = i2;
                break;
            }
        }
        if (negative) {
            if (i > this.np + 1) {
                return result;
            }
            throw new NumberFormatException(numberString());
        }
        return -result;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number decimalValue(boolean decimal) {
        char chLocal = charAt((this.np + this.sp) - 1);
        try {
            if (chLocal == 'F') {
                return Float.valueOf(Float.parseFloat(numberString()));
            }
            if (chLocal == 'D') {
                return Double.valueOf(Double.parseDouble(numberString()));
            }
            if (decimal) {
                return decimalValue();
            }
            return Double.valueOf(doubleValue());
        } catch (NumberFormatException ex) {
            throw new JSONException(ex.getMessage() + ", " + info());
        }
    }

    public static boolean isWhitespace(char ch) {
        return ch <= ' ' && (ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t' || ch == '\f' || ch == '\b');
    }

    public String[] scanFieldStringArray(char[] fieldName, int argTypesCount, SymbolTable typeSymbolTable) {
        throw new UnsupportedOperationException();
    }

    public boolean matchField2(char[] fieldName) {
        throw new UnsupportedOperationException();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public int getFeatures() {
        return this.features;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void setFeatures(int features) {
        this.features = features;
    }
}
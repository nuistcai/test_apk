package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public final class JSONScanner extends JSONLexerBase {
    private final int len;
    private final String text;

    public JSONScanner(String input) {
        this(input, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(String input, int features) {
        super(features);
        this.text = input;
        this.len = this.text.length();
        this.bp = -1;
        next();
        if (this.ch == 65279) {
            next();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char charAt(int index) {
        if (index >= this.len) {
            return JSONLexer.EOI;
        }
        return this.text.charAt(index);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final char next() {
        int index = this.bp + 1;
        this.bp = index;
        char cCharAt = index >= this.len ? JSONLexer.EOI : this.text.charAt(index);
        this.ch = cCharAt;
        return cCharAt;
    }

    public JSONScanner(char[] input, int inputLength) {
        this(input, inputLength, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(char[] input, int inputLength, int features) {
        this(new String(input, 0, inputLength), features);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void copyTo(int offset, int count, char[] dest) {
        this.text.getChars(offset, offset + count, dest, 0);
    }

    static boolean charArrayCompare(String src, int offset, char[] dest) {
        int destLen = dest.length;
        if (destLen + offset > src.length()) {
            return false;
        }
        for (int i = 0; i < destLen; i++) {
            if (dest[i] != src.charAt(offset + i)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final boolean charArrayCompare(char[] chars) {
        return charArrayCompare(this.text, this.bp, chars);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final int indexOf(char ch, int startIndex) {
        return this.text.indexOf(ch, startIndex);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String addSymbol(int offset, int len, int hash, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.text, offset, len, hash);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public byte[] bytesValue() {
        if (this.token == 26) {
            int start = this.np + 1;
            int len = this.sp;
            if (len % 2 != 0) {
                throw new JSONException("illegal state. " + len);
            }
            byte[] bytes = new byte[len / 2];
            for (int i = 0; i < bytes.length; i++) {
                char c0 = this.text.charAt((i * 2) + start);
                char c1 = this.text.charAt((i * 2) + start + 1);
                char c = '0';
                int b0 = c0 - (c0 <= '9' ? '0' : '7');
                if (c1 > '9') {
                    c = '7';
                }
                int b1 = c1 - c;
                bytes[i] = (byte) ((b0 << 4) | b1);
            }
            return bytes;
        }
        if (!this.hasSpecial) {
            return IOUtils.decodeBase64(this.text, this.np + 1, this.sp);
        }
        String escapedText = new String(this.sbuf, 0, this.sp);
        return IOUtils.decodeBase64(escapedText);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String stringVal() {
        if (!this.hasSpecial) {
            return subString(this.np + 1, this.sp);
        }
        return new String(this.sbuf, 0, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String subString(int offset, int count) {
        if (ASMUtils.IS_ANDROID) {
            if (count < this.sbuf.length) {
                this.text.getChars(offset, offset + count, this.sbuf, 0);
                return new String(this.sbuf, 0, count);
            }
            char[] chars = new char[count];
            this.text.getChars(offset, offset + count, chars, 0);
            return new String(chars);
        }
        return this.text.substring(offset, offset + count);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char[] sub_chars(int offset, int count) {
        if (ASMUtils.IS_ANDROID && count < this.sbuf.length) {
            this.text.getChars(offset, offset + count, this.sbuf, 0);
            return this.sbuf;
        }
        char[] chars = new char[count];
        this.text.getChars(offset, offset + count, chars, 0);
        return chars;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String numberString() {
        char chLocal = charAt((this.np + this.sp) - 1);
        int sp = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp--;
        }
        return subString(this.np, sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final BigDecimal decimalValue() {
        char chLocal = charAt((this.np + this.sp) - 1);
        int sp = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp--;
        }
        if (sp > 65535) {
            throw new JSONException("decimal overflow");
        }
        int offset = this.np;
        int count = sp;
        if (count < this.sbuf.length) {
            this.text.getChars(offset, offset + count, this.sbuf, 0);
            return new BigDecimal(this.sbuf, 0, count, MathContext.UNLIMITED);
        }
        char[] chars = new char[count];
        this.text.getChars(offset, offset + count, chars, 0);
        return new BigDecimal(chars, 0, chars.length, MathContext.UNLIMITED);
    }

    public boolean scanISO8601DateIfMatch() {
        return scanISO8601DateIfMatch(true);
    }

    public boolean scanISO8601DateIfMatch(boolean strict) {
        int rest = this.len - this.bp;
        return scanISO8601DateIfMatch(strict, rest);
    }

    /* JADX WARN: Failed to apply debug info
    jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
    	at jadx.core.dex.visitors.typeinference.TypeUpdateInfo.requestUpdate(TypeUpdateInfo.java:35)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
     */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0720  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x072b  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x07b7 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:377:0x07b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean scanISO8601DateIfMatch(boolean strict, int rest) throws NumberFormatException {
        char c6;
        char c5;
        char c7;
        char c4;
        char c3;
        char c2;
        char c52;
        char c62;
        boolean z;
        char c72;
        boolean sperate17;
        char d1;
        char d0;
        char M1;
        char M0;
        char y3;
        char y2;
        char y1;
        char d02;
        char c63;
        int hour;
        int minute;
        int seconds;
        int millis;
        char h0;
        char h1;
        char m0;
        char m1;
        char s0;
        char s1;
        char c64;
        char y12;
        char y22;
        char y32;
        char M12;
        char d03;
        char d12;
        char y0;
        char y02;
        char c;
        int i;
        int i2;
        char cCharAt;
        char cCharAt2;
        char c8;
        int i3;
        char cCharAt3;
        char cCharAt4;
        char cCharAt5;
        char cCharAt6;
        if (rest < 8) {
            return false;
        }
        char c0 = charAt(this.bp);
        char c1 = charAt(this.bp + 1);
        char c22 = charAt(this.bp + 2);
        char c32 = charAt(this.bp + 3);
        char c42 = charAt(this.bp + 4);
        char c53 = charAt(this.bp + 5);
        char c65 = charAt(this.bp + 6);
        char c73 = charAt(this.bp + 7);
        if (!strict && rest > 13) {
            char cCharAt7 = charAt((this.bp + rest) - 1);
            char cCharAt8 = charAt((this.bp + rest) - 2);
            if (c0 == '/' && c1 == 'D' && c22 == 'a' && c32 == 't' && c42 == 'e' && c53 == '(' && cCharAt7 == '/' && cCharAt8 == ')') {
                int i4 = -1;
                for (int i5 = 6; i5 < rest; i5++) {
                    char cCharAt9 = charAt(this.bp + i5);
                    if (cCharAt9 == '+') {
                        i4 = i5;
                    } else if (cCharAt9 < '0' || cCharAt9 > '9') {
                        break;
                    }
                }
                if (i4 == -1) {
                    return false;
                }
                int i6 = this.bp + 6;
                long j = Long.parseLong(subString(i6, (this.bp + i4) - i6));
                this.calendar = Calendar.getInstance(this.timeZone, this.locale);
                this.calendar.setTimeInMillis(j);
                this.token = 5;
                return true;
            }
            c6 = c65;
            c5 = c53;
        } else {
            c6 = c65;
            c5 = c53;
        }
        if (rest == 8 || rest == 14) {
            c7 = c73;
            c4 = c42;
            c3 = c32;
            c2 = c22;
            c52 = c5;
            c62 = c6;
            z = false;
        } else if (rest == 16 && ((cCharAt6 = charAt(this.bp + 10)) == 'T' || cCharAt6 == ' ')) {
            c7 = c73;
            c4 = c42;
            c3 = c32;
            c2 = c22;
            c52 = c5;
            c62 = c6;
            z = false;
        } else {
            if (rest != 17 || charAt(this.bp + 6) == '-') {
                if (rest >= 9) {
                    char c82 = charAt(this.bp + 8);
                    char c9 = charAt(this.bp + 9);
                    if ((c42 == '-' && c73 == '-') || (c42 == '/' && c73 == '/')) {
                        y12 = c1;
                        y22 = c22;
                        y32 = c32;
                        char c10 = c5;
                        M12 = c6;
                        if (c9 == ' ') {
                            y0 = c0;
                            c64 = c6;
                            y02 = '\t';
                            c = c10;
                            d03 = '0';
                            d12 = c82;
                        } else {
                            y0 = c0;
                            c64 = c6;
                            y02 = '\n';
                            c = c10;
                            d03 = c82;
                            d12 = c9;
                        }
                    } else {
                        if (c42 == '-') {
                            c64 = c6;
                            if (c64 == '-') {
                                y12 = c1;
                                y22 = c22;
                                y32 = c32;
                                M12 = c5;
                                if (c82 == ' ') {
                                    y0 = c0;
                                    y02 = '\b';
                                    c = '0';
                                    d03 = '0';
                                    d12 = c73;
                                } else {
                                    y0 = c0;
                                    y02 = '\t';
                                    c = '0';
                                    d03 = c73;
                                    d12 = c82;
                                }
                            }
                        } else {
                            c64 = c6;
                        }
                        if ((c22 == '.' && c5 == '.') || (c22 == '-' && c5 == '-')) {
                            M12 = c42;
                            y12 = c73;
                            y22 = c82;
                            y32 = c9;
                            y0 = c64;
                            y02 = '\n';
                            c = c32;
                            d03 = c0;
                            d12 = c1;
                        } else if (c82 == 'T') {
                            y12 = c1;
                            y22 = c22;
                            y32 = c32;
                            M12 = c5;
                            y0 = c0;
                            y02 = '\b';
                            c = c42;
                            d03 = c64;
                            d12 = c73;
                        } else if (c42 == 24180 || c42 == 45380) {
                            y12 = c1;
                            y22 = c22;
                            y32 = c32;
                            if (c73 == 26376 || c73 == 50900) {
                                char M02 = c5;
                                M12 = c64;
                                if (c9 != 26085 && c9 != 51068) {
                                    if (charAt(this.bp + 10) == 26085 || charAt(this.bp + 10) == 51068) {
                                        d03 = c82;
                                        d12 = c9;
                                        y0 = c0;
                                        y02 = 11;
                                        c = M02;
                                    } else {
                                        return false;
                                    }
                                } else {
                                    d03 = '0';
                                    d12 = c82;
                                    y0 = c0;
                                    y02 = '\n';
                                    c = M02;
                                }
                            } else if (c64 == 26376 || c64 == 50900) {
                                M12 = c5;
                                if (c82 == 26085 || c82 == 51068) {
                                    d03 = '0';
                                    y0 = c0;
                                    y02 = '\n';
                                    d12 = c73;
                                    c = '0';
                                } else if (c9 == 26085 || c9 == 51068) {
                                    d03 = c73;
                                    y0 = c0;
                                    y02 = '\n';
                                    d12 = c82;
                                    c = '0';
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                    if (checkDate(y0, y12, y22, y32, c, M12, d03, d12)) {
                        char c11 = y02;
                        setCalendar(y0, y12, y22, y32, c, M12, d03, d12);
                        char cCharAt10 = charAt(this.bp + c11);
                        if (cCharAt10 == 'T' && rest == 16 && c11 == '\b' && charAt(this.bp + 15) == 'Z') {
                            char cCharAt11 = charAt(this.bp + c11 + 1);
                            char cCharAt12 = charAt(this.bp + c11 + 2);
                            char cCharAt13 = charAt(this.bp + c11 + 3);
                            char cCharAt14 = charAt(this.bp + c11 + 4);
                            char cCharAt15 = charAt(this.bp + c11 + 5);
                            char cCharAt16 = charAt(this.bp + c11 + 6);
                            if (!checkTime(cCharAt11, cCharAt12, cCharAt13, cCharAt14, cCharAt15, cCharAt16)) {
                                return false;
                            }
                            setTime(cCharAt11, cCharAt12, cCharAt13, cCharAt14, cCharAt15, cCharAt16);
                            this.calendar.set(14, 0);
                            if (this.calendar.getTimeZone().getRawOffset() != 0) {
                                String[] availableIDs = TimeZone.getAvailableIDs(0);
                                if (availableIDs.length > 0) {
                                    this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs[0]));
                                }
                            }
                            this.token = 5;
                            return true;
                        }
                        if (cCharAt10 == 'T' || (cCharAt10 == ' ' && !strict)) {
                            if (rest >= c11 + '\t' && charAt(this.bp + c11 + 3) == ':' && charAt(this.bp + c11 + 6) == ':') {
                                char cCharAt17 = charAt(this.bp + c11 + 1);
                                char cCharAt18 = charAt(this.bp + c11 + 2);
                                char cCharAt19 = charAt(this.bp + c11 + 4);
                                char cCharAt20 = charAt(this.bp + c11 + 5);
                                char cCharAt21 = charAt(this.bp + c11 + 7);
                                char cCharAt22 = charAt(this.bp + c11 + 8);
                                if (!checkTime(cCharAt17, cCharAt18, cCharAt19, cCharAt20, cCharAt21, cCharAt22)) {
                                    return false;
                                }
                                setTime(cCharAt17, cCharAt18, cCharAt19, cCharAt20, cCharAt21, cCharAt22);
                                int i7 = -1;
                                if (charAt(this.bp + c11 + 9) != '.') {
                                    i = 0;
                                } else {
                                    if (rest < c11 + 11 || (cCharAt3 = charAt(this.bp + c11 + 10)) < '0' || cCharAt3 > '9') {
                                        return false;
                                    }
                                    int i8 = cCharAt3 - '0';
                                    i7 = 1;
                                    if (rest > c11 + 11 && (cCharAt5 = charAt(this.bp + c11 + 11)) >= '0' && cCharAt5 <= '9') {
                                        i7 = 2;
                                        i8 = (i8 * 10) + (cCharAt5 - '0');
                                    }
                                    if (i7 == 2 && (cCharAt4 = charAt(this.bp + c11 + 12)) >= '0' && cCharAt4 <= '9') {
                                        i7 = 3;
                                        i = (i8 * 10) + (cCharAt4 - '0');
                                    } else {
                                        i = i8;
                                    }
                                }
                                this.calendar.set(14, i);
                                int i9 = 0;
                                char cCharAt23 = charAt(this.bp + c11 + 10 + i7);
                                if (cCharAt23 != ' ') {
                                    i2 = i7;
                                    cCharAt = cCharAt23;
                                } else {
                                    int i10 = i7 + 1;
                                    i2 = i10;
                                    cCharAt = charAt(this.bp + c11 + 10 + i10);
                                }
                                if (cCharAt == '+' || cCharAt == '-') {
                                    char cCharAt24 = charAt(this.bp + c11 + 10 + i2 + 1);
                                    if (cCharAt24 >= '0' && cCharAt24 <= '1' && (cCharAt2 = charAt(this.bp + c11 + 10 + i2 + 2)) >= '0' && cCharAt2 <= '9') {
                                        char cCharAt25 = charAt(this.bp + c11 + 10 + i2 + 3);
                                        char c12 = '0';
                                        if (cCharAt25 == ':') {
                                            char cCharAt26 = charAt(this.bp + c11 + 10 + i2 + 4);
                                            char cCharAt27 = charAt(this.bp + c11 + 10 + i2 + 5);
                                            if (cCharAt26 == '4' && cCharAt27 == '5') {
                                                if (cCharAt24 != '1' || (cCharAt2 != '2' && cCharAt2 != '3')) {
                                                    if (cCharAt24 != '0') {
                                                        return false;
                                                    }
                                                    if (cCharAt2 != '5' && cCharAt2 != '8') {
                                                        return false;
                                                    }
                                                }
                                            } else if ((cCharAt26 != '0' && cCharAt26 != '3') || cCharAt27 != '0') {
                                                return false;
                                            }
                                            i3 = 6;
                                            c12 = cCharAt27;
                                            c8 = cCharAt26;
                                        } else if (cCharAt25 == '0') {
                                            char cCharAt28 = charAt(this.bp + c11 + 10 + i2 + 4);
                                            if (cCharAt28 != '0' && cCharAt28 != '3') {
                                                return false;
                                            }
                                            c8 = cCharAt28;
                                            i3 = 5;
                                        } else if (cCharAt25 == '3' && charAt(this.bp + c11 + 10 + i2 + 4) == '0') {
                                            c12 = '0';
                                            c8 = '3';
                                            i3 = 5;
                                        } else if (cCharAt25 != '4' || charAt(this.bp + c11 + 10 + i2 + 4) != '5') {
                                            c8 = '0';
                                            i3 = 3;
                                        } else {
                                            c12 = '5';
                                            c8 = '4';
                                            i3 = 5;
                                        }
                                        setTimeZone(cCharAt, cCharAt24, cCharAt2, c8, c12);
                                        i9 = i3;
                                    }
                                    return false;
                                }
                                if (cCharAt == 'Z') {
                                    i9 = 1;
                                    if (this.calendar.getTimeZone().getRawOffset() != 0) {
                                        String[] availableIDs2 = TimeZone.getAvailableIDs(0);
                                        if (availableIDs2.length > 0) {
                                            this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs2[0]));
                                        }
                                    }
                                }
                                char cCharAt29 = charAt(this.bp + c11 + '\n' + i2 + i9);
                                if (cCharAt29 != 26 && cCharAt29 != '\"') {
                                    return false;
                                }
                                int i11 = this.bp + c11 + '\n' + i2 + i9;
                                this.bp = i11;
                                this.ch = charAt(i11);
                                this.token = 5;
                                return true;
                            }
                            return false;
                        }
                        if (cCharAt10 == '\"' || cCharAt10 == 26 || cCharAt10 == 26085 || cCharAt10 == 51068) {
                            int i12 = 0;
                            this.calendar.set(11, i12);
                            this.calendar.set(12, i12);
                            this.calendar.set(13, i12);
                            this.calendar.set(14, i12);
                            int i13 = this.bp + c11;
                            this.bp = i13;
                            this.ch = charAt(i13);
                            this.token = 5;
                            return true;
                        }
                        if ((cCharAt10 != '+' && cCharAt10 != '-') || this.len != c11 + 6 || charAt(this.bp + c11 + 3) != ':' || charAt(this.bp + c11 + 4) != '0' || charAt(this.bp + c11 + 5) != '0') {
                            return false;
                        }
                        setTime('0', '0', '0', '0', '0', '0');
                        this.calendar.set(14, 0);
                        setTimeZone(cCharAt10, charAt(this.bp + c11 + 1), charAt(this.bp + c11 + 2));
                        return true;
                    }
                    return false;
                }
                return false;
            }
            c7 = c73;
            c4 = c42;
            c3 = c32;
            c2 = c22;
            c52 = c5;
            c62 = c6;
            z = false;
        }
        if (!strict) {
            char c83 = charAt(this.bp + 8);
            char c43 = c4;
            if (c43 == '-') {
                c72 = c7;
                boolean z2 = c72 == '-';
                boolean c_47 = z2;
                boolean sperate16 = !c_47 && rest == 16;
                sperate17 = !c_47 && rest == 17;
                if (!sperate17 || sperate16) {
                    y2 = c2;
                    y3 = c3;
                    M0 = c52;
                    M1 = c63;
                    d0 = c83;
                    d1 = charAt(this.bp + 9);
                    d02 = c0;
                    y1 = c1;
                    if (checkDate(d02, y1, y2, y3, M0, M1, d0, d1)) {
                        return false;
                    }
                    setCalendar(d02, y1, y2, y3, M0, M1, d0, d1);
                    if (rest != 8) {
                        char c92 = charAt(this.bp + 9);
                        char c102 = charAt(this.bp + 10);
                        char c112 = charAt(this.bp + 11);
                        char c122 = charAt(this.bp + 12);
                        char c13 = charAt(this.bp + 13);
                        if ((sperate17 && c102 == 'T' && c13 == ':' && charAt(this.bp + 16) == 'Z') || (sperate16 && ((c102 == ' ' || c102 == 'T') && c13 == ':'))) {
                            char m02 = charAt(this.bp + 14);
                            char m12 = charAt(this.bp + 15);
                            h0 = c112;
                            h1 = c122;
                            m0 = m02;
                            m1 = m12;
                            s0 = '0';
                            s1 = '0';
                        } else {
                            h0 = c83;
                            h1 = c92;
                            m0 = c102;
                            m1 = c112;
                            s0 = c122;
                            s1 = c13;
                        }
                        if (!checkTime(h0, h1, m0, m1, s0, s1)) {
                            return false;
                        }
                        if (rest == 17 && !sperate17) {
                            char S0 = charAt(this.bp + 14);
                            char S1 = charAt(this.bp + 15);
                            char S2 = charAt(this.bp + 16);
                            if (S0 < '0' || S0 > '9' || S1 < '0' || S1 > '9' || S2 < '0' || S2 > '9') {
                                return false;
                            }
                            millis = ((S0 - '0') * 100) + ((S1 - '0') * 10) + (S2 - '0');
                        } else {
                            millis = 0;
                        }
                        hour = ((h0 - '0') * 10) + (h1 - '0');
                        minute = ((m0 - '0') * 10) + (m1 - '0');
                        seconds = ((s0 - '0') * 10) + (s1 - '0');
                    } else {
                        hour = 0;
                        minute = 0;
                        seconds = 0;
                        millis = 0;
                    }
                    this.calendar.set(11, hour);
                    this.calendar.set(12, minute);
                    this.calendar.set(13, seconds);
                    this.calendar.set(14, millis);
                    this.token = 5;
                    return true;
                }
                if (c43 == '-') {
                    c63 = c62;
                    if (c63 == '-') {
                        y2 = c2;
                        y3 = c3;
                        M0 = '0';
                        M1 = c52;
                        d0 = '0';
                        d1 = c72;
                        d02 = c0;
                        y1 = c1;
                    }
                    if (checkDate(d02, y1, y2, y3, M0, M1, d0, d1)) {
                    }
                } else {
                    c63 = c62;
                }
                y2 = c2;
                y3 = c3;
                M0 = c43;
                M1 = c52;
                d0 = c63;
                d1 = c72;
                d02 = c0;
                y1 = c1;
                if (checkDate(d02, y1, y2, y3, M0, M1, d0, d1)) {
                }
            } else {
                c72 = c7;
            }
            boolean c_472 = z2;
            boolean sperate162 = !c_472 && rest == 16;
            sperate17 = !c_472 && rest == 17;
            c63 = !sperate17 ? c62 : c62;
            y2 = c2;
            y3 = c3;
            M0 = c52;
            M1 = c63;
            d0 = c83;
            d1 = charAt(this.bp + 9);
            d02 = c0;
            y1 = c1;
            if (checkDate(d02, y1, y2, y3, M0, M1, d0, d1)) {
            }
        } else {
            return z;
        }
    }

    protected void setTime(char h0, char h1, char m0, char m1, char s0, char s1) {
        int hour = ((h0 - '0') * 10) + (h1 - '0');
        int minute = ((m0 - '0') * 10) + (m1 - '0');
        int seconds = ((s0 - '0') * 10) + (s1 - '0');
        this.calendar.set(11, hour);
        this.calendar.set(12, minute);
        this.calendar.set(13, seconds);
    }

    protected void setTimeZone(char timeZoneFlag, char t0, char t1) {
        setTimeZone(timeZoneFlag, t0, t1, '0', '0');
    }

    protected void setTimeZone(char timeZoneFlag, char t0, char t1, char t3, char t4) {
        int timeZoneOffset = ((((t0 - '0') * 10) + (t1 - '0')) * 3600 * 1000) + ((((t3 - '0') * 10) + (t4 - '0')) * 60 * 1000);
        if (timeZoneFlag == '-') {
            timeZoneOffset = -timeZoneOffset;
        }
        if (this.calendar.getTimeZone().getRawOffset() != timeZoneOffset) {
            this.calendar.setTimeZone(new SimpleTimeZone(timeZoneOffset, Integer.toString(timeZoneOffset)));
        }
    }

    private boolean checkTime(char h0, char h1, char m0, char m1, char s0, char s1) {
        if (h0 == '0') {
            if (h1 < '0' || h1 > '9') {
                return false;
            }
        } else if (h0 == '1') {
            if (h1 < '0' || h1 > '9') {
                return false;
            }
        } else if (h0 != '2' || h1 < '0' || h1 > '4') {
            return false;
        }
        if (m0 >= '0' && m0 <= '5') {
            if (m1 < '0' || m1 > '9') {
                return false;
            }
        } else if (m0 != '6' || m1 != '0') {
            return false;
        }
        return (s0 < '0' || s0 > '5') ? s0 == '6' && s1 == '0' : s1 >= '0' && s1 <= '9';
    }

    private void setCalendar(char y0, char y1, char y2, char y3, char M0, char M1, char d0, char d1) {
        this.calendar = Calendar.getInstance(this.timeZone, this.locale);
        int year = ((y0 - '0') * 1000) + ((y1 - '0') * 100) + ((y2 - '0') * 10) + (y3 - '0');
        int month = (((M0 - '0') * 10) + (M1 - '0')) - 1;
        int day = ((d0 - '0') * 10) + (d1 - '0');
        this.calendar.set(1, year);
        this.calendar.set(2, month);
        this.calendar.set(5, day);
    }

    static boolean checkDate(char y0, char y1, char y2, char y3, char M0, char M1, int d0, int d1) {
        if (y0 < '0' || y0 > '9' || y1 < '0' || y1 > '9' || y2 < '0' || y2 > '9' || y3 < '0' || y3 > '9') {
            return false;
        }
        if (M0 == '0') {
            if (M1 < '1' || M1 > '9') {
                return false;
            }
        } else {
            if (M0 != '1') {
                return false;
            }
            if (M1 != '0' && M1 != '1' && M1 != '2') {
                return false;
            }
        }
        if (d0 == 48) {
            if (d1 < 49 || d1 > 57) {
                return false;
            }
            return true;
        }
        if (d0 == 49 || d0 == 50) {
            if (d1 < 48 || d1 > 57) {
                return false;
            }
            return true;
        }
        if (d0 != 51) {
            return false;
        }
        if (d1 != 48 && d1 != 49) {
            return false;
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean isEOF() {
        if (this.bp != this.len) {
            return this.ch == 26 && this.bp + 1 >= this.len;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0068, code lost:
    
        if (r5 != '.') goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x006a, code lost:
    
        r17.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006c, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006d, code lost:
    
        if (r14 >= 0) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006f, code lost:
    
        r17.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0071, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0072, code lost:
    
        if (r9 == false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0074, code lost:
    
        if (r5 == '\"') goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0076, code lost:
    
        r17.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0078, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0079, code lost:
    
        r5 = charAt(r15);
        r15 = r15 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0084, code lost:
    
        if (r5 == ',') goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0086, code lost:
    
        if (r5 != '}') goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x008d, code lost:
    
        if (isWhitespace(r5) == false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x008f, code lost:
    
        r5 = charAt(r15);
        r15 = r15 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0097, code lost:
    
        r17.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0099, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x009a, code lost:
    
        r17.bp = r15 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00a1, code lost:
    
        if (r5 != ',') goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00a3, code lost:
    
        r2 = r17.bp + 1;
        r17.bp = r2;
        r17.ch = charAt(r2);
        r17.matchStat = 3;
        r17.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00b3, code lost:
    
        if (r10 == false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00b8, code lost:
    
        return r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b9, code lost:
    
        if (r5 != '}') goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00bb, code lost:
    
        r17.bp = r15 - 1;
        r13 = r17.bp + 1;
        r17.bp = r13;
        r5 = charAt(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00c8, code lost:
    
        if (r5 != ',') goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00ca, code lost:
    
        r17.token = 16;
        r2 = r17.bp + 1;
        r17.bp = r2;
        r17.ch = charAt(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00da, code lost:
    
        if (r5 != ']') goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00dc, code lost:
    
        r17.token = 15;
        r2 = r17.bp + 1;
        r17.bp = r2;
        r17.ch = charAt(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00ec, code lost:
    
        if (r5 != '}') goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00ee, code lost:
    
        r17.token = 13;
        r2 = r17.bp + 1;
        r17.bp = r2;
        r17.ch = charAt(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0100, code lost:
    
        if (r5 != 26) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0102, code lost:
    
        r17.token = 20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0107, code lost:
    
        r17.matchStat = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x010f, code lost:
    
        if (isWhitespace(r5) == false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0111, code lost:
    
        r13 = r17.bp + 1;
        r17.bp = r13;
        r5 = charAt(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x011b, code lost:
    
        r17.bp = r3;
        r17.ch = r4;
        r17.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0121, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0122, code lost:
    
        if (r10 == false) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0127, code lost:
    
        return r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:?, code lost:
    
        return -r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:?, code lost:
    
        return -r14;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int scanFieldInt(char[] fieldName) {
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int index = this.bp + fieldName.length;
        int index2 = index + 1;
        char ch = charAt(index);
        boolean quote = ch == '\"';
        if (quote) {
            ch = charAt(index2);
            index2++;
        }
        boolean negative = ch == '-';
        if (negative) {
            ch = charAt(index2);
            index2++;
        }
        if (ch >= '0' && ch <= '9') {
            int value = ch - '0';
            while (true) {
                int index3 = index2 + 1;
                char ch2 = charAt(index2);
                if (ch2 < '0' || ch2 > '9') {
                    break;
                }
                int value_10 = value * 10;
                if (value_10 < value) {
                    this.matchStat = -1;
                    return 0;
                }
                value = value_10 + (ch2 - '0');
                index2 = index3;
            }
        } else {
            this.matchStat = -1;
            return 0;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String scanFieldString(char[] fieldName) {
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        while (!charArrayCompare(this.text, this.bp, fieldName)) {
            if (isWhitespace(this.ch)) {
                next();
                while (isWhitespace(this.ch)) {
                    next();
                }
            } else {
                this.matchStat = -2;
                return stringDefaultValue();
            }
        }
        int index = this.bp + fieldName.length;
        int spaceCount = 0;
        int index2 = index + 1;
        char ch = charAt(index);
        if (ch != '\"') {
            while (isWhitespace(ch)) {
                spaceCount++;
                ch = charAt(index2);
                index2++;
            }
            if (ch != '\"') {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        }
        int startIndex = index2;
        int endIndex = indexOf(Typography.quote, startIndex);
        if (endIndex == -1) {
            throw new JSONException("unclosed str");
        }
        String stringVal = subString(startIndex, endIndex - startIndex);
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
            int chars_len = endIndex - (((this.bp + fieldName.length) + 1) + spaceCount);
            char[] chars = sub_chars(this.bp + fieldName.length + 1 + spaceCount, chars_len);
            stringVal = readString(chars, chars_len);
        }
        if ((this.features & Feature.TrimStringFieldValue.mask) != 0) {
            stringVal = stringVal.trim();
        }
        char ch2 = charAt(endIndex + 1);
        while (ch2 != ',' && ch2 != '}') {
            if (isWhitespace(ch2)) {
                endIndex++;
                ch2 = charAt(endIndex + 1);
            } else {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        }
        this.bp = endIndex + 1;
        this.ch = ch2;
        String strVal = stringVal;
        if (ch2 == ',') {
            int i3 = this.bp + 1;
            this.bp = i3;
            this.ch = charAt(i3);
            this.matchStat = 3;
            return strVal;
        }
        int i4 = this.bp + 1;
        this.bp = i4;
        char ch3 = charAt(i4);
        if (ch3 == ',') {
            this.token = 16;
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = charAt(i5);
        } else if (ch3 == ']') {
            this.token = 15;
            int i6 = this.bp + 1;
            this.bp = i6;
            this.ch = charAt(i6);
        } else if (ch3 == '}') {
            this.token = 13;
            int i7 = this.bp + 1;
            this.bp = i7;
            this.ch = charAt(i7);
        } else if (ch3 == 26) {
            this.token = 20;
        } else {
            this.bp = startPos;
            this.ch = startChar;
            this.matchStat = -1;
            return stringDefaultValue();
        }
        this.matchStat = 4;
        return strVal;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public Date scanFieldDate(char[] fieldName) {
        Date dateVal;
        int index;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int index2 = this.bp + fieldName.length;
        int index3 = index2 + 1;
        char ch = charAt(index2);
        if (ch == '\"') {
            int endIndex = indexOf(Typography.quote, index3);
            if (endIndex == -1) {
                throw new JSONException("unclosed str");
            }
            int rest = endIndex - index3;
            this.bp = index3;
            if (scanISO8601DateIfMatch(false, rest)) {
                dateVal = this.calendar.getTime();
                ch = charAt(endIndex + 1);
                this.bp = startPos;
                while (ch != ',' && ch != '}') {
                    if (isWhitespace(ch)) {
                        endIndex++;
                        ch = charAt(endIndex + 1);
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                }
                this.bp = endIndex + 1;
                this.ch = ch;
            } else {
                this.bp = startPos;
                this.matchStat = -1;
                return null;
            }
        } else {
            char c = '0';
            if (ch == '-' || (ch >= '0' && ch <= '9')) {
                long millis = 0;
                boolean negative = false;
                if (ch == '-') {
                    ch = charAt(index3);
                    negative = true;
                    index3++;
                }
                if (ch >= '0' && ch <= '9') {
                    long millis2 = ch - '0';
                    while (true) {
                        index = index3 + 1;
                        ch = charAt(index3);
                        if (ch < c || ch > '9') {
                            break;
                        }
                        index3 = index;
                        millis2 = (10 * millis2) + (ch - '0');
                        c = '0';
                    }
                    long millis3 = millis2;
                    if (ch == ',' || ch == '}') {
                        this.bp = index - 1;
                    }
                    millis = millis3;
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
        if (ch == ',') {
            int i = this.bp + 1;
            this.bp = i;
            this.ch = charAt(i);
            this.matchStat = 3;
            this.token = 16;
            return dateVal;
        }
        int i2 = this.bp + 1;
        this.bp = i2;
        char ch2 = charAt(i2);
        if (ch2 == ',') {
            this.token = 16;
            int i3 = this.bp + 1;
            this.bp = i3;
            this.ch = charAt(i3);
        } else if (ch2 == ']') {
            this.token = 15;
            int i4 = this.bp + 1;
            this.bp = i4;
            this.ch = charAt(i4);
        } else if (ch2 == '}') {
            this.token = 13;
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = charAt(i5);
        } else if (ch2 == 26) {
            this.token = 20;
        } else {
            this.bp = startPos;
            this.ch = startChar;
            this.matchStat = -1;
            return null;
        }
        this.matchStat = 4;
        return dateVal;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldSymbol(char[] fieldName) {
        this.matchStat = 0;
        while (!charArrayCompare(this.text, this.bp, fieldName)) {
            if (isWhitespace(this.ch)) {
                next();
                while (isWhitespace(this.ch)) {
                    next();
                }
            } else {
                this.matchStat = -2;
                return 0L;
            }
        }
        int index = this.bp + fieldName.length;
        int spaceCount = 0;
        int index2 = index + 1;
        char ch = charAt(index);
        if (ch != '\"') {
            while (isWhitespace(ch)) {
                ch = charAt(index2);
                spaceCount++;
                index2++;
            }
            if (ch != '\"') {
                this.matchStat = -1;
                return 0L;
            }
        }
        long hash = TypeUtils.fnv1a_64_magic_hashcode;
        while (true) {
            int index3 = index2 + 1;
            char ch2 = charAt(index2);
            if (ch2 == '\"') {
                this.bp = index3;
                char cCharAt = charAt(this.bp);
                char ch3 = cCharAt;
                this.ch = cCharAt;
                while (ch3 != ',') {
                    if (ch3 == '}') {
                        next();
                        skipWhitespace();
                        char ch4 = getCurrent();
                        if (ch4 == ',') {
                            this.token = 16;
                            int i = this.bp + 1;
                            this.bp = i;
                            this.ch = charAt(i);
                        } else if (ch4 == ']') {
                            this.token = 15;
                            int i2 = this.bp + 1;
                            this.bp = i2;
                            this.ch = charAt(i2);
                        } else if (ch4 == '}') {
                            this.token = 13;
                            int i3 = this.bp + 1;
                            this.bp = i3;
                            this.ch = charAt(i3);
                        } else if (ch4 == 26) {
                            this.token = 20;
                        } else {
                            this.matchStat = -1;
                            return 0L;
                        }
                        this.matchStat = 4;
                        return hash;
                    }
                    if (isWhitespace(ch3)) {
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        ch3 = charAt(i4);
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                }
                int i5 = this.bp + 1;
                this.bp = i5;
                this.ch = charAt(i5);
                this.matchStat = 3;
                return hash;
            }
            if (index3 > this.len) {
                this.matchStat = -1;
                return 0L;
            }
            hash = (hash ^ ch2) * TypeUtils.fnv1a_64_magic_prime;
            index2 = index3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ee, code lost:
    
        if (r8 != ']') goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f4, code lost:
    
        if (r5.size() != 0) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f6, code lost:
    
        r4 = r10 + 1;
        r3 = charAt(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00fd, code lost:
    
        r18.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0101, code lost:
    
        return null;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Collection<String> scanFieldStringArray(char[] fieldName, Class<?> type) {
        int index;
        char ch;
        boolean space;
        int index2;
        int index3;
        this.matchStat = 0;
        while (true) {
            if (this.ch != '\n' && this.ch != ' ') {
                break;
            }
            int index4 = this.bp + 1;
            this.bp = index4;
            this.ch = index4 >= this.len ? JSONLexer.EOI : this.text.charAt(index4);
        }
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return null;
        }
        Collection<String> list = newCollectionByType(type);
        int startPos = this.bp;
        char startChar = this.ch;
        int index5 = this.bp + fieldName.length;
        int index6 = index5 + 1;
        char ch2 = charAt(index5);
        int i = -1;
        if (ch2 == '[') {
            int index7 = index6 + 1;
            char ch3 = charAt(index6);
            while (true) {
                if (ch3 == '\"') {
                    int startIndex = index7;
                    int endIndex = indexOf(Typography.quote, startIndex);
                    if (endIndex != i) {
                        String stringVal = subString(startIndex, endIndex - startIndex);
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
                            int slashCount2 = endIndex - startIndex;
                            char[] chars = sub_chars(startIndex, slashCount2);
                            stringVal = readString(chars, slashCount2);
                        }
                        int index8 = endIndex + 1;
                        index2 = index8 + 1;
                        index3 = charAt(index8);
                        list.add(stringVal);
                    } else {
                        throw new JSONException("unclosed str");
                    }
                } else {
                    if (ch3 != 'n' || !this.text.startsWith("ull", index7)) {
                        break;
                    }
                    int index9 = index7 + 3;
                    index2 = index9 + 1;
                    index3 = charAt(index9);
                    list.add(null);
                }
                if (index3 == 44) {
                    index7 = index2 + 1;
                    ch3 = charAt(index2);
                    i = -1;
                } else {
                    if (index3 != 93) {
                        this.matchStat = -1;
                        return null;
                    }
                    index = index2 + 1;
                    ch = charAt(index2);
                    while (isWhitespace(ch)) {
                        ch = charAt(index);
                        index++;
                    }
                }
            }
        } else {
            if (!this.text.startsWith("ull", index6)) {
                this.matchStat = -1;
                return null;
            }
            int index10 = index6 + 3;
            index = index10 + 1;
            ch = charAt(index10);
            list = null;
        }
        this.bp = index;
        if (ch == ',') {
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return list;
        }
        if (ch == '}') {
            char ch4 = charAt(this.bp);
            do {
                if (ch4 == ',') {
                    this.token = 16;
                    int i3 = this.bp + 1;
                    this.bp = i3;
                    this.ch = charAt(i3);
                } else if (ch4 == ']') {
                    this.token = 15;
                    int i4 = this.bp + 1;
                    this.bp = i4;
                    this.ch = charAt(i4);
                } else if (ch4 == '}') {
                    this.token = 13;
                    int i5 = this.bp + 1;
                    this.bp = i5;
                    this.ch = charAt(i5);
                } else if (ch4 == 26) {
                    this.token = 20;
                    this.ch = ch4;
                } else {
                    space = false;
                    while (isWhitespace(ch4)) {
                        int index11 = index + 1;
                        ch4 = charAt(index);
                        this.bp = index11;
                        space = true;
                        index = index11;
                    }
                }
                this.matchStat = 4;
                return list;
            } while (space);
            this.matchStat = -1;
            return null;
        }
        this.ch = startChar;
        this.bp = startPos;
        this.matchStat = -1;
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:71:0x0116, code lost:
    
        r19.matchStat = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x011a, code lost:
    
        if (r12 == 0) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x011f, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:?, code lost:
    
        return -r9;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long scanFieldLong(char[] fieldName) {
        int index;
        char ch;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return 0L;
        }
        int index2 = this.bp + fieldName.length;
        int index3 = index2 + 1;
        char ch2 = charAt(index2);
        boolean quote = ch2 == '\"';
        if (quote) {
            ch2 = charAt(index3);
            index3++;
        }
        int index4 = 0;
        if (ch2 == '-') {
            ch2 = charAt(index3);
            index4 = 1;
            index3++;
        }
        if (ch2 >= '0' && ch2 <= '9') {
            long value = ch2 - '0';
            while (true) {
                index = index3 + 1;
                ch = charAt(index3);
                if (ch < '0' || ch > '9') {
                    break;
                }
                value = (10 * value) + (ch - '0');
                index3 = index;
            }
            if (ch == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (quote) {
                if (ch == '\"') {
                    ch = charAt(index);
                    index++;
                } else {
                    this.matchStat = -1;
                    return 0L;
                }
            }
            if (ch == ',' || ch == '}') {
                this.bp = index - 1;
            }
            boolean valid = value >= 0 || (value == Long.MIN_VALUE && index4 != 0);
            if (!valid) {
                this.bp = startPos;
                this.ch = startChar;
                this.matchStat = -1;
                return 0L;
            }
            while (ch != ',') {
                if (ch == '}') {
                    int i = 1;
                    int i2 = this.bp + 1;
                    this.bp = i2;
                    char ch3 = charAt(i2);
                    while (true) {
                        if (ch3 == ',') {
                            this.token = 16;
                            int i3 = this.bp + i;
                            this.bp = i3;
                            this.ch = charAt(i3);
                            break;
                        }
                        if (ch3 == ']') {
                            this.token = 15;
                            int i4 = this.bp + 1;
                            this.bp = i4;
                            this.ch = charAt(i4);
                            break;
                        }
                        if (ch3 == '}') {
                            this.token = 13;
                            int i5 = this.bp + 1;
                            this.bp = i5;
                            this.ch = charAt(i5);
                            break;
                        }
                        if (ch3 == 26) {
                            this.token = 20;
                            break;
                        }
                        if (isWhitespace(ch3)) {
                            i = 1;
                            int i6 = this.bp + 1;
                            this.bp = i6;
                            ch3 = charAt(i6);
                        } else {
                            this.bp = startPos;
                            this.ch = startChar;
                            this.matchStat = -1;
                            return 0L;
                        }
                    }
                } else if (isWhitespace(ch)) {
                    this.bp = index;
                    ch = charAt(index);
                    index++;
                } else {
                    this.matchStat = -1;
                    return 0L;
                }
            }
            int i7 = this.bp + 1;
            this.bp = i7;
            this.ch = charAt(i7);
            this.matchStat = 3;
            this.token = 16;
            return index4 != 0 ? -value : value;
        }
        this.bp = startPos;
        this.ch = startChar;
        this.matchStat = -1;
        return 0L;
    }

    /* JADX WARN: Code restructure failed: missing block: B:92:0x015e, code lost:
    
        r12.matchStat = 4;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean scanFieldBoolean(char[] fieldName) {
        char ch;
        boolean value;
        int index;
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return false;
        }
        int startPos = this.bp;
        int index2 = this.bp + fieldName.length;
        int index3 = index2 + 1;
        char ch2 = charAt(index2);
        boolean quote = ch2 == '\"';
        if (quote) {
            ch2 = charAt(index3);
            index3++;
        }
        if (ch2 == 't') {
            int index4 = index3 + 1;
            if (charAt(index3) != 114) {
                this.matchStat = -1;
                return false;
            }
            int index5 = index4 + 1;
            if (charAt(index4) != 117) {
                this.matchStat = -1;
                return false;
            }
            int index6 = index5 + 1;
            if (charAt(index5) != 101) {
                this.matchStat = -1;
                return false;
            }
            if (quote) {
                int index7 = index6 + 1;
                if (charAt(index6) == 34) {
                    index6 = index7;
                } else {
                    this.matchStat = -1;
                    return false;
                }
            }
            this.bp = index6;
            ch = charAt(this.bp);
            value = true;
        } else if (ch2 == 'f') {
            int index8 = index3 + 1;
            if (charAt(index3) != 97) {
                this.matchStat = -1;
                return false;
            }
            int index9 = index8 + 1;
            if (charAt(index8) != 108) {
                this.matchStat = -1;
                return false;
            }
            int index10 = index9 + 1;
            if (charAt(index9) != 115) {
                this.matchStat = -1;
                return false;
            }
            int index11 = index10 + 1;
            if (charAt(index10) != 101) {
                this.matchStat = -1;
                return false;
            }
            if (quote) {
                index = index11 + 1;
                if (charAt(index11) != 34) {
                    this.matchStat = -1;
                    return false;
                }
            } else {
                index = index11;
            }
            this.bp = index;
            ch = charAt(this.bp);
            value = false;
        } else if (ch2 == '1') {
            if (quote) {
                int index12 = index3 + 1;
                if (charAt(index3) == 34) {
                    index3 = index12;
                } else {
                    this.matchStat = -1;
                    return false;
                }
            }
            this.bp = index3;
            ch = charAt(this.bp);
            value = true;
        } else if (ch2 == '0') {
            if (quote) {
                int index13 = index3 + 1;
                if (charAt(index3) == 34) {
                    index3 = index13;
                } else {
                    this.matchStat = -1;
                    return false;
                }
            }
            this.bp = index3;
            ch = charAt(this.bp);
            value = false;
        } else {
            this.matchStat = -1;
            return false;
        }
        while (true) {
            if (ch == ',') {
                int i = this.bp + 1;
                this.bp = i;
                this.ch = charAt(i);
                this.matchStat = 3;
                this.token = 16;
                break;
            }
            if (ch == '}') {
                int i2 = this.bp + 1;
                this.bp = i2;
                char ch3 = charAt(i2);
                while (true) {
                    if (ch3 == ',') {
                        this.token = 16;
                        int i3 = this.bp + 1;
                        this.bp = i3;
                        this.ch = charAt(i3);
                        break;
                    }
                    if (ch3 == ']') {
                        this.token = 15;
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        this.ch = charAt(i4);
                        break;
                    }
                    if (ch3 == '}') {
                        this.token = 13;
                        int i5 = this.bp + 1;
                        this.bp = i5;
                        this.ch = charAt(i5);
                        break;
                    }
                    if (ch3 == 26) {
                        this.token = 20;
                        break;
                    }
                    if (isWhitespace(ch3)) {
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        ch3 = charAt(i6);
                    } else {
                        this.matchStat = -1;
                        return false;
                    }
                }
            } else if (isWhitespace(ch)) {
                int i7 = this.bp + 1;
                this.bp = i7;
                ch = charAt(i7);
            } else {
                this.bp = startPos;
                charAt(this.bp);
                this.matchStat = -1;
                return false;
            }
        }
        return value;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x007d, code lost:
    
        if (r2 != '.') goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007f, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0081, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0082, code lost:
    
        if (r6 == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0084, code lost:
    
        if (r2 == '\"') goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0086, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0088, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0089, code lost:
    
        r2 = charAt(r12);
        r12 = r12 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0090, code lost:
    
        if (r11 >= 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0092, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0094, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0095, code lost:
    
        if (r2 != r15) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0097, code lost:
    
        r14.bp = r12;
        r14.ch = charAt(r14.bp);
        r14.matchStat = 3;
        r14.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00a6, code lost:
    
        if (r5 == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ab, code lost:
    
        return r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b0, code lost:
    
        if (isWhitespace(r2) == false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00b2, code lost:
    
        r2 = charAt(r12);
        r12 = r12 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ba, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00bc, code lost:
    
        if (r5 == false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00c1, code lost:
    
        return r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:?, code lost:
    
        return -r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:?, code lost:
    
        return -r11;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int scanInt(char expectNext) {
        this.matchStat = 0;
        int mark = this.bp;
        int offset = this.bp;
        int offset2 = offset + 1;
        char chLocal = charAt(offset);
        while (isWhitespace(chLocal)) {
            chLocal = charAt(offset2);
            offset2++;
        }
        boolean quote = chLocal == '\"';
        if (quote) {
            chLocal = charAt(offset2);
            offset2++;
        }
        boolean negative = chLocal == '-';
        if (negative) {
            chLocal = charAt(offset2);
            offset2++;
        }
        if (chLocal >= '0' && chLocal <= '9') {
            int value = chLocal - '0';
            while (true) {
                int offset3 = offset2 + 1;
                char chLocal2 = charAt(offset2);
                if (chLocal2 < '0' || chLocal2 > '9') {
                    break;
                }
                int value_10 = value * 10;
                if (value_10 < value) {
                    throw new JSONException("parseInt error : " + subString(mark, offset3 - 1));
                }
                value = value_10 + (chLocal2 - '0');
                offset2 = offset3;
            }
        } else {
            if (chLocal == 'n') {
                int offset4 = offset2 + 1;
                if (charAt(offset2) == 117) {
                    int offset5 = offset4 + 1;
                    if (charAt(offset4) == 108) {
                        int offset6 = offset5 + 1;
                        if (charAt(offset5) == 108) {
                            this.matchStat = 5;
                            int offset7 = offset6 + 1;
                            char chLocal3 = charAt(offset6);
                            if (quote && chLocal3 == '\"') {
                                chLocal3 = charAt(offset7);
                                offset7++;
                            }
                            while (chLocal3 != ',') {
                                if (chLocal3 == ']') {
                                    this.bp = offset7;
                                    this.ch = charAt(this.bp);
                                    this.matchStat = 5;
                                    this.token = 15;
                                    return 0;
                                }
                                if (isWhitespace(chLocal3)) {
                                    chLocal3 = charAt(offset7);
                                    offset7++;
                                } else {
                                    this.matchStat = -1;
                                    return 0;
                                }
                            }
                            this.bp = offset7;
                            this.ch = charAt(this.bp);
                            this.matchStat = 5;
                            this.token = 16;
                            return 0;
                        }
                    }
                }
            }
            this.matchStat = -1;
            return 0;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public double scanDouble(char seperator) throws NumberFormatException {
        int offset;
        char chLocal;
        boolean negative;
        int start;
        int count;
        double value;
        this.matchStat = 0;
        int offset2 = this.bp;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(offset2);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        boolean negative2 = chLocal2 == '-';
        if (negative2) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        char c = '0';
        if (chLocal2 >= '0') {
            char c2 = '9';
            if (chLocal2 <= '9') {
                long intVal = chLocal2 - '0';
                while (true) {
                    offset = offset3 + 1;
                    chLocal = charAt(offset3);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    intVal = (10 * intVal) + (chLocal - '0');
                    offset3 = offset;
                }
                long power = 1;
                boolean small = chLocal == '.';
                if (!small) {
                    negative = negative2;
                } else {
                    int offset4 = offset + 1;
                    char chLocal3 = charAt(offset);
                    if (chLocal3 >= '0' && chLocal3 <= '9') {
                        negative = negative2;
                        power = 10;
                        int offset5 = offset4;
                        intVal = (intVal * 10) + (chLocal3 - '0');
                        while (true) {
                            offset = offset5 + 1;
                            chLocal = charAt(offset5);
                            if (chLocal < c || chLocal > c2) {
                                break;
                            }
                            intVal = (intVal * 10) + (chLocal - '0');
                            power *= 10;
                            offset5 = offset;
                            c = '0';
                            c2 = '9';
                        }
                    } else {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                }
                boolean exp = chLocal == 'e' || chLocal == 'E';
                if (exp) {
                    int offset6 = offset + 1;
                    chLocal = charAt(offset);
                    if (chLocal == '+' || chLocal == '-') {
                        chLocal = charAt(offset6);
                        offset = offset6 + 1;
                    } else {
                        offset = offset6;
                    }
                    while (chLocal >= '0' && chLocal <= '9') {
                        chLocal = charAt(offset);
                        offset++;
                    }
                }
                if (!quote) {
                    start = this.bp;
                    count = (offset - start) - 1;
                } else {
                    if (chLocal != '\"') {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                    int offset7 = offset + 1;
                    chLocal = charAt(offset);
                    start = this.bp + 1;
                    count = (offset7 - start) - 2;
                    offset = offset7;
                }
                if (!exp && count < 18) {
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
                    this.bp = offset;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                }
                this.matchStat = -1;
                return value;
            }
        }
        if (chLocal2 == 'n') {
            int offset8 = offset3 + 1;
            if (charAt(offset3) == 117) {
                int offset9 = offset8 + 1;
                if (charAt(offset8) == 108) {
                    int offset10 = offset9 + 1;
                    if (charAt(offset9) == 108) {
                        this.matchStat = 5;
                        int offset11 = offset10 + 1;
                        char chLocal4 = charAt(offset10);
                        if (quote && chLocal4 == '\"') {
                            chLocal4 = charAt(offset11);
                            offset11++;
                        }
                        while (chLocal4 != ',') {
                            if (chLocal4 == ']') {
                                this.bp = offset11;
                                this.ch = charAt(this.bp);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0.0d;
                            }
                            if (isWhitespace(chLocal4)) {
                                chLocal4 = charAt(offset11);
                                offset11++;
                            } else {
                                this.matchStat = -1;
                                return 0.0d;
                            }
                        }
                        this.bp = offset11;
                        this.ch = charAt(this.bp);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0.0d;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0.0d;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char seperator) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        int offset2 = this.bp;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(offset2);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        char c = '0';
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            long value = chLocal2 - '0';
            while (true) {
                offset = offset3 + 1;
                chLocal = charAt(offset3);
                if (chLocal < c || chLocal > '9') {
                    break;
                }
                value = (10 * value) + (chLocal - '0');
                offset3 = offset;
                c = '0';
            }
            if (chLocal == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (quote) {
                if (chLocal == '\"') {
                    chLocal = charAt(offset);
                    offset++;
                } else {
                    this.matchStat = -1;
                    return 0L;
                }
            }
            boolean valid = value >= 0 || (value == Long.MIN_VALUE && negative);
            if (!valid) {
                this.matchStat = -1;
                return 0L;
            }
            while (chLocal != seperator) {
                if (isWhitespace(chLocal)) {
                    chLocal = charAt(offset);
                    offset++;
                } else {
                    this.matchStat = -1;
                    return value;
                }
            }
            this.bp = offset;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return negative ? -value : value;
        }
        if (chLocal2 == 'n') {
            int offset4 = offset3 + 1;
            if (charAt(offset3) == 117) {
                int offset5 = offset4 + 1;
                if (charAt(offset4) == 108) {
                    int offset6 = offset5 + 1;
                    if (charAt(offset5) == 108) {
                        this.matchStat = 5;
                        int offset7 = offset6 + 1;
                        char chLocal3 = charAt(offset6);
                        if (quote && chLocal3 == '\"') {
                            chLocal3 = charAt(offset7);
                            offset7++;
                        }
                        while (chLocal3 != ',') {
                            if (chLocal3 == ']') {
                                this.bp = offset7;
                                this.ch = charAt(this.bp);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0L;
                            }
                            if (isWhitespace(chLocal3)) {
                                chLocal3 = charAt(offset7);
                                offset7++;
                            } else {
                                this.matchStat = -1;
                                return 0L;
                            }
                        }
                        this.bp = offset7;
                        this.ch = charAt(this.bp);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0L;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0L;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public Date scanDate(char seperator) {
        Date dateVal;
        int index;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        int index2 = this.bp;
        int index3 = index2 + 1;
        char ch = charAt(index2);
        if (ch == '\"') {
            int endIndex = indexOf(Typography.quote, index3);
            if (endIndex == -1) {
                throw new JSONException("unclosed str");
            }
            int rest = endIndex - index3;
            this.bp = index3;
            if (scanISO8601DateIfMatch(false, rest)) {
                dateVal = this.calendar.getTime();
                ch = charAt(endIndex + 1);
                this.bp = startPos;
                while (ch != ',' && ch != ']') {
                    if (isWhitespace(ch)) {
                        endIndex++;
                        ch = charAt(endIndex + 1);
                    } else {
                        this.bp = startPos;
                        this.ch = startChar;
                        this.matchStat = -1;
                        return null;
                    }
                }
                this.bp = endIndex + 1;
                this.ch = ch;
            } else {
                this.bp = startPos;
                this.ch = startChar;
                this.matchStat = -1;
                return null;
            }
        } else {
            char c = '9';
            char c2 = '0';
            if (ch == '-' || (ch >= '0' && ch <= '9')) {
                long millis = 0;
                boolean negative = false;
                if (ch == '-') {
                    ch = charAt(index3);
                    negative = true;
                    index3++;
                }
                if (ch >= '0' && ch <= '9') {
                    millis = ch - '0';
                    while (true) {
                        index = index3 + 1;
                        ch = charAt(index3);
                        if (ch < c2 || ch > c) {
                            break;
                        }
                        millis = (10 * millis) + (ch - '0');
                        index3 = index;
                        c = '9';
                        c2 = '0';
                    }
                    if (ch == ',' || ch == ']') {
                        this.bp = index - 1;
                    }
                }
                if (millis < 0) {
                    this.bp = startPos;
                    this.ch = startChar;
                    this.matchStat = -1;
                    return null;
                }
                if (negative) {
                    millis = -millis;
                }
                dateVal = new Date(millis);
            } else {
                if (ch == 'n') {
                    int index4 = index3 + 1;
                    if (charAt(index3) == 117) {
                        int index5 = index4 + 1;
                        if (charAt(index4) == 108) {
                            int index6 = index5 + 1;
                            if (charAt(index5) == 108) {
                                ch = charAt(index6);
                                this.bp = index6;
                                dateVal = null;
                            }
                        }
                    }
                }
                this.bp = startPos;
                this.ch = startChar;
                this.matchStat = -1;
                return null;
            }
        }
        if (ch == ',') {
            int i = this.bp + 1;
            this.bp = i;
            this.ch = charAt(i);
            this.matchStat = 3;
            return dateVal;
        }
        int i2 = this.bp + 1;
        this.bp = i2;
        char ch2 = charAt(i2);
        if (ch2 == ',') {
            this.token = 16;
            int i3 = this.bp + 1;
            this.bp = i3;
            this.ch = charAt(i3);
        } else if (ch2 == ']') {
            this.token = 15;
            int i4 = this.bp + 1;
            this.bp = i4;
            this.ch = charAt(i4);
        } else if (ch2 == '}') {
            this.token = 13;
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = charAt(i5);
        } else if (ch2 == 26) {
            this.ch = JSONLexer.EOI;
            this.token = 20;
        } else {
            this.bp = startPos;
            this.ch = startChar;
            this.matchStat = -1;
            return null;
        }
        this.matchStat = 4;
        return dateVal;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void arrayCopy(int srcPos, char[] dest, int destPos, int length) {
        this.text.getChars(srcPos, srcPos + length, dest, destPos);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        StringBuilder buf = new StringBuilder();
        int line = 1;
        int column = 1;
        int i = 0;
        while (i < this.bp) {
            char ch = this.text.charAt(i);
            if (ch == '\n') {
                column = 1;
                line++;
            }
            i++;
            column++;
        }
        buf.append("pos ").append(this.bp).append(", line ").append(line).append(", column ").append(column);
        if (this.text.length() < 65535) {
            buf.append(this.text);
        } else {
            buf.append(this.text.substring(0, 65535));
        }
        return buf.toString();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String[] scanFieldStringArray(char[] fieldName, int argTypesCount, SymbolTable typeSymbolTable) {
        int offset;
        char ch;
        int startPos = this.bp;
        char starChar = this.ch;
        while (isWhitespace(this.ch)) {
            next();
        }
        String[] strArr = null;
        if (fieldName != null) {
            this.matchStat = 0;
            if (!charArrayCompare(fieldName)) {
                this.matchStat = -2;
                return null;
            }
            int offset2 = this.bp + fieldName.length;
            int offset3 = offset2 + 1;
            char ch2 = this.text.charAt(offset2);
            while (isWhitespace(ch2)) {
                ch2 = this.text.charAt(offset3);
                offset3++;
            }
            if (ch2 == ':') {
                offset = offset3 + 1;
                ch = this.text.charAt(offset3);
                while (isWhitespace(ch)) {
                    ch = this.text.charAt(offset);
                    offset++;
                }
            } else {
                this.matchStat = -1;
                return null;
            }
        } else {
            offset = this.bp + 1;
            ch = this.ch;
        }
        if (ch != '[') {
            if (ch == 'n' && this.text.startsWith("ull", this.bp + 1)) {
                this.bp += 4;
                this.ch = this.text.charAt(this.bp);
                return null;
            }
            this.matchStat = -1;
            return null;
        }
        this.bp = offset;
        this.ch = this.text.charAt(this.bp);
        String[] types = argTypesCount >= 0 ? new String[argTypesCount] : new String[4];
        int typeIndex = 0;
        while (true) {
            if (isWhitespace(this.ch)) {
                next();
            } else if (this.ch == '\"') {
                String type = scanSymbol(typeSymbolTable, Typography.quote);
                if (typeIndex == types.length) {
                    int newCapacity = types.length + (types.length >> 1) + 1;
                    String[] array = new String[newCapacity];
                    System.arraycopy(types, 0, array, 0, types.length);
                    types = array;
                }
                int typeIndex2 = typeIndex + 1;
                types[typeIndex] = type;
                while (isWhitespace(this.ch)) {
                    next();
                }
                if (this.ch == ',') {
                    next();
                    typeIndex = typeIndex2;
                    strArr = null;
                } else {
                    if (types.length != typeIndex2) {
                        String[] array2 = new String[typeIndex2];
                        System.arraycopy(types, 0, array2, 0, typeIndex2);
                        types = array2;
                    }
                    while (isWhitespace(this.ch)) {
                        next();
                    }
                    if (this.ch == ']') {
                        next();
                        return types;
                    }
                    this.bp = startPos;
                    this.ch = starChar;
                    this.matchStat = -1;
                    return null;
                }
            } else {
                this.bp = startPos;
                this.ch = starChar;
                this.matchStat = -1;
                return strArr;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean matchField2(char[] fieldName) {
        while (isWhitespace(this.ch)) {
            next();
        }
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return false;
        }
        int offset = this.bp + fieldName.length;
        int offset2 = offset + 1;
        char ch = this.text.charAt(offset);
        while (isWhitespace(ch)) {
            ch = this.text.charAt(offset2);
            offset2++;
        }
        if (ch == ':') {
            this.bp = offset2;
            this.ch = charAt(this.bp);
            return true;
        }
        this.matchStat = -2;
        return false;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject() {
        skipObject(false);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject(boolean valid) {
        boolean quote = false;
        int braceCnt = 0;
        int i = this.bp;
        while (i < this.text.length()) {
            char ch = this.text.charAt(i);
            if (ch == '\\') {
                if (i < this.len - 1) {
                    i++;
                } else {
                    this.ch = ch;
                    this.bp = i;
                    throw new JSONException("illegal str, " + info());
                }
            } else if (ch == '\"') {
                quote = !quote;
            } else if (ch == '{') {
                if (!quote) {
                    braceCnt++;
                }
            } else if (ch == '}' && !quote && braceCnt - 1 == -1) {
                this.bp = i + 1;
                int i2 = this.bp;
                int length = this.text.length();
                char cCharAt = JSONLexer.EOI;
                if (i2 == length) {
                    this.ch = JSONLexer.EOI;
                    this.token = 20;
                    return;
                }
                this.ch = this.text.charAt(this.bp);
                if (this.ch != ',') {
                    if (this.ch == '}') {
                        this.token = 13;
                        next();
                        return;
                    } else if (this.ch == ']') {
                        this.token = 15;
                        next();
                        return;
                    } else {
                        nextToken(16);
                        return;
                    }
                }
                this.token = 16;
                int index = this.bp + 1;
                this.bp = index;
                if (index < this.text.length()) {
                    cCharAt = this.text.charAt(index);
                }
                this.ch = cCharAt;
                return;
            }
            i++;
        }
        for (int j = 0; j < this.bp; j++) {
            if (j < this.text.length() && this.text.charAt(j) == ' ') {
                i++;
            }
        }
        if (i == this.text.length()) {
            throw new JSONException("illegal str, " + info());
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipArray() {
        skipArray(false);
    }

    public final void skipArray(boolean valid) {
        boolean quote = false;
        int bracketCnt = 0;
        int i = this.bp;
        while (i < this.text.length()) {
            char ch = this.text.charAt(i);
            if (ch == '\\') {
                if (i < this.len - 1) {
                    i++;
                } else {
                    this.ch = ch;
                    this.bp = i;
                    throw new JSONException("illegal str, " + info());
                }
            } else if (ch == '\"') {
                quote = !quote;
            } else if (ch == '[') {
                if (!quote) {
                    bracketCnt++;
                }
            } else {
                char cCharAt = JSONLexer.EOI;
                if (ch == '{' && valid) {
                    int index = this.bp + 1;
                    this.bp = index;
                    if (index < this.text.length()) {
                        cCharAt = this.text.charAt(index);
                    }
                    this.ch = cCharAt;
                    skipObject(valid);
                } else if (ch == ']' && !quote && bracketCnt - 1 == -1) {
                    this.bp = i + 1;
                    if (this.bp == this.text.length()) {
                        this.ch = JSONLexer.EOI;
                        this.token = 20;
                        return;
                    } else {
                        this.ch = this.text.charAt(this.bp);
                        nextToken(16);
                        return;
                    }
                }
            }
            i++;
        }
        if (i == this.text.length()) {
            throw new JSONException("illegal str, " + info());
        }
    }

    public final void skipString() {
        if (this.ch == '\"') {
            int i = this.bp;
            while (true) {
                i++;
                if (i < this.text.length()) {
                    char c = this.text.charAt(i);
                    if (c == '\\') {
                        if (i < this.len - 1) {
                            i++;
                        }
                    } else if (c == '\"') {
                        String str = this.text;
                        int i2 = i + 1;
                        this.bp = i2;
                        this.ch = str.charAt(i2);
                        return;
                    }
                } else {
                    throw new JSONException("unclosed str");
                }
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0095, code lost:
    
        if (r4 == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x009d, code lost:
    
        throw new com.alibaba.fastjson.JSONException("illegal json.");
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean seekArrayToItem(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index must > 0, but " + index);
        }
        if (this.token == 20) {
            return false;
        }
        if (this.token != 14) {
            throw new UnsupportedOperationException();
        }
        for (int i = 0; i < index; i++) {
            skipWhitespace();
            if (this.ch == '\"' || this.ch == '\'') {
                skipString();
                if (this.ch == ',') {
                    next();
                } else {
                    if (this.ch == ']') {
                        next();
                        nextToken(16);
                        return false;
                    }
                    throw new JSONException("illegal json.");
                }
            } else {
                if (this.ch == '{') {
                    next();
                    this.token = 12;
                    skipObject(false);
                } else if (this.ch == '[') {
                    next();
                    this.token = 14;
                    skipArray(false);
                } else {
                    boolean match = false;
                    int j = this.bp + 1;
                    while (true) {
                        if (j >= this.text.length()) {
                            break;
                        }
                        char c = this.text.charAt(j);
                        if (c == ',') {
                            match = true;
                            this.bp = j + 1;
                            this.ch = charAt(this.bp);
                            break;
                        }
                        if (c != ']') {
                            j++;
                        } else {
                            this.bp = j + 1;
                            this.ch = charAt(this.bp);
                            nextToken();
                            return false;
                        }
                    }
                }
                if (this.token != 16) {
                    if (this.token == 15) {
                        return false;
                    }
                    throw new UnsupportedOperationException();
                }
            }
        }
        nextToken();
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public int seekObjectToField(long fieldNameHash, boolean deepScan) {
        int i = -1;
        if (this.token == 20) {
            return -1;
        }
        int i2 = 13;
        if (this.token != 13) {
            int i3 = 15;
            if (this.token != 15) {
                int i4 = 16;
                if (this.token != 12 && this.token != 16) {
                    throw new UnsupportedOperationException(JSONToken.name(this.token));
                }
                while (this.ch != '}') {
                    if (this.ch == 26) {
                        return i;
                    }
                    if (this.ch != '\"') {
                        skipWhitespace();
                    }
                    if (this.ch == '\"') {
                        long hash = TypeUtils.fnv1a_64_magic_hashcode;
                        int i5 = this.bp + 1;
                        while (true) {
                            if (i5 >= this.text.length()) {
                                break;
                            }
                            char c = this.text.charAt(i5);
                            if (c == '\\') {
                                i5++;
                                if (i5 == this.text.length()) {
                                    throw new JSONException("unclosed str, " + info());
                                }
                                c = this.text.charAt(i5);
                            }
                            if (c == '\"') {
                                this.bp = i5 + 1;
                                this.ch = this.bp >= this.text.length() ? JSONLexer.EOI : this.text.charAt(this.bp);
                            } else {
                                hash = (hash ^ c) * TypeUtils.fnv1a_64_magic_prime;
                                i5++;
                            }
                        }
                        if (hash == fieldNameHash) {
                            if (this.ch != ':') {
                                skipWhitespace();
                            }
                            if (this.ch == ':') {
                                int index = this.bp + 1;
                                this.bp = index;
                                this.ch = index >= this.text.length() ? JSONLexer.EOI : this.text.charAt(index);
                                if (this.ch == 44) {
                                    int index2 = this.bp + 1;
                                    this.bp = index2;
                                    this.ch = index2 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(index2);
                                    this.token = i4;
                                    return 3;
                                }
                                if (this.ch == ']') {
                                    int index3 = this.bp + 1;
                                    this.bp = index3;
                                    this.ch = index3 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(index3);
                                    this.token = i3;
                                    return 3;
                                }
                                if (this.ch == '}') {
                                    int index4 = this.bp + 1;
                                    this.bp = index4;
                                    this.ch = index4 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(index4);
                                    this.token = i2;
                                    return 3;
                                }
                                if (this.ch >= '0' && this.ch <= '9') {
                                    this.sp = 0;
                                    this.pos = this.bp;
                                    scanNumber();
                                    return 3;
                                }
                                nextToken(2);
                                return 3;
                            }
                            return 3;
                        }
                        if (this.ch != ':') {
                            skipWhitespace();
                        }
                        if (this.ch == ':') {
                            int index5 = this.bp + 1;
                            this.bp = index5;
                            this.ch = index5 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(index5);
                            if (this.ch != '\"' && this.ch != '\'' && this.ch != '{' && this.ch != '[' && this.ch != '0' && this.ch != '1' && this.ch != '2' && this.ch != '3' && this.ch != '4' && this.ch != '5' && this.ch != '6' && this.ch != '7' && this.ch != '8' && this.ch != '9' && this.ch != '+' && this.ch != '-') {
                                skipWhitespace();
                            }
                            if (this.ch != '-' && this.ch != '+') {
                                if (this.ch < '0' || this.ch > '9') {
                                    if (this.ch == '\"') {
                                        skipString();
                                        if (this.ch != ',' && this.ch != '}') {
                                            skipWhitespace();
                                        }
                                        if (this.ch == ',') {
                                            next();
                                        }
                                    } else if (this.ch == 't') {
                                        next();
                                        if (this.ch == 'r') {
                                            next();
                                            if (this.ch == 'u') {
                                                next();
                                                if (this.ch == 'e') {
                                                    next();
                                                }
                                            }
                                        }
                                        if (this.ch != ',' && this.ch != '}') {
                                            skipWhitespace();
                                        }
                                        if (this.ch == ',') {
                                            next();
                                        }
                                    } else if (this.ch == 'n') {
                                        next();
                                        if (this.ch == 'u') {
                                            next();
                                            if (this.ch == 'l') {
                                                next();
                                                if (this.ch == 'l') {
                                                    next();
                                                }
                                            }
                                        }
                                        if (this.ch != ',' && this.ch != '}') {
                                            skipWhitespace();
                                        }
                                        if (this.ch == ',') {
                                            next();
                                        }
                                    } else if (this.ch == 'f') {
                                        next();
                                        if (this.ch == 'a') {
                                            next();
                                            if (this.ch == 'l') {
                                                next();
                                                if (this.ch == 's') {
                                                    next();
                                                    if (this.ch == 'e') {
                                                        next();
                                                    }
                                                }
                                            }
                                        }
                                        if (this.ch != ',' && this.ch != '}') {
                                            skipWhitespace();
                                        }
                                        if (this.ch == ',') {
                                            next();
                                        }
                                    } else if (this.ch == '{') {
                                        int index6 = this.bp + 1;
                                        this.bp = index6;
                                        this.ch = index6 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(index6);
                                        if (!deepScan) {
                                            skipObject(false);
                                            if (this.token == 13) {
                                                return -1;
                                            }
                                        } else {
                                            this.token = 12;
                                            return 1;
                                        }
                                    } else if (this.ch == '[') {
                                        next();
                                        if (deepScan) {
                                            this.token = 14;
                                            return 2;
                                        }
                                        skipArray(false);
                                        if (this.token == 13) {
                                            return -1;
                                        }
                                    } else {
                                        throw new UnsupportedOperationException();
                                    }
                                    i2 = 13;
                                    i = -1;
                                    i3 = 15;
                                    i4 = 16;
                                }
                            }
                            next();
                            while (this.ch >= '0' && this.ch <= '9') {
                                next();
                            }
                            if (this.ch == '.') {
                                next();
                                while (this.ch >= '0' && this.ch <= '9') {
                                    next();
                                }
                            }
                            if (this.ch == 'E' || this.ch == 'e') {
                                next();
                                if (this.ch == '-' || this.ch == '+') {
                                    next();
                                }
                                while (this.ch >= '0' && this.ch <= '9') {
                                    next();
                                }
                            }
                            if (this.ch != ',') {
                                skipWhitespace();
                            }
                            if (this.ch == ',') {
                                next();
                            }
                            i2 = 13;
                            i = -1;
                            i3 = 15;
                            i4 = 16;
                        } else {
                            throw new JSONException("illegal json, " + info());
                        }
                    } else {
                        throw new UnsupportedOperationException();
                    }
                }
                next();
                nextToken();
                return i;
            }
        }
        nextToken();
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01a3, code lost:
    
        if (r16.ch == '{') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x01a7, code lost:
    
        if (r16.ch == '[') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01ab, code lost:
    
        if (r16.ch == '0') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01b1, code lost:
    
        if (r16.ch == '1') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01b7, code lost:
    
        if (r16.ch == '2') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x01bd, code lost:
    
        if (r16.ch == '3') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x01c3, code lost:
    
        if (r16.ch == '4') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x01c9, code lost:
    
        if (r16.ch == '5') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x01cf, code lost:
    
        if (r16.ch == '6') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x01d5, code lost:
    
        if (r16.ch == '7') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x01db, code lost:
    
        if (r16.ch == '8') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x01df, code lost:
    
        if (r16.ch == '9') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x01e3, code lost:
    
        if (r16.ch == '+') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x01e7, code lost:
    
        if (r16.ch == '-') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x01e9, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x01ee, code lost:
    
        if (r16.ch == '-') goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x01f2, code lost:
    
        if (r16.ch == '+') goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x01f6, code lost:
    
        if (r16.ch < '0') goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x01fa, code lost:
    
        if (r16.ch > '9') goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x01ff, code lost:
    
        if (r16.ch != '\"') goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0201, code lost:
    
        skipString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0206, code lost:
    
        if (r16.ch == ',') goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x020a, code lost:
    
        if (r16.ch == '}') goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x020c, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x0211, code lost:
    
        if (r16.ch != ',') goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0213, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x021a, code lost:
    
        if (r16.ch != '{') goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x021c, code lost:
    
        r3 = r16.bp + 1;
        r16.bp = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0228, code lost:
    
        if (r3 < r16.text.length()) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x022a, code lost:
    
        r6 = com.alibaba.fastjson.parser.JSONLexer.EOI;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x022d, code lost:
    
        r6 = r16.text.charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0233, code lost:
    
        r16.ch = r6;
        skipObject(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x023e, code lost:
    
        if (r16.ch != '[') goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0240, code lost:
    
        next();
        skipArray(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x024c, code lost:
    
        throw new java.lang.UnsupportedOperationException();
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x024d, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0252, code lost:
    
        if (r16.ch < '0') goto L217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0256, code lost:
    
        if (r16.ch > '9') goto L218;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0258, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0260, code lost:
    
        if (r16.ch != '.') goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0262, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0267, code lost:
    
        if (r16.ch < '0') goto L219;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x026b, code lost:
    
        if (r16.ch > '9') goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x026d, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0275, code lost:
    
        if (r16.ch == 'E') goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x027b, code lost:
    
        if (r16.ch != 'e') goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x027d, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0282, code lost:
    
        if (r16.ch == '-') goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0286, code lost:
    
        if (r16.ch != '+') goto L223;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x0288, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x028d, code lost:
    
        if (r16.ch < '0') goto L221;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0291, code lost:
    
        if (r16.ch > '9') goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0293, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x0299, code lost:
    
        if (r16.ch == ',') goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x029b, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x02a0, code lost:
    
        if (r16.ch != ',') goto L211;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x02a2, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x02a5, code lost:
    
        r4 = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x02c5, code lost:
    
        throw new com.alibaba.fastjson.JSONException("illegal json, " + info());
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b0, code lost:
    
        r2 = -1;
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b4, code lost:
    
        if (r10 >= r17.length) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ba, code lost:
    
        if (r8 != r17[r10]) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00bc, code lost:
    
        r2 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00be, code lost:
    
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00ca, code lost:
    
        if (r2 == (-1)) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00ce, code lost:
    
        if (r16.ch == ':') goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00d0, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00d5, code lost:
    
        if (r16.ch != ':') goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00d7, code lost:
    
        r5 = r16.bp + 1;
        r16.bp = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00e3, code lost:
    
        if (r5 < r16.text.length()) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00e5, code lost:
    
        r7 = com.alibaba.fastjson.parser.JSONLexer.EOI;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00e8, code lost:
    
        r7 = r16.text.charAt(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00ee, code lost:
    
        r16.ch = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00f2, code lost:
    
        if (r16.ch != 44) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00f4, code lost:
    
        r3 = r16.bp + 1;
        r16.bp = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0100, code lost:
    
        if (r3 < r16.text.length()) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0103, code lost:
    
        r6 = r16.text.charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0109, code lost:
    
        r16.ch = r6;
        r16.token = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0112, code lost:
    
        if (r16.ch != ']') goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0114, code lost:
    
        r3 = r16.bp + 1;
        r16.bp = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0120, code lost:
    
        if (r3 < r16.text.length()) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0123, code lost:
    
        r6 = r16.text.charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0129, code lost:
    
        r16.ch = r6;
        r16.token = 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0132, code lost:
    
        if (r16.ch != '}') goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0134, code lost:
    
        r3 = r16.bp + 1;
        r16.bp = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0140, code lost:
    
        if (r3 < r16.text.length()) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0143, code lost:
    
        r6 = r16.text.charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0149, code lost:
    
        r16.ch = r6;
        r16.token = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0152, code lost:
    
        if (r16.ch < '0') goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0156, code lost:
    
        if (r16.ch > '9') goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0158, code lost:
    
        r16.sp = 0;
        r16.pos = r16.bp;
        scanNumber();
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0162, code lost:
    
        nextToken(2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0166, code lost:
    
        r16.matchStat = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0169, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x016c, code lost:
    
        if (r16.ch == ':') goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x016e, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0173, code lost:
    
        if (r16.ch != ':') goto L204;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0175, code lost:
    
        r5 = r16.bp + 1;
        r16.bp = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0181, code lost:
    
        if (r5 < r16.text.length()) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0183, code lost:
    
        r11 = com.alibaba.fastjson.parser.JSONLexer.EOI;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0186, code lost:
    
        r11 = r16.text.charAt(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x018c, code lost:
    
        r16.ch = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0199, code lost:
    
        if (r16.ch == '\"') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x019f, code lost:
    
        if (r16.ch == '\'') goto L128;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int seekObjectToField(long[] fieldNameHash) {
        int i = 16;
        if (this.token != 12 && this.token != 16) {
            throw new UnsupportedOperationException();
        }
        while (this.ch != '}') {
            char c = this.ch;
            char cCharAt = JSONLexer.EOI;
            if (c == 26) {
                this.matchStat = -1;
                return -1;
            }
            if (this.ch != '\"') {
                skipWhitespace();
            }
            if (this.ch == '\"') {
                long hash = TypeUtils.fnv1a_64_magic_hashcode;
                int i2 = this.bp;
                while (true) {
                    i2++;
                    if (i2 >= this.text.length()) {
                        break;
                    }
                    char c2 = this.text.charAt(i2);
                    if (c2 == '\\') {
                        i2++;
                        if (i2 == this.text.length()) {
                            throw new JSONException("unclosed str, " + info());
                        }
                        c2 = this.text.charAt(i2);
                    }
                    if (c2 == '\"') {
                        this.bp = i2 + 1;
                        this.ch = this.bp >= this.text.length() ? JSONLexer.EOI : this.text.charAt(this.bp);
                    } else {
                        hash = (hash ^ c2) * TypeUtils.fnv1a_64_magic_prime;
                    }
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
        next();
        nextToken();
        this.matchStat = -1;
        return -1;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public String scanTypeName(SymbolTable symbolTable) {
        int p;
        if (!this.text.startsWith("\"@type\":\"", this.bp) || (p = this.text.indexOf(34, this.bp + 9)) == -1) {
            return null;
        }
        this.bp += 9;
        int h = 0;
        for (int i = this.bp; i < p; i++) {
            h = (h * 31) + this.text.charAt(i);
        }
        int i2 = this.bp;
        String typeName = addSymbol(i2, p - this.bp, h, symbolTable);
        char separator = this.text.charAt(p + 1);
        if (separator != ',' && separator != ']') {
            return null;
        }
        this.bp = p + 2;
        this.ch = this.text.charAt(this.bp);
        return typeName;
    }
}
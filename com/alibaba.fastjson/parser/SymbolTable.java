package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;

/* loaded from: classes.dex */
public class SymbolTable {
    private final int indexMask;
    private final String[] symbols;

    public SymbolTable(int tableSize) {
        this.indexMask = tableSize - 1;
        this.symbols = new String[tableSize];
        addSymbol("$ref", 0, 4, "$ref".hashCode());
        addSymbol(JSON.DEFAULT_TYPE_KEY, 0, JSON.DEFAULT_TYPE_KEY.length(), JSON.DEFAULT_TYPE_KEY.hashCode());
    }

    public String addSymbol(char[] buffer, int offset, int len) {
        int hash = hash(buffer, offset, len);
        return addSymbol(buffer, offset, len, hash);
    }

    public String addSymbol(char[] buffer, int offset, int len, int hash) {
        int bucket = this.indexMask & hash;
        String symbol = this.symbols[bucket];
        if (symbol != null) {
            boolean eq = true;
            if (hash == symbol.hashCode() && len == symbol.length()) {
                int i = 0;
                while (true) {
                    if (i >= len) {
                        break;
                    }
                    if (buffer[offset + i] == symbol.charAt(i)) {
                        i++;
                    } else {
                        eq = false;
                        break;
                    }
                }
            } else {
                eq = false;
            }
            if (eq) {
                return symbol;
            }
            return new String(buffer, offset, len);
        }
        String symbol2 = new String(buffer, offset, len).intern();
        this.symbols[bucket] = symbol2;
        return symbol2;
    }

    public String addSymbol(String buffer, int offset, int len, int hash) {
        return addSymbol(buffer, offset, len, hash, false);
    }

    public String addSymbol(String buffer, int offset, int len, int hash, boolean replace) {
        int bucket = this.indexMask & hash;
        String symbol = this.symbols[bucket];
        if (symbol != null) {
            if (hash == symbol.hashCode() && len == symbol.length() && buffer.startsWith(symbol, offset)) {
                return symbol;
            }
            String str = subString(buffer, offset, len);
            if (replace) {
                this.symbols[bucket] = str;
            }
            return str;
        }
        String symbol2 = (len == buffer.length() ? buffer : subString(buffer, offset, len)).intern();
        this.symbols[bucket] = symbol2;
        return symbol2;
    }

    private static String subString(String src, int offset, int len) {
        char[] chars = new char[len];
        src.getChars(offset, offset + len, chars, 0);
        return new String(chars);
    }

    public static int hash(char[] buffer, int offset, int len) {
        int h = 0;
        int off = offset;
        int i = 0;
        while (i < len) {
            h = (h * 31) + buffer[off];
            i++;
            off++;
        }
        return h;
    }
}
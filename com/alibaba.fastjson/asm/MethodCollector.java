package com.alibaba.fastjson.asm;

/* loaded from: classes.dex */
public class MethodCollector {
    protected boolean debugInfoPresent;
    private final int ignoreCount;
    private final int paramCount;
    private final StringBuilder result = new StringBuilder();
    private int currentParameter = 0;

    protected MethodCollector(int ignoreCount, int paramCount) {
        this.ignoreCount = ignoreCount;
        this.paramCount = paramCount;
        this.debugInfoPresent = paramCount == 0;
    }

    protected void visitLocalVariable(String name, int index) {
        if (index >= this.ignoreCount && index < this.ignoreCount + this.paramCount) {
            if (!name.equals("arg" + this.currentParameter)) {
                this.debugInfoPresent = true;
            }
            this.result.append(',');
            this.result.append(name);
            this.currentParameter++;
        }
    }

    protected String getResult() {
        return this.result.length() != 0 ? this.result.substring(1) : "";
    }
}
package com.alibaba.fastjson.asm;

import kotlin.jvm.internal.IntCompanionObject;

/* loaded from: classes.dex */
final class Item {
    int hashCode;
    int index;
    int intVal;
    long longVal;
    Item next;
    String strVal1;
    String strVal2;
    String strVal3;
    int type;

    Item() {
    }

    Item(int index, Item i) {
        this.index = index;
        this.type = i.type;
        this.intVal = i.intVal;
        this.longVal = i.longVal;
        this.strVal1 = i.strVal1;
        this.strVal2 = i.strVal2;
        this.strVal3 = i.strVal3;
        this.hashCode = i.hashCode;
    }

    void set(int type, String strVal1, String strVal2, String strVal3) {
        this.type = type;
        this.strVal1 = strVal1;
        this.strVal2 = strVal2;
        this.strVal3 = strVal3;
        switch (type) {
            case 1:
            case 7:
            case 8:
            case 13:
                this.hashCode = Integer.MAX_VALUE & (strVal1.hashCode() + type);
                break;
            case 12:
                this.hashCode = Integer.MAX_VALUE & ((strVal1.hashCode() * strVal2.hashCode()) + type);
                break;
            default:
                this.hashCode = Integer.MAX_VALUE & ((strVal1.hashCode() * strVal2.hashCode() * strVal3.hashCode()) + type);
                break;
        }
    }

    void set(int intVal) {
        this.type = 3;
        this.intVal = intVal;
        this.hashCode = (this.type + intVal) & IntCompanionObject.MAX_VALUE;
    }

    boolean isEqualTo(Item i) {
        switch (this.type) {
            case 1:
            case 7:
            case 8:
            case 13:
                return i.strVal1.equals(this.strVal1);
            case 2:
            case 9:
            case 10:
            case 11:
            case 14:
            default:
                return i.strVal1.equals(this.strVal1) && i.strVal2.equals(this.strVal2) && i.strVal3.equals(this.strVal3);
            case 3:
            case 4:
                return i.intVal == this.intVal;
            case 5:
            case 6:
            case 15:
                return i.longVal == this.longVal;
            case 12:
                return i.strVal1.equals(this.strVal1) && i.strVal2.equals(this.strVal2);
        }
    }
}
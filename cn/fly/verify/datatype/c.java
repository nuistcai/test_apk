package cn.fly.verify.datatype;

import cn.fly.tools.proguard.EverythingKeeper;
import cn.fly.tools.utils.HashonHelper;
import java.io.Serializable;

/* loaded from: classes.dex */
public class c implements EverythingKeeper, Serializable {
    public String toJSONString() {
        return HashonHelper.fromObject(this);
    }
}
package cn.fly.mcl.tcp;

import cn.fly.commons.n;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class g {
    public static final String a = n.a("004>fedgbiZc");
    private static g b;
    private List<Map<String, Object>> c;

    private g() {
    }

    public static g a() {
        if (b == null) {
            synchronized (g.class) {
                if (b == null) {
                    b = new g();
                }
            }
        }
        return b;
    }

    public boolean b() {
        return (this.c == null || this.c.isEmpty()) ? false : true;
    }

    public List<Map<String, Object>> c() {
        return this.c;
    }

    public void a(Map<String, Object> map) {
        if (this.c == null) {
            this.c = new ArrayList();
        }
        this.c.add(map);
    }

    public void b(Map<String, Object> map) {
        if (this.c != null && this.c.contains(map)) {
            this.c.remove(map);
        }
    }
}
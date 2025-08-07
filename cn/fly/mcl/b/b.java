package cn.fly.mcl.b;

import cn.fly.commons.o;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class b {
    private HashMap<String, Object> a;
    private boolean b;

    public HashMap<String, Object> a() {
        return this.a;
    }

    public b(HashMap<String, Object> map) {
        this.a = map;
        this.b = false;
    }

    public b(HashMap<String, Object> map, boolean z) {
        this.a = map;
        this.b = z;
    }

    public int b() throws IOException {
        if (this.a == null) {
            return -1;
        }
        return ((Integer) this.a.get(o.a("004c dkdc9f"))).intValue();
    }

    public InputStream c() throws IOException {
        return new ByteArrayInputStream(f());
    }

    public InputStream d() throws IOException {
        return new ByteArrayInputStream(f());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Map<String, List<String>> e() throws IOException {
        HashMap map;
        HashMap map2 = new HashMap();
        a(map2, "apc", String.valueOf(this.b));
        if (this.a != null && this.a.containsKey("headers") && (map = (HashMap) this.a.get("headers")) != null) {
            for (Map.Entry entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add((String) entry.getValue());
                    map2.put(entry.getKey(), arrayList);
                } else if (entry.getValue() instanceof List) {
                    map2.put(entry.getKey(), (List) entry.getValue());
                }
            }
        }
        return map2;
    }

    private byte[] f() {
        String str;
        if (this.a != null && this.a.containsKey("body")) {
            str = (String) this.a.get("body");
        } else {
            str = "{}";
        }
        return str.getBytes();
    }

    private void a(HashMap<String, List<String>> map, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        map.put(str, arrayList);
    }
}
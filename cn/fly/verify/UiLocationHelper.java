package cn.fly.verify;

import cn.fly.tools.proguard.PublicMemberKeeper;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class UiLocationHelper implements PublicMemberKeeper {
    private static UiLocationHelper instance;
    private HashMap<String, List<Integer>> viewLocations;

    private UiLocationHelper() {
    }

    public static UiLocationHelper getInstance() {
        if (instance == null) {
            synchronized (UiLocationHelper.class) {
                if (instance == null) {
                    instance = new UiLocationHelper();
                }
            }
        }
        return instance;
    }

    public HashMap<String, List<Integer>> getViewLocations() {
        return this.viewLocations;
    }

    public void setViewLocations(HashMap<String, List<Integer>> map) {
        this.viewLocations = map;
    }
}
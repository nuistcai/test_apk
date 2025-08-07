package cn.fly.tools.utils;

import cn.fly.tools.proguard.EverythingKeeper;
import cn.fly.tools.utils.ExecutorDispatcher;

/* loaded from: classes.dex */
public interface IExecutor extends EverythingKeeper {
    <T extends ExecutorDispatcher.SafeRunnable> void executeDelayed(T t, long j);

    <T extends ExecutorDispatcher.SafeRunnable> void executeDuctile(T t);

    <T extends ExecutorDispatcher.SafeRunnable> void executeImmediately(T t);

    <T extends ExecutorDispatcher.SafeRunnable> void executeSerial(T t);
}
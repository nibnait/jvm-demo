package cc.tianbin.demo.jvm._native;

import cc.tianbin.demo.jvm._native.java.lang.*;
import io.github.nibnait.common.utils.DataUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nibnait on 2022/12/13
 */
public class Registry {

    private Registry() {
        throw new AssertionError("工具类不允许被实例化");
    }

    /**
     * 初始化本地方法
     */
    public static void initNative() {
        new _Class();
        new _Double();
        new _Float();
        new _Object();
        new _String();
        new _System();
    }

    /**
     * key   类名~方法名~方法描述符
     * value 本地方法的实现
     */
    private static Map<String, NativeMethod> registry = new HashMap<>();

    private static final String KEY_FORMAT = "{}~{}~{}";

    public static String formatKey(String className, String methodName, String methodDescriptor) {
        return DataUtils.format(KEY_FORMAT, className, methodName, methodDescriptor);
    }

    public static void register(String className, String methodName, String methodDescriptor, NativeMethod method) {
        String key = formatKey(className, methodName, methodDescriptor);
        registry.put(key, method);
    }

    public static NativeMethod findNativeMethod(String className, String methodName, String methodDescriptor) {
        String key = formatKey(className, methodName, methodDescriptor);
        return registry.get(key);
    }

}

package cc.tianbin.demo.jvm.utils;

import io.github.nibnait.common.utils.DataUtils;

/**
 * Created by nibnait on 2022/12/07
 */
public class LogUtil {

    private LogUtil() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static void printf(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static void log(String format, Object... args) {
        System.out.println(DataUtils.format(format, args));
    }
}

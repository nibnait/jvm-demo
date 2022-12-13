package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.rtda.heap.classloader.ClassLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nibnait on 2022/12/13
 */
public class StringPool {

    private StringPool() {
        throw new AssertionError("工具类不允许被实例化");
    }

    /**
     * class文件中 字符串是以 MUTF8 格式保存的
     * JVM运行期间，字符串以 java.lang.String 对象的形式存在，在 String 对象内部以 UTF-16 格式保存
     *
     * 字符串常量池
     * key go字符串
     * value java字符串
     */
    private static Map<String, JVMMAObject> internedStrings = new HashMap<>();

    /**
     * 字符串对象是不可变的。（一旦构造好了，其内部的 value 字段就不会变了）
     */
    private static final String value = "value";

    /**
     * 根据Go字符串返回相应的Java字符串实例
     */
    public static JVMMAObject jString(ClassLoader loader, String goStr) {
        JVMMAObject internedStr = internedStrings.get(goStr);
        if (internedStr != null) {
            return internedStr;
        }

        char[] chars = goStr.toCharArray();
        JVMMAObject jChars = JVMMAObject.newObject(loader.loadClass("[C"), chars);
        // 跳过 String 的构造函数，直接创建实例
        JVMMAObject jStr = loader.loadClass("java/lang/String").newObject();
        jStr.setRefVal(value, "[C", jChars);

        internedStrings.put(goStr, jStr);
        return jStr;
    }

    /**
     * 通过 String(char value[]) 构造方法 实例化一个字符串
     */
    public static String goString(JVMMAObject jStr) {
        JVMMAObject charArr = jStr.getRefVar(value, "[C");
        return new String(charArr.chars());
    }


}

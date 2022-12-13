package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import java.util.Arrays;

/**
 * Created by nibnait on 2022/12/13
 */
public class JArrayObject {

    private JArrayObject() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static boolean checkArrayCopy(JObject src, JObject dest) {
        JClass srcClass = src.getClazz();
        JClass destClass = dest.getClazz();

        if (!srcClass.isArray() || !destClass.isArray()) {
            return false;
        }

        if (srcClass.componentClass().IsPrimitive() || destClass.componentClass().IsPrimitive()) {
            return srcClass == destClass;
        }

        return true;
    }

    public static Object arrayCopy(JObject src, int srcPos, int length) {
        Object data = src.data;
        if (data instanceof byte[]) {
            return Arrays.copyOfRange((byte[]) data, srcPos, srcPos + length);
        }

        if (data instanceof char[]) {
            return Arrays.copyOfRange((char[]) data, srcPos, srcPos + length);
        }

        if (data instanceof short[]) {
            return Arrays.copyOfRange((short[]) data, srcPos, srcPos + length);
        }

        if (data instanceof int[]) {
            return Arrays.copyOfRange((int[]) data, srcPos, srcPos + length);
        }

        if (data instanceof double[]) {
            return Arrays.copyOfRange((double[]) data, srcPos, srcPos + length);
        }

        if (data instanceof float[]) {
            return Arrays.copyOfRange((float[]) data, srcPos, srcPos + length);
        }

        if (data instanceof Object[]) {
            return Arrays.copyOfRange((Object[]) data, srcPos, srcPos + length);
        }

        throw new RuntimeException("Not array!");
    }

}

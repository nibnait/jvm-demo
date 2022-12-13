package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.exception.IllegalArgumentException;

import java.util.Map;

/**
 * Created by nibnait on 2022/12/13
 */
public class ClassNameHelper {

    private ClassNameHelper() {
        throw new AssertionError("工具类不允许被实例化");
    }

    /**
     * 根据类名 得到数组类名
     *
     * [XXX -> [[XXX
     * int -> [I
     * XXX -> [LXXX;
     */
    public static String getArrayClassName(String className) {
        return FieldDescriptor.A_REF.getCode() + toDescriptor(className);
    }

    private static String toDescriptor(String className) {
        if (className.getBytes()[0] == '[') {
            return className;
        }

        String d = FieldDescriptor.PRIMITIVE_TYPE.get(className);
        if (d != null) {
            return d;
        }

        return "L" + className + ";";
    }

    /**
     * 根据数组类名 推测出数组元素类名
     */
    public static String getComponentClassName(String className) {
        if (className.getBytes()[0] == '[') {
            String componentTypeDescriptor = className.substring(1);
            return toClassName(componentTypeDescriptor);
        }
        throw new IllegalArgumentException("Not array {}", className);
    }

    private static String toClassName(String descriptor) {
        byte descByte = descriptor.getBytes()[0];
        if (descByte == '[') {
            return descriptor;
        }

        if (descByte == 'L') {
            return descriptor.substring(1, descriptor.length() - 1);
        }

        for (Map.Entry<String, String> entry : FieldDescriptor.PRIMITIVE_TYPE.entrySet()) {
            if (entry.getValue().equals(descriptor)) {
                // primitive
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("Invalid descriptor {}", descriptor);
    }
}

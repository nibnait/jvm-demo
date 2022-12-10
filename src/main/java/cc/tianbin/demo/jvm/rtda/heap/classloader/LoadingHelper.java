package cc.tianbin.demo.jvm.rtda.heap.classloader;

import cc.tianbin.demo.jvm.classfile.ClassFile;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;

/**
 * 1. 加载（Loading）
 * Created by nibnait on 2022/12/08
 */
public class LoadingHelper {

    private LoadingHelper() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static Class loadClass(byte[] bytecode, ClassLoader classLoader) {
        // 解析字节码
        ClassFile classFile = new ClassFile(bytecode);
        Class clazz = new Class(classFile);
        clazz.setLoader(classLoader);

        // 处理父类
        resolveSuperClass(clazz);
        // 处理内部接口
        resolveInterfaces(clazz);
        return clazz;
    }

    private static void resolveSuperClass(Class clazz) {
        if (!clazz.getName().equals("java/lang/Object")) {
            clazz.setSuperClass(clazz.getLoader().loadClass(clazz.getSuperClassName()));
        }
    }

    private static void resolveInterfaces(Class clazz) {
        int interfaceCount = clazz.getInterfaceNames().length;
        if (interfaceCount > 0) {
            Class[] interfaces = new Class[interfaceCount];
            for (int i = 0; i < interfaceCount; i++) {
                interfaces[i] = clazz.getLoader().loadClass(clazz.getInterfaceNames()[i]);
            }
            clazz.setInterfaces(interfaces);
        }
    }
}

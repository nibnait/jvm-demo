package cc.tianbin.demo.jvm.rtda.heap.classloader;

import cc.tianbin.demo.jvm.classfile.ClassFile;
import cc.tianbin.demo.jvm.common.CommonConstants;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.utils.LogUtil;

/**
 * 1. 加载（Loading）
 * Created by nibnait on 2022/12/08
 */
public class LoadingHelper {

    private LoadingHelper() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static JClass loadClass(byte[] bytecode, JClassLoader classLoader) {
        // 解析字节码
        ClassFile classFile = new ClassFile(bytecode);
        if (classLoader.isVerboseClassFlag()) {
            LogUtil.printClassInfo(classFile);
        }

        JClass clazz = new JClass(classFile);
        clazz.setLoader(classLoader);

        // 处理父类
        resolveSuperClass(clazz);
        // 处理内部接口
        resolveInterfaces(clazz);
        return clazz;
    }

    private static void resolveSuperClass(JClass clazz) {
        if (!CommonConstants.JAVA_LANG_OBJECT.equals(clazz.getName())) {
            clazz.setSuperClass(clazz.getLoader().loadClass(clazz.getSuperClassName()));
        }
    }

    private static void resolveInterfaces(JClass clazz) {
        int interfaceCount = clazz.getInterfaceNames().length;
        if (interfaceCount > 0) {
            JClass[] interfaces = new JClass[interfaceCount];
            for (int i = 0; i < interfaceCount; i++) {
                interfaces[i] = clazz.getLoader().loadClass(clazz.getInterfaceNames()[i]);
            }
            clazz.setInterfaces(interfaces);
        }
    }
}

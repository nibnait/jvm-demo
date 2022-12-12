package cc.tianbin.demo.jvm.rtda.heap.methodarea;

/**
 * Created by nibnait on 2022/12/12
 */
public class MethodLookup {

    private MethodLookup() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static Method lookupMethodInClass(Class clazz, String name, String descriptor) {
        Class c = clazz;
        while (c != null) {
            for (Method method : c.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }
            c = c.getSuperClass();
        }
        return null;
    }

    public static Method lookupMethodInInterfaces(Class[] interfaces, String name, String descriptor) {
        if (interfaces == null || interfaces.length == 0) {
            return null;
        }

        for (Class c : interfaces) {
            for (Method method : c.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }

            Method method = lookupMethodInInterfaces(c.getInterfaces(), name, descriptor);
            if (method != null) {
                return method;
            }
        }
        return null;
    }

}

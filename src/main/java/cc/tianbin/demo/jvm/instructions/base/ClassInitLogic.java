package cc.tianbin.demo.jvm.instructions.base;

import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.Thread;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;

/**
 * Created by nibnait on 2022/12/12
 */
public class ClassInitLogic {

    private ClassInitLogic() {
        throw new AssertionError("工具类不允许被实例化");
    }

    /**
     * 类初始化就是执行类的 <clinit> (初始化方法)，类的初始化在下列情况下触发：
     * 1. 执行new指令创建类实例，但类还没有被初始化。
     * 2. 执行 putstatic, getstatic 指令存取类的静态变量，但声明该字段的类还没有被初始化。
     * 3. 执行 invokestatic 调用类的静态方法，但声明该方法的类还没有被初始化。
     * 4. 当初始化一个类时，如果类的超类还没有被初始化，要先初始化类的超类。
     * 5. 执行某些反射操作时。
     */
    public static void initClass(Thread thread, JClass clazz) {
        clazz.startInit();
        scheduleClinit(thread, clazz);
        initSuperClass(thread, clazz);
    }

    private static void scheduleClinit(Thread thread, JClass clazz) {
        Method clinit = clazz.getClinitMethod();
        if (clinit == null) {
            return;
        }
        Frame newFrame = thread.newFrame(clinit);
        thread.pushFrame(newFrame);
    }

    private static void initSuperClass(Thread thread, JClass clazz) {
        if (clazz.getAccessFlag().isInterface()) {
            return;
        }
        JClass superClass = clazz.getSuperClass();
        if (superClass != null && !superClass.isInitStarted()) {
            initClass(thread, superClass);
        }
    }
}

package cc.tianbin.demo.jvm._native.sun.misc;

import cc.tianbin.demo.jvm._native.NativeMethod;
import cc.tianbin.demo.jvm._native.Registry;
import cc.tianbin.demo.jvm.instructions.base.MethodInvokeLogic;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.heap.classloader.JClassLoader;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;

/**
 * Created by nibnait on 2022/12/13
 */
public class VM {

    /**
     * @see sun.misc.VM
     */
    private final String smVM = "sun/misc/VM";

    public VM() {
        Registry.register(smVM, "initialize", "()V", new NativeMethod("initialize", this));
    }

    /**
     * @see sun.misc.VM#initialize()
     * @see System#initializeSystemClass()
     */
    public void initialize(Frame frame) {
        JClassLoader classLoader = frame.method.getClazz().getLoader();
        JClass jlSysClass = classLoader.loadClass("java/lang/System");
        Method initSysClass = jlSysClass.getStaticMethod("initializeSystemClass", "()V", true);
        MethodInvokeLogic.invokeMethod(frame, initSysClass);
    }

}

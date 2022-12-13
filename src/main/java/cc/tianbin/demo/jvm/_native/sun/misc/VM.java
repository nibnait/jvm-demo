package cc.tianbin.demo.jvm._native.sun.misc;

import cc.tianbin.demo.jvm._native.NativeMethod;
import cc.tianbin.demo.jvm._native.Registry;
import cc.tianbin.demo.jvm.instructions.base.MethodInvokeLogic;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.StringPool;

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
     */
    public void initialize(Frame frame) {
        JClass vmClass = frame.method.getClazz();
        JObject savedProps = vmClass.getRefVar("savedProps", "Ljava/util/Properties;");
        JObject key = StringPool.jString(vmClass.getLoader(), "foo");
        JObject val = StringPool.jString(vmClass.getLoader(), "bar");

        frame.operandStack.pushRef(savedProps);
        frame.operandStack.pushRef(key);
        frame.operandStack.pushRef(val);

        JClass propsClass = vmClass.getLoader().loadClass("java/util/Properties");
        Method setPropMethod = propsClass.getInstanceMethod("setProperty",
                "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
        MethodInvokeLogic.invokeMethod(frame, setPropMethod);
    }

}

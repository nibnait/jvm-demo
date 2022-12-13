package cc.tianbin.demo.jvm._native.java.lang;

import cc.tianbin.demo.jvm._native.NativeMethod;
import cc.tianbin.demo.jvm._native.Registry;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.StringPool;

/**
 * Created by nibnait on 2022/12/13
 */
public class _String {

    /**
     * @see java.lang.String
     */
    private final String jlString = "java/lang/String";

    public _String() {
        Registry.register(jlString, "intern", "()Ljava/lang/String;", new NativeMethod("intern", this));
        Registry.register(jlString, "registerNatives", "()V", new NativeMethod("registerNatives", this));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    /**
     * @see String#intern()
     */
    public void intern(Frame frame) {
        JObject thiz = frame.localVariables.getThis();
        JObject interned = StringPool.internString(thiz);
        frame.operandStack.pushRef(interned);
    }

}

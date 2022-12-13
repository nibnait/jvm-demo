package cc.tianbin.demo.jvm._native.java.lang;

import cc.tianbin.demo.jvm._native.NativeMethod;
import cc.tianbin.demo.jvm._native.Registry;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/13
 */
public class _Object {

    /**
     * @see java.lang.Object
     */
    private final String jlObject = "java/lang/Object";

    public _Object() {
        Registry.register(jlObject, "getClass", "()Ljava/lang/Class;", new NativeMethod("getClass", this));
        Registry.register(jlObject, "hashCode", "()I", new NativeMethod("_hashCode", this));
        Registry.register(jlObject, "clone", "()Ljava/lang/Object;", new NativeMethod("_clone", this));
        Registry.register(jlObject, "registerNatives", "()V", new NativeMethod("registerNatives", this));
    }

    /**
     * @see Object#registerNatives()
     */
    public void registerNatives(Frame frame) {
        // do nothing
    }

    /**
     * @see Object#getClass()
     * <p>
     * public final native Class<?> getClass();
     * ()Ljava/lang/Class;
     */
    public void getClass(Frame frame) {
        // getRef(0) 拿到 this 引用
        JObject thiz = frame.localVariables.getThis();
        // 拿到类对象
        JObject clazz = thiz.getClazz().jClass;
        // 吧类对象推到栈顶
        frame.operandStack.pushRef(clazz);
    }

    /**
     * @see Object#hashCode()
     * <p>
     * public native int hashCode();
     * ()I
     */
    public void hashCode(Frame frame) {
        JObject thiz = frame.localVariables.getThis();
        frame.operandStack.pushInt(thiz.hashCode());
    }

    /**
     * @see Object#clone()
     * <p>
     * protected native Object clone() throws CloneNotSupportedException;
     * ()Ljava/lang/Object;
     */
    public void clone(Frame frame) throws CloneNotSupportedException {
        JObject thiz = frame.localVariables.getThis();

        JClass cloneable = thiz.getClazz().getLoader().loadClass("java/lang/Cloneable");

        if (!thiz.getClazz().isImplements(cloneable)) {
            throw new CloneNotSupportedException();
        }

        frame.operandStack.pushRef(thiz);
    }

}

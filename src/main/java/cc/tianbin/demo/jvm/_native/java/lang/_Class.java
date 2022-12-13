package cc.tianbin.demo.jvm._native.java.lang;

import cc.tianbin.demo.jvm._native.NativeMethod;
import cc.tianbin.demo.jvm._native.Registry;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.LocalVariables;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.classloader.JClassLoader;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.StringPool;

/**
 * Created by nibnait on 2022/12/13
 */
public class _Class {

    /**
     * @see java.lang.Class
     */
    private final String jlClass = "java/lang/Class";

    public _Class() {
        Registry.register(jlClass, "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;", new NativeMethod( "getPrimitiveClass", this));
        Registry.register(jlClass, "getName0", "()Ljava/lang/String;", new NativeMethod( "getName0", this));
        Registry.register(jlClass, "desiredAssertionStatus0", "(Ljava/lang/Class;)Z", new NativeMethod( "desiredAssertionStatus0", this));
        Registry.register(jlClass, "registerNatives", "()V", new NativeMethod( "registerNatives", this));
    }

    /**
     * @see Class#registerNatives()
     */
    public void registerNatives(Frame frame) {
        // do nothing
    }

    /**
     * @see Class#getPrimitiveClass(String)
     *
     */
    public void getPrimitiveClass(Frame frame) {
        JObject nameObj = frame.localVariables.getRef(0);
        String name = StringPool.goString(nameObj);

        JClassLoader loader = frame.method.getClazz().getLoader();
        JObject jClass = loader.loadClass(name).jClass;

        frame.operandStack.pushRef(jClass);
    }

    /**
     * @see Class#getName0()
     */
    public void getName0(Frame frame) {
        JObject thiz = frame.localVariables.getThis();
        JClass clazz = (JClass) thiz.extra;

        String name = "虚拟机本地方法getName0获取类名：" + clazz.javaName();
        JObject nameObj = StringPool.jString(clazz.getLoader(), name);

        frame.operandStack.pushRef(nameObj);
    }

    /**
     * @see Class#desiredAssertionStatus0(Class)
     */
    public void desiredAssertionStatus0(Frame frame) {
        frame.operandStack.pushBoolean(false);
    }

    public void isInterface(Frame frame) {
        LocalVariables vars = frame.localVariables;
        JObject thiz = vars.getThis();
        JClass clazz = (JClass) thiz.extra;

        OperandStack stack = frame.operandStack;
        stack.pushBoolean(clazz.getAccessFlag().isInterface());
    }

    public void isPrimitive(Frame frame) {
        LocalVariables vars = frame.localVariables;
        JObject thiz = vars.getThis();
        JClass clazz = (JClass) thiz.extra;

        OperandStack stack = frame.operandStack;
        stack.pushBoolean(clazz.IsPrimitive());
    }

}

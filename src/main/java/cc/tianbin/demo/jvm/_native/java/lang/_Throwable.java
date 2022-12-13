package cc.tianbin.demo.jvm._native.java.lang;

import cc.tianbin.demo.jvm._native.NativeMethod;
import cc.tianbin.demo.jvm._native.Registry;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.Thread;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;

/**
 * Created by nibnait on 2022/12/13
 */
public class _Throwable {

    private StackTraceElement stackTraceElement;

    /**
     * @see java.lang.Throwable
     */
    private final String jlThrowable = "java/lang/Throwable";

    public _Throwable() {
        Registry.register(jlThrowable, "fillInStackTrace", "(I)Ljava/lang/Throwable;", new NativeMethod("fillInStackTrace", this));
        Registry.register(jlThrowable, "registerNatives", "()V", new NativeMethod("registerNatives", this));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    public String string() {
        return String.format("%s.%s(%s:%d)", this.stackTraceElement.className, this.stackTraceElement.methodName, this.stackTraceElement.fileName, this.stackTraceElement.lineNumber);
    }

    /**
     * @see Throwable#fillInStackTrace()
     */
    public void fillInStackTrace(Frame frame) {
        JObject thiz = frame.localVariables.getThis();
        frame.operandStack.pushRef(thiz);

        _Throwable[] stes = createStackTraceElements(thiz, frame.thread);
        thiz.setExtra(stes);
    }

    private _Throwable[] createStackTraceElements(JObject tObj, Thread thread) {
        // 计算所需要跳过的帧数
        int skip = distanceToObject(tObj.getClazz()) + 2;
        Frame[] frames = thread.getFrames();
        _Throwable[] stackTraceElements = new _Throwable[frames.length - skip];
        int idx = 0;
        for (int i = skip; i < frames.length; i++) {
            stackTraceElements[idx] = createStackTraceElement(frames[i]);
        }
        return stackTraceElements;
    }

    private int distanceToObject(JClass clazz) {
        int distance = 0;
        JClass c = clazz.getSuperClass();
        while (c != null) {
            distance++;
            c = c.getSuperClass();
        }
        return distance;
    }

    private _Throwable createStackTraceElement(Frame frame) {
        Method method = frame.method;
        JClass clazz = method.getClazz();
        StackTraceElement stackTraceElement = new StackTraceElement();
        stackTraceElement.fileName = clazz.getSourceFile();
        stackTraceElement.className = clazz.javaName();
        stackTraceElement.methodName = method.getName();
        stackTraceElement.lineNumber = method.getLineNumber(frame.nextPC - 1);
        _Throwable throwable = new _Throwable();
        throwable.stackTraceElement = stackTraceElement;
        return throwable;
    }

    private class StackTraceElement {
        private String fileName;
        private String className;
        private String methodName;
        private int lineNumber;
    }

}

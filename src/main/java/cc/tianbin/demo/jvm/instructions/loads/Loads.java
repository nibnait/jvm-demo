package cc.tianbin.demo.jvm.instructions.loads;

import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JVMMAObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class Loads {

    private Loads() {
        throw new AssertionError("工具类不允许被实例化");
    }

    /**
     * iload
     * 从局部变量表获取 int 变量，然后推入操作数栈顶
     */
    public static void iload(Frame frame, int index) {
        int val = frame.localVariables.getInt(index);
        frame.operandStack.pushInt(val);
    }

    /**
     * lload
     * 从局部变量表获取 long 变量，然后推入操作数栈顶
     */
    public static void lload(Frame frame, int index) {
        long val = frame.localVariables.getLong(index);
        frame.operandStack.pushLong(val);
    }

    /**
     * fload
     * 从局部变量表获取 float 变量，然后推入操作数栈顶
     */
    public static void fload(Frame frame, int index) {
        float val = frame.localVariables.getFloat(index);
        frame.operandStack.pushFloat(val);
    }

    /**
     * dload
     * 从局部变量表获取 double 变量，然后推入操作数栈顶
     */
    public static void dload(Frame frame, int index) {
        double val = frame.localVariables.getDouble(index);
        frame.operandStack.pushDouble(val);
    }

    /**
     * aload
     * 从局部变量表获取 引用变量，然后推入操作数栈顶
     */
    public static void aload(Frame frame, int index) {
        JVMMAObject val = frame.localVariables.getRef(index);
        frame.operandStack.pushRef(val);
    }
}

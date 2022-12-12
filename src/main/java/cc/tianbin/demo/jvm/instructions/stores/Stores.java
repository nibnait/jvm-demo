package cc.tianbin.demo.jvm.instructions.stores;

import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JVMMAObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class Stores {

    private Stores() {
        throw new AssertionError("工具类不允许被实例化");
    }

    /**
     * istore
     * 从操作数栈顶弹出 int 变量，存入局部变量表的 index位置
     */
    public static void istore(Frame frame, int index) {
        int val = frame.operandStack.popInt();
        frame.localVariables.setInt(index, val);
    }

    /**
     * lstore
     * 从操作数栈顶弹出 long 变量，存入局部变量表的 index位置
     */
    public static void lstore(Frame frame, int index) {
        long val = frame.operandStack.popLong();
        frame.localVariables.setLong(index, val);
    }

    /**
     * fstore
     * 从操作数栈顶弹出 float 变量，存入局部变量表的 index位置
     */
    public static void fstore(Frame frame, int index) {
        float val = frame.operandStack.popFloat();
        frame.localVariables.setFloat(index, val);
    }

    /**
     * dstore
     * 从操作数栈顶弹出 double 变量，存入局部变量表的 index位置
     */
    public static void dstore(Frame frame, int index) {
        double val = frame.operandStack.popDouble();
        frame.localVariables.setDouble(index, val);
    }

    /**
     * astore
     * 从操作数栈顶弹出 double 变量，存入局部变量表的 index位置
     */
    public static void astore(Frame frame, int index) {
        JVMMAObject val = frame.operandStack.popRef();
        frame.localVariables.setRef(index, val);
    }
}

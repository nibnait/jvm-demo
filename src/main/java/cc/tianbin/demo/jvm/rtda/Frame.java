package cc.tianbin.demo.jvm.rtda;

import cc.tianbin.demo.jvm.rtda.frame.LocalVariables;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import io.github.nibnait.common.utils.DataUtils;

/**
 * Created by nibnait on 2022/12/04
 */
public class Frame {

    // 栈帧
    public Frame lower;

    // 局部变量表
    public LocalVariables localVariables;

    // 操作数栈
    public OperandStack operandStack;

    // 栈帧所在的线程
    public Thread thread;

    // 为了通过frame变量拿到当前类的运行时常量池
    public Method method;

    // Program Counter
    public int nextPC;

    public Frame(Thread thread, Method method) {
        this.thread = thread;
        this.method = method;

        /**
         * 局部变量表 和 操作数栈的大小 是有编译器预先计算好的
         * @see cc.tianbin.demo.jvm.classfile.attributes.impl.group1.CodeAttribute.maxLocals
         * @see cc.tianbin.demo.jvm.classfile.attributes.impl.group1.CodeAttribute.maxStack
         */
        this.localVariables = new LocalVariables(method.getMaxLocals());
        this.operandStack = new OperandStack(method.getMaxStack());
    }

    public String formatLocalVars() {
        return DataUtils.format("{}", localVariables.formatSlots());
    }

}

package cc.tianbin.demo.jvm.instructions.references.array;

import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.ClassRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/13
 */
public class ANEW_ARRAY extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xbd;
    }

    @Override
    public String operate() {
        return "anewarray";
    }

    @Override
    public void execute(Frame frame) {
        RuntimeConstantPool runtimeConstantPool = frame.method.getClazz().getRuntimeConstantPool();
        ClassRef classRef = (ClassRef) runtimeConstantPool.getConstants(this.index);
        JClass componentClass = classRef.resolvedClass();

        OperandStack stack = frame.operandStack;
        int count = stack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException();
        }

        JClass arrClass = componentClass.arrayClass();
        JObject arr = arrClass.newArray(count);
        stack.pushRef(arr);
    }
}

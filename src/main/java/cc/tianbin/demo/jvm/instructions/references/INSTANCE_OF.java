package cc.tianbin.demo.jvm.instructions.references;

import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.ClassRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/10
 */
public class INSTANCE_OF extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xc1;
    }

    @Override
    public String operate() {
        return "instanceof";
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        JObject ref = stack.popRef();

        if (ref == null) {
            // 空对象，不管他 instanceof 啥，返回的结果都是 false
            stack.pushInt(0);
            return;
        }

        RuntimeConstantPool runtimeConstantPool = frame.method.getClazz().getRuntimeConstantPool();
        ClassRef classRef = (ClassRef) runtimeConstantPool.getConstants(this.index);
        JClass clazz = classRef.resolvedClass();

        if (ref.isInstanceOf(clazz)) {
            stack.pushInt(1);
        } else {
            stack.pushInt(0);
        }

    }
}

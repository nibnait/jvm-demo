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
public class CHECK_CAST extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xc0;
    }

    @Override
    public String operate() {
        return "checkcast";
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        JObject ref = stack.popRef();
        // 不改变操作数栈
        stack.pushRef(ref);

        if (ref == null) {
            // null引用 可以转换成任何类型
            return;
        }

        RuntimeConstantPool runtimeConstantPool = frame.method.getClazz().getRuntimeConstantPool();
        ClassRef classRef = (ClassRef) runtimeConstantPool.getConstants(this.index);
        JClass clazz = classRef.resolvedClass();
        if (!ref.isInstanceOf(clazz)) {
            throw new ClassCastException(String.format("%s cannot be cast to %s",
                    ref.getClazz().getName(), clazz.getName()));
        }
    }
}

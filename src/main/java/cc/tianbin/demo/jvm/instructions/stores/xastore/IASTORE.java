package cc.tianbin.demo.jvm.instructions.stores.xastore;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JVMMAObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class IASTORE extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x4f;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        // 要赋给数组元素的值
        int val = stack.popInt();
        // 数组索引
        int index = stack.popInt();
        // 数组引用
        JVMMAObject arrRef = stack.popRef();
        checkNotNull(arrRef);

        int[] ints = arrRef.ints();
        checkIndex(ints.length, index);

        ints[index] = val;
    }
}

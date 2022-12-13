package cc.tianbin.demo.jvm.instructions.stores.xastore;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JVMMAObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class CASTORE extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x55;
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

        char[] chars = arrRef.chars();
        checkIndex(chars.length, index);

        chars[index] = (char) val;
    }
}

package cc.tianbin.demo.jvm.instructions.stores.xastore;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JVMMAObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class FASTORE extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x51;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        // 要赋给数组元素的值
        float val = stack.popFloat();
        // 数组索引
        int index = stack.popInt();
        // 数组引用
        JVMMAObject arrRef = stack.popRef();
        checkNotNull(arrRef);

        float[] floats = arrRef.floats();
        checkIndex(floats.length, index);

        floats[index] = val;
    }
}

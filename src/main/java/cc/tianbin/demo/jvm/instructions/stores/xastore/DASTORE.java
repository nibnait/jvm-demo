package cc.tianbin.demo.jvm.instructions.stores.xastore;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class DASTORE extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x52;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        // 要赋给数组元素的值
        double val = stack.popDouble();
        // 数组索引
        int index = stack.popInt();
        // 数组引用
        JObject arrRef = stack.popRef();
        checkNotNull(arrRef);

        double[] doubles = arrRef.doubles();
        checkIndex(doubles.length, index);

        doubles[index] = val;
    }
}

package cc.tianbin.demo.jvm.instructions.loads.xaload;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JVMMAObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class DALOAD extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x31;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int index = stack.popInt();
        JVMMAObject arrRef = stack.popRef();
        checkNotNull(arrRef);

        double[] doubles = arrRef.doubles();
        checkIndex(doubles.length, index);
        stack.pushDouble(doubles[index]);
    }
}

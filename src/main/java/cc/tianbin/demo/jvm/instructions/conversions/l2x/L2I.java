package cc.tianbin.demo.jvm.instructions.conversions.l2x;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class L2I extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x88;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        long val = stack.popLong();
        stack.pushInt((int) val);
    }
}

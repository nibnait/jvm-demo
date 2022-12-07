package cc.tianbin.demo.jvm.instructions.conversions.f2x;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class F2L extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x8c;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        float val = stack.popFloat();
        stack.pushLong((long) val);
    }
}

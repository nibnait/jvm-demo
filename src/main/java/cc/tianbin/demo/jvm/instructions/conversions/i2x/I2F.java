package cc.tianbin.demo.jvm.instructions.conversions.i2x;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Convert int to float
 * Created by nibnait on 2022/12/07
 */
public class I2F extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x86;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int val = stack.popInt();
        stack.pushFloat((float) val);
    }
}

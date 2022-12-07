package cc.tianbin.demo.jvm.instructions.math.mul;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class FMUL extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x6a;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        float v2 = stack.popFloat();
        float v1 = stack.popFloat();
        stack.pushFloat(v1 / v2);
    }
}

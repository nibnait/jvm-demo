package cc.tianbin.demo.jvm.instructions.math.neg;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class INEG extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x74;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int val = stack.popInt();
        stack.pushInt(-val);
    }
}

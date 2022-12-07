package cc.tianbin.demo.jvm.instructions.conversions.d2x;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class D2F extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x90;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        double val = stack.popDouble();
        stack.pushFloat((float) val);
    }
}

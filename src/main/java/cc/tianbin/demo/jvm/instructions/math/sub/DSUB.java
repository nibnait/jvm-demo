package cc.tianbin.demo.jvm.instructions.math.sub;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class DSUB extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x67;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        double v2 = stack.popDouble();
        double v1 = stack.popDouble();
        stack.pushDouble(v1 - v2);
    }
}

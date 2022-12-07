package cc.tianbin.demo.jvm.instructions.math.rem;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class FREM extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x72;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        float v2 = stack.popFloat();
        float v1 = stack.popFloat();

        if (v1 == 0) {
            throw new ArithmeticException("/ by zero");
        }
        stack.pushFloat(v2 % v1);
    }
}

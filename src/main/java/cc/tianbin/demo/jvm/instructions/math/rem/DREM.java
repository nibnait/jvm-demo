package cc.tianbin.demo.jvm.instructions.math.rem;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class DREM extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x73;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        double v2 = stack.popDouble();
        double v1 = stack.popDouble();

        if (v1 == 0) {
            throw new ArithmeticException("/ by zero");
        }
        stack.pushDouble(v2 % v1);
    }
}

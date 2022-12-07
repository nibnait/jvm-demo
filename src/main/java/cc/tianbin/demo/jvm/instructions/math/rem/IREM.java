package cc.tianbin.demo.jvm.instructions.math.rem;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class IREM extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x70;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int v2 = stack.popInt();
        int v1 = stack.popInt();

        if (v1 == 0) {
            throw new ArithmeticException("/ by zero");
        }
        stack.pushInt(v2 % v1);
    }
}

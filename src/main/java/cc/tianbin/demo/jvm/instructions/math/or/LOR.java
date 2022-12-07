package cc.tianbin.demo.jvm.instructions.math.or;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class LOR extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x81;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        long v2 = stack.popLong();
        long v1 = stack.popLong();
        stack.pushLong(v1 | v2);
    }
}

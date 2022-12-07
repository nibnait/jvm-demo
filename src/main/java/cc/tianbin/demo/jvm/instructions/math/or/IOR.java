package cc.tianbin.demo.jvm.instructions.math.or;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class IOR extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x80;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        stack.pushLong(v1 | v2);
    }
}

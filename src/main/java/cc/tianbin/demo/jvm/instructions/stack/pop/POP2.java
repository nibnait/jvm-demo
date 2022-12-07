package cc.tianbin.demo.jvm.instructions.stack.pop;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

// Pop the top one or two operand stack values
/*
bottom -> top
[...][c][b][a]
         |  |
         V  V
[...][c]
*/
public class POP2 extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x58;
    }

    @Override
    public void execute(Frame frame) {
        frame.operandStack.popSlot();
        frame.operandStack.popSlot();
    }
}

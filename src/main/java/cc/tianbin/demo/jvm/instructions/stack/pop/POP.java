package cc.tianbin.demo.jvm.instructions.stack.pop;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

// Pop the top operand stack value
/*
bottom -> top
[...][c][b][a]
            |
            V
[...][c][b]
*/
public class POP extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x57;
    }

    @Override
    public void execute(Frame frame) {
        frame.operandStack.popSlot();
    }
}

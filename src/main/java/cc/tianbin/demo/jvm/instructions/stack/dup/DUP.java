package cc.tianbin.demo.jvm.instructions.stack.dup;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.frame.Slot;

// Duplicate the top operand stack value
/*
bottom -> top
[...][c][b][a]
             \_
               |
               V
[...][c][b][a][a]
*/
public class DUP extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x59;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        Slot slot = stack.popSlot();
        stack.pushSlot(slot);
        stack.pushSlot(slot);
    }
}

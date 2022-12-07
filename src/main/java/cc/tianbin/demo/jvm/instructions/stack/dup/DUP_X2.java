package cc.tianbin.demo.jvm.instructions.stack.dup;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.frame.Slot;

// Duplicate the top operand stack value and insert two or three values down
/*
bottom -> top
[...][c][b][a]
       _____/
      |
      V
[...][a][c][b][a]
*/
public class DUP_X2 extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x5b;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        Slot slot1 = stack.popSlot();
        Slot slot2 = stack.popSlot();
        Slot slot3 = stack.popSlot();
        stack.pushSlot(slot1);
        stack.pushSlot(slot3);
        stack.pushSlot(slot2);
        stack.pushSlot(slot1);
    }
}

package cc.tianbin.demo.jvm.instructions.stack.swap;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.frame.Slot;

// Swap the top two operand stack values
/*
bottom -> top
[...][c][b][a]
          \/
          /\
         V  V
[...][c][a][b]
*/
public class SWAP extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x5f;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        Slot slot1 = stack.popSlot();
        Slot slot2 = stack.popSlot();
        stack.pushSlot(slot1);
        stack.pushSlot(slot2);
    }
}

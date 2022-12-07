package cc.tianbin.demo.jvm.instructions.comparisons.if_icmp;

import cc.tianbin.demo.jvm.instructions.base.BranchInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class IF_ICMPNE extends BranchInstruction {
    @Override
    public int opcode() {
        return 0xa0;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        if (v1 != v2) {
            branch(frame, this.offset);
        }
    }
}

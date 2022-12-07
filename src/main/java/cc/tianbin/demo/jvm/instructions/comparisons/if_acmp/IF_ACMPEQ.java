package cc.tianbin.demo.jvm.instructions.comparisons.if_acmp;

import cc.tianbin.demo.jvm.instructions.base.BranchInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class IF_ACMPEQ extends BranchInstruction {
    @Override
    public int opcode() {
        return 0xa5;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        Object v2 = stack.popRef();
        Object v1 = stack.popRef();
        if (v1.equals(v2)) {
            branch(frame, this.offset);
        }
    }
}

package cc.tianbin.demo.jvm.instructions.comparisons.ifcond;

import cc.tianbin.demo.jvm.instructions.base.BranchInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class IFGT extends BranchInstruction {
    @Override
    public int opcode() {
        return 0x9d;
    }

    @Override
    public void execute(Frame frame) {
        int val = frame.operandStack.popInt();
        if (val > 0) {
            branch(frame, this.offset);
        }
    }
}

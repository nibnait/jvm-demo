package cc.tianbin.demo.jvm.instructions.control;

import cc.tianbin.demo.jvm.instructions.base.BranchInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class GOTO extends BranchInstruction {
    @Override
    public int opcode() {
        return 0xa7;
    }

    @Override
    public void execute(Frame frame) {
        branch(frame, this.offset);
    }
}

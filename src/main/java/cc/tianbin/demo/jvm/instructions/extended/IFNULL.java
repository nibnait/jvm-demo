package cc.tianbin.demo.jvm.instructions.extended;

import cc.tianbin.demo.jvm.instructions.base.BranchInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class IFNULL extends BranchInstruction {
    @Override
    public int opcode() {
        return 0xc6;
    }

    @Override
    public void execute(Frame frame) {
        Object ref = frame.operandStack.popRef();
        if (ref == null) {
            branch(frame, this.offset);
        }
    }
}

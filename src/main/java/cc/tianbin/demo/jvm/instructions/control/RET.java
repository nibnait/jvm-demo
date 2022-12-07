package cc.tianbin.demo.jvm.instructions.control;

import cc.tianbin.demo.jvm.exception.InstructionException;
import cc.tianbin.demo.jvm.instructions.base.BranchInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.utils.NumberUtil;

/**
 * Access jump table by key match and jump
 * Created by nibnait on 2022/12/07
 */
public class RET extends BranchInstruction {
    @Override
    public int opcode() {
        return 0xa9;
    }

    @Override
    public void execute(Frame frame) {
        // Deprecated
        throw new InstructionException("invalid operate instruction {} {} ",
                NumberUtil.int2HexString(0xa9), "ret");
    }
}

package cc.tianbin.demo.jvm.instructions.control.rtn;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class RETURN extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0xb1;
    }

    @Override
    public void execute(Frame frame) {
        // todo 7.4
    }
}

package cc.tianbin.demo.jvm.instructions.comparisons.fcmp;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class FCMPL extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x95;
    }

    @Override
    public void execute(Frame frame) {
        fcmp(frame, false);
    }

}

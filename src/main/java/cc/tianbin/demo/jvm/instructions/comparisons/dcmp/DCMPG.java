package cc.tianbin.demo.jvm.instructions.comparisons.dcmp;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class DCMPG extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x98;
    }

    @Override
    public void execute(Frame frame) {
        dcmp(frame, true);
    }

}

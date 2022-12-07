package cc.tianbin.demo.jvm.instructions.constants.nop;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/06
 */
public class NOP extends NoOperandsInstruction {

    @Override
    public int opcode() {
        return 0x00;
    }

    @Override
    public void execute(Frame frame) {
        //really do nothing
    }
}

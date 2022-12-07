package cc.tianbin.demo.jvm.instructions.constants.ldc;

import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/06
 */
public class LDC2_W extends Index16Instruction {

    @Override
    public int opcode() {
        return 0x14;
    }

    @Override
    public void execute(Frame frame) {
        // todo 6.6.5
    }
}

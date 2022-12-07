package cc.tianbin.demo.jvm.instructions.constants.ldc;

import cc.tianbin.demo.jvm.instructions.base.Index8Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/06
 */
public class LDC extends Index8Instruction {

    @Override
    public int opcode() {
        return 0x12;
    }

    @Override
    public void execute(Frame frame) {
        // todo 9.3.4
    }
}

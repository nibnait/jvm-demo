package cc.tianbin.demo.jvm.instructions.references;

import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/12
 */
public class INVOKE_DYNAMIC extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xc7;
    }

    @Override
    public void execute(Frame frame) {

    }
}

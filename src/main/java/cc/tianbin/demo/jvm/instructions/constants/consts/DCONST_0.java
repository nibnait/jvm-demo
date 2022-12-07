package cc.tianbin.demo.jvm.instructions.constants.consts;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/06
 */
public class DCONST_0 extends NoOperandsInstruction {

    @Override
    public int opcode() {
        return 0x0e;
    }

    @Override
    public void execute(Frame frame) {
        frame.operandStack.pushDouble(0.0d);
    }
}

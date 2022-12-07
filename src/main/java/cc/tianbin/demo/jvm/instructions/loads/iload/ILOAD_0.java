package cc.tianbin.demo.jvm.instructions.loads.iload;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.instructions.loads.Loads;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class ILOAD_0 extends NoOperandsInstruction {

    @Override
    public int opcode() {
        return 0x1a;
    }

    @Override
    public void execute(Frame frame) {
        Loads.iload(frame, 0);
    }

}

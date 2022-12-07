package cc.tianbin.demo.jvm.instructions.loads.fload;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.instructions.loads.Loads;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class FLOAD_2 extends NoOperandsInstruction {

    @Override
    public int opcode() {
        return 0x24;
    }

    @Override
    public void execute(Frame frame) {
        Loads.fload(frame, 2);
    }

}

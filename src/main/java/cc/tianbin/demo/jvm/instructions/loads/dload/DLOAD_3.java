package cc.tianbin.demo.jvm.instructions.loads.dload;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.instructions.loads.Loads;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class DLOAD_3 extends NoOperandsInstruction {

    @Override
    public int opcode() {
        return 0x29;
    }

    @Override
    public void execute(Frame frame) {
        Loads.dload(frame, 3);
    }

}
